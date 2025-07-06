package com.certichain.data.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.certichain.data.model.DocumentType;

@Repository
public interface DocumentTypeRepository extends MongoRepository<DocumentType,ObjectId> {

    @Query("{ 'UserID' : ?0 }")
    List<DocumentType> findByUserID(String userID);
    
}
