package com.lynn.reverbtime.client.RoomComponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomComponentServiceTest {

    private MockWebServer mockWebServer;
    private RoomComponentClient client;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws IOException, IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        this.objectMapper = new ObjectMapper();
        this.client = new RoomComponentClient(WebClient.builder(), mockWebServer.url("/").toString());

        /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String comp_json = ow.writeValueAsString(comp);*/

    }
    @Test
    public void getComponentsTest() throws JsonProcessingException {

        RoomComponent[] comps = List.of(new RoomComponent("Concrete", 0.7),
                new RoomComponent("Wood", 0.4),
                new RoomComponent("Drywall", 0.5)).toArray(RoomComponent[]::new);
        for (RoomComponent r: comps) {
            System.out.println(r.toString());
        }
        String compsJson = objectMapper.writeValueAsString(comps);

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(compsJson)
                .setResponseCode(200);

        mockWebServer.enqueue(mockResponse);

        RoomComponent[] result = client.getComponents();

        for (int i = 0; i <comps.length; i++) {
            assertEquals(comps[i].toString(),result[i].toString());
        }

    }
    @Test
    public void getComponentsByNameTest() throws JsonProcessingException {
        RoomComponent comp = new RoomComponent("Wood", 0.4);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String compJson = ow.writeValueAsString(comp);

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(compJson)
                .setResponseCode(200);

        mockWebServer.enqueue(mockResponse);

        RoomComponent result = client.getComponentByName(comp.getName());

        assertEquals(comp.toString(), result.toString());

    }

    @Test
    public void addComponentToDataBaseTest() throws Exception {
        RoomComponent comp = new RoomComponent("Wood", 0.4);
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(500);

        mockWebServer.enqueue(mockResponse);

        assertThrows(RuntimeException.class, () -> client.addComponentToDataBase(comp));

    }

    @Test
    public void removeComponentFromDataBaseTest(){

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(500);

        mockWebServer.enqueue(mockResponse);
        assertThrows(RuntimeException.class, () -> client.removeComponentFromDataBase("Concrete"));

    }

}