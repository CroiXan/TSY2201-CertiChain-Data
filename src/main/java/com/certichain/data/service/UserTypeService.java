package com.certichain.data.service;

import org.springframework.stereotype.Service;

import com.certichain.data.repository.UserTypeRepository;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }
}
