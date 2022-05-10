package com.lynn.reverbtime.RoomComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



/*
Service class that provides methods to save to, delete and get items from the table by
calling the corresponding repository. Performs business logic as well.
*/
@Service
public class rcService {

    private final rcRepository rcRepository;

    @Autowired
    public rcService(rcRepository rcRepository) {
        this.rcRepository = rcRepository;
    }

    public List<RoomComponent> getComponents() {

        return rcRepository.findAll();
    }

    public Optional<RoomComponent> getComponentByName(String name) {
        Optional<RoomComponent> rcByName = rcRepository.findroomComponentByName(name);
        if (!rcByName.isPresent()) {
            throw new IllegalStateException("Component with name " + name + " does not exist");
        }
        return rcByName;
    }

    public void addRoomComponent(RoomComponent rc) {
        Optional<RoomComponent> rcByName = rcRepository.findroomComponentByName(rc.getName());
        if (rcByName.isPresent()) {
            throw new IllegalStateException("Name taken");
        }
        rcRepository.save(rc);
    }

    public void deleteComponent(String name) {
        Optional<RoomComponent> rcByName = rcRepository.findroomComponentByName(name);
        if (!rcByName.isPresent()) {
            throw new IllegalStateException("Component with name " + name + " does not exist");
        }
        rcRepository.deleteById(rcByName.get().getId());
    }


}
