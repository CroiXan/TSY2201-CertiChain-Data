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

import com.certichain.data.model.UserData;
import com.certichain.data.model.UserDataApi;
import com.certichain.data.service.UserDataService;

@RestController
@RequestMapping("/api/userdata")
public class UserDataController {

    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping
    public ResponseEntity<List<UserDataApi>> getAll() {
        return ResponseEntity.ok( userDataService.findAll().stream()
                .map(UserDataApi::fromUserData)
                .collect(Collectors.toList()) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataApi> getById(@PathVariable String id) {
        Optional<UserData> userOpt = userDataService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok( UserDataApi.fromUserData(userOpt.get()) );
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/userid/{userID}")
    public ResponseEntity<UserDataApi> getByUserID(@PathVariable String userID) {
        Optional<UserData> userOpt = userDataService.findByUserID(userID);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok( UserDataApi.fromUserData(userOpt.get()) );
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDataApi> create(@RequestBody UserDataApi userData) {
        UserData created = userDataService.create(UserDataApi.toUserData(userData));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDataApi.fromUserData(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDataApi> update(
            @PathVariable String id,
            @RequestBody UserDataApi userData) {
        Optional<UserData> updatedOpt = userDataService.update(id, UserDataApi.toUserData(userData));

        if (updatedOpt.isPresent()) {
            return ResponseEntity.ok(UserDataApi.fromUserData(updatedOpt.get()));
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
    public ResponseEntity<List<UserDataApi>> getByUserTypeId(@PathVariable String userTypeId) {
        List<UserData> result = userDataService.findByUserTypeId(userTypeId);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result.stream()
                .map(UserDataApi::fromUserData)
                .collect(Collectors.toList()));
    }
    
}
