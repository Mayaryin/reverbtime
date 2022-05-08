package com.lynn.reverbtime.client.RoomComponent;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
Service class that builds and makes rest API calls using the Spring WebClient
*/

@Service
public class RoomComponentClient {

    private final WebClient webClient;

    //Set up WebClient with correct base url
    public RoomComponentClient(WebClient.Builder builder,
                               @Value("${reverbtime.roomcomponents.url}") String BaseUrl) {
        webClient = WebClient.builder().baseUrl(BaseUrl).build();
    }

    //GET request, get all items
    public RoomComponent[] getComponents() {
        return webClient.
                get()
                .uri("/roomcomponents")
                .retrieve()
                .bodyToMono(RoomComponent[].class)
                .block();
    }

    //GET request, get single item by name
    public RoomComponent getComponentByName(@PathVariable("name") String name) {
        return webClient.
                get()
                .uri("/roomcomponents/" + name)
                .retrieve()
                .bodyToMono(RoomComponent.class)
                .block();
    }

    //POST request, function throws exception in case a name is already in the database
    public RoomComponent addComponentToDataBase(RoomComponent comp) throws Exception {
        return webClient.
                post()
                .uri("/roomcomponents")
                .body(Mono.just(comp), RoomComponent.class)
                .retrieve()
                .bodyToMono(RoomComponent.class)
                .block();
    }

    //DELETE request
    public void removeComponentFromDataBase(@PathVariable("name") String name) {
        ClientResponse clientResponse =  webClient
                .delete()
                .uri("/roomcomponents/" +name)
                .retrieve()
                .bodyToMono(ClientResponse.class).block();

        if (clientResponse.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            throw new RuntimeException("Internal Server Error!");
        }
    }
}
