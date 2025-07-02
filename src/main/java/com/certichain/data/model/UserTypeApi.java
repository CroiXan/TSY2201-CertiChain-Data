package com.certichain.data.model;

import org.bson.types.ObjectId;

public class UserTypeApi {
    
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

    public static UserType toUserType(UserTypeApi api) {
        UserType userType = new UserType();

        if (api.getId() != null && ObjectId.isValid(api.getId())) {
            userType.setId(new ObjectId(api.getId()));
        }

        userType.setName(api.getName());
        userType.setState(api.getState());

        return userType;
    }

    public static UserTypeApi fromUserType(UserType userType) {
        UserTypeApi api = new UserTypeApi();

        if (userType.getId() != null) {
            api.setId(userType.getId().toString());
        }

        api.setName(userType.getName());
        api.setState(userType.getState());

        return api;
    }
}
