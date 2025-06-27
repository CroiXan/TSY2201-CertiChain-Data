package com.certichain.data.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.certichain.data.model.UserData;

@Repository
public interface UserDataRepository extends MongoRepository<UserData, String> {

    Optional<UserData> findByUserID(String userID);
    
}
