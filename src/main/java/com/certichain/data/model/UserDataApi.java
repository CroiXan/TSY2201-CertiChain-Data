package com.certichain.data.model;

public class UserDataApi {

    private String id;
    private String UserID;
    private String name;
    private String UserTypeId;
    private String UserSubTypeId;
    private String Status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
