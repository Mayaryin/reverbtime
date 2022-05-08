package com.lynn.reverbtime.RoomComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 This class serves as API layer as it defines the Rest API through mappings.
*/

@RestController
@RequestMapping(path="reverbtime/roomcomponents")
public class rcController {

    private final rcService rcService;

    @Autowired
    /*This eliminates the need for getters and setters.
    Spring injects rcService when rcController is created*/
    public rcController(rcService rcService) {
        this.rcService = rcService;
    }

    @GetMapping
    public List<RoomComponent> getComponents(){
        return rcService.getComponents();
    }

    @GetMapping(path = "{rcName}")
    public Optional<RoomComponent> getComponentByName(@PathVariable("rcName") String name){
        return rcService.getComponentByName(name);
    }

    @PostMapping
    public void registerRC (@RequestBody RoomComponent rc){
        rcService.addRoomComponent(rc);
    }

    @DeleteMapping(path= "{rcName}")
    public void deleteComponent (@PathVariable("rcName") String name){
        rcService.deleteComponent(name);
    }

}
