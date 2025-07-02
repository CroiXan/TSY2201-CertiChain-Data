package com.certichain.data.model;

import org.bson.types.ObjectId;

public class DocumentTypeApi {

    private String id;
    private String UserID;
    private String Name;
    private String State;

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

    public static DocumentType toDocumentType(DocumentTypeApi api) {
        DocumentType documentType = new DocumentType();

        if (api.getId() != null && ObjectId.isValid(api.getId())) {
            documentType.setId(new ObjectId(api.getId()));
        }

        documentType.setUserID(api.getUserID());
        documentType.setName(api.getName());
        documentType.setState(api.getState());

        return documentType;
    }

    public static DocumentTypeApi fromDocumentType(DocumentType documentType) {
        DocumentTypeApi api = new DocumentTypeApi();

        if (documentType.getId() != null) {
            api.setId(documentType.getId().toString());
        }

        api.setUserID(documentType.getUserID());
        api.setName(documentType.getName());
        api.setState(documentType.getState());

        return api;
    }

}
