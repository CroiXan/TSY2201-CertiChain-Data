package com.certichain.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userdata")
public class UserData {

    @Id
    private String Id;

    @Indexed(unique = true)  
    private String UserID;

    private String name;

    private String UserTypeId;

    private String UserSubTypeId;
    
    private String Status;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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
}
