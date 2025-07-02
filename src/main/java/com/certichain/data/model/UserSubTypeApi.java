package com.certichain.data.model;

import org.bson.types.ObjectId;

public class UserSubTypeApi {

    private String id;
    private String Name;
    private String State;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public static UserSubType toUserSubType(UserSubTypeApi api) {
        UserSubType userSubType = new UserSubType();

        if (api.getId() != null && ObjectId.isValid(api.getId())) {
            userSubType.setId(new ObjectId(api.getId()));
        }

        userSubType.setName(api.getName());
        userSubType.setState(api.getState());

        return userSubType;
    }

    public static UserSubTypeApi fromUserSubType(UserSubType userSubType) {
        UserSubTypeApi api = new UserSubTypeApi();

        if (userSubType.getId() != null) {
            api.setId(userSubType.getId().toString());
        }

        api.setName(userSubType.getName());
        api.setState(userSubType.getState());

        return api;
    }

}
