package com.example.joy.beyond;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserEventRepository extends  CrudRepository<UserEvent, Long> {
    List<UserEvent> findByName(String name);
    List<UserEvent> findByToken(String token);
}
