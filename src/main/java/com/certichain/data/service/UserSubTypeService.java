package com.certichain.data.service;

import org.springframework.stereotype.Service;

import com.certichain.data.repository.UserSubTypeRepository;

@Service
public class UserSubTypeService {

    private final UserSubTypeRepository userSubTypeRepository;

    public UserSubTypeService(UserSubTypeRepository userSubTypeRepository) {
        this.userSubTypeRepository = userSubTypeRepository;
    }

}
