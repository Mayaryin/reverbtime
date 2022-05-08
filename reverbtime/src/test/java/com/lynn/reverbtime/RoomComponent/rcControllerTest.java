package com.lynn.reverbtime.RoomComponent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class rcControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void getComponents() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/reverbtime/roomcomponents"))
                .andDo(print());
    }

    @Test
    void getComponentByName() throws Exception {

        RoomComponent comp = new RoomComponent("Concrete", 0.7);

        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/reverbtime/roomcomponents/Concrete"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(comp.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.alpha").value(comp.getAlpha()))
                .andExpect(status().isOk());
    }

    @Test
    void registerRC() throws Exception {
        RoomComponent comp = new RoomComponent("Stone", 0.67);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String comp_json = ow.writeValueAsString(comp);

        mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/reverbtime/roomcomponents/")
                .content(comp_json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/reverbtime/roomcomponents"))
                .andDo(print());
    }

    @Test
    void deleteComponent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/reverbtime/roomcomponents/Concrete"))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/reverbtime/roomcomponents"))
                .andDo(print());
    }
}