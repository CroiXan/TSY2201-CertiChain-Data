package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.certichain.data.model.UserType;
import com.certichain.data.model.UserTypeApi;
import com.certichain.data.service.UserTypeService;

@RestController
@RequestMapping("/api/userTypes")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping
    public List<UserTypeApi> getAll() {
        return userTypeService.getAll().stream()
                .map(this::fromUserType)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeApi> getById(@PathVariable String id) {
        Optional<UserType> ut = userTypeService.getUserSubTypeById(id);
        UserTypeApi utapi = this.fromUserType(ut.get());
        return new ResponseEntity<>(utapi, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserTypeApi> create(@RequestBody UserTypeApi userType) {
        UserType created = userTypeService.createUserType(this.toUserType(userType));
        return new ResponseEntity<>(this.fromUserType(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeApi> update(@PathVariable String id,
                                           @RequestBody UserTypeApi userType) {
        userType.setId(id);
        Optional<UserType> updated = userTypeService.updateUserSubType(this.toUserType(userType));
        UserTypeApi utapi = this.fromUserType(updated.get());
        return new ResponseEntity<>(utapi, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = userTypeService.deleteUserSubTypeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private UserType toUserType(UserTypeApi api) {
        UserType userType = new UserType();

        if (api.getId() != null && ObjectId.isValid(api.getId())) {
            userType.setId(new ObjectId(api.getId()));
        }

        userType.setName(api.getName());
        userType.setState(api.getState());

        return userType;
    }

    private UserTypeApi fromUserType(UserType userType) {
        UserTypeApi api = new UserTypeApi();

        if (userType.getId() != null) {
            api.setId(userType.getId().toString());
        }

        api.setName(userType.getName());
        api.setState(userType.getState());

        return api;
    }
    
}
