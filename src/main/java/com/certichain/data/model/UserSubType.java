package com.certichain.data.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "usersubtype")
public class UserSubType {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String Name;
    private String State;

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
    public ObjectId getId() {
        return this.id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

}
