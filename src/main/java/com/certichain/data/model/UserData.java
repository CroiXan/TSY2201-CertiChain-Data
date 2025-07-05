package com.certichain.data.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "userdata")
public class UserData {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @Indexed(unique = true)  
    private String UserID;

    private String name;

    @Field("UserTypeId")
    private String UserTypeId;

    private String UserSubTypeId;
    
    private String Status;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getUserSubTypeId() {
        return UserSubTypeId;
    }

    public void setUserSubTypeId(String userSubTypeId) {
        UserSubTypeId = userSubTypeId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
