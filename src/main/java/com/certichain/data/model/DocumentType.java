package com.certichain.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documenttype")
public class DocumentType {

    @Id
    private String Id;
    private String UserID;
    private String Name;
    private String State;
    
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
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getState() {
        return State;
    }
    public void setState(String state) {
        State = state;
    }
    
}
