package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.certichain.data.model.UserSubTypeApi;
import com.certichain.data.service.UserSubTypeService;

@RestController
@RequestMapping("/api/userSubTypes")
public class UserSubTypeController {

    private final UserSubTypeService userSubTypeService;

    public UserSubTypeController(UserSubTypeService userSubTypeService) {
        this.userSubTypeService = userSubTypeService;
    }

    @GetMapping
    public List<UserSubTypeApi> getAll() {
        return userSubTypeService.getAll().stream()
                .map(UserSubTypeApi::fromUserSubType)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSubTypeApi> getById(@PathVariable String id) {
        Optional<UserSubType> ust = userSubTypeService.getUserSubTypeById(id);
        UserSubTypeApi ustapi = UserSubTypeApi.fromUserSubType(ust.get());
        return new ResponseEntity<>(ustapi, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserSubTypeApi> create(@RequestBody UserSubTypeApi userSubType) {
        UserSubType created = userSubTypeService.createUserSubType(UserSubTypeApi.toUserSubType(userSubType));
        UserSubTypeApi result = UserSubTypeApi.fromUserSubType(created);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSubTypeApi> update(@PathVariable String id,
                                              @RequestBody UserSubTypeApi userSubType) {
        userSubType.setId(id);
        Optional<UserSubType> updated = userSubTypeService.updateUserSubType(UserSubTypeApi.toUserSubType(userSubType));
        UserSubTypeApi ustapi = UserSubTypeApi.fromUserSubType(updated.get());
        return new ResponseEntity<>(ustapi, HttpStatus.OK);
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
