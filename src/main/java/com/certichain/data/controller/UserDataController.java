package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;

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

import com.certichain.data.model.UserData;
import com.certichain.data.service.UserDataService;

@RestController
@RequestMapping("/api/userdata")
public class UserDataController {

    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getAll() {
        return ResponseEntity.ok(userDataService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getById(@PathVariable String id) {
        Optional<UserData> userOpt = userDataService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/userid/{userID}")
    public ResponseEntity<UserData> getByUserID(@PathVariable String userID) {
        Optional<UserData> userOpt = userDataService.findByUserID(userID);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserData> create(@RequestBody UserData userData) {
        UserData created = userDataService.create(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserData> update(
            @PathVariable String id,
            @RequestBody UserData userData) {
        Optional<UserData> updatedOpt = userDataService.update(id, userData);

        if (updatedOpt.isPresent()) {
            return ResponseEntity.ok(updatedOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (userDataService.findById(id).isPresent()) {
            userDataService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{userTypeId}")
    public ResponseEntity<List<UserData>> getByUserTypeId(@PathVariable String userTypeId) {
        List<UserData> result = userDataService.findByUserTypeId(userTypeId);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
    
}
