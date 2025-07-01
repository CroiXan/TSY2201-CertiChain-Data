package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certichain.data.model.UserSubType;
import com.certichain.data.service.UserSubTypeService;

@RestController
@RequestMapping("/api/userSubTypes")
public class UserSubTypeController {

    private final UserSubTypeService userSubTypeService;

    public UserSubTypeController(UserSubTypeService userSubTypeService) {
        this.userSubTypeService = userSubTypeService;
    }

    @GetMapping
    public List<UserSubType> getAll() {
        return userSubTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSubType> getById(@PathVariable String id) {
        Optional<UserSubType> ust = userSubTypeService.getUserSubTypeById(id);
        return ust.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSubType> create(@RequestBody UserSubType userSubType) {
        UserSubType created = userSubTypeService.createUserSubType(userSubType);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSubType> update(@PathVariable String id,
                                              @RequestBody UserSubType userSubType) {
        ObjectId oid = new ObjectId(id);
        userSubType.setId(oid);
        Optional<UserSubType> updated = userSubTypeService.updateUserSubType(userSubType);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = userSubTypeService.deleteUserSubTypeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
