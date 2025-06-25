package com.certichain.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usersubtype")
public class UserSubType {

    @Id
    private String Id;
    private String Name;
    private String State;

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
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
