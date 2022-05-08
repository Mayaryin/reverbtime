package com.lynn.reverbtime.RoomComponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
Abstract class for data access.Methods from here will be transformed to SQL Queries.
*/

@Repository
public interface rcRepository extends JpaRepository<RoomComponent, Long> {
    @Query("SELECT r FROM RoomComponent r WHERE r.name = ?1")
    Optional<RoomComponent> findroomComponentByName(String Name);

}
