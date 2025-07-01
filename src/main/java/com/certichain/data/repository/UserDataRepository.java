package com.certichain.data.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.certichain.data.model.UserData;

@Repository
public interface UserDataRepository extends MongoRepository<UserData, ObjectId> {

    Optional<UserData> findByUserID(String userID);

    List<UserData> findAllByUserTypeId(String userTypeId);
    
}
