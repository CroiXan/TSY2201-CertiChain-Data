package com.certichain.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.certichain.data.model.UserType;

@Repository
public interface UserTypeRepository extends MongoRepository<UserType,String>{

}
