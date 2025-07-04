package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certichain.data.model.DocumentType;
import com.certichain.data.model.DocumentTypeApi;
import com.certichain.data.service.DocumentTypeService;

@RestController
@RequestMapping("/api/documenttypes")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeservice;

    public DocumentTypeController(DocumentTypeService documentTypeservice) {
        this.documentTypeservice = documentTypeservice;
    }

    @GetMapping
    public List<DocumentTypeApi> getAll() {
        List<DocumentType> docuTypeList = documentTypeservice.findAll();
        return docuTypeList.stream()
                .map(this::fromDocumentType)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeApi> getById(@PathVariable String id) {
        return new ResponseEntity<>(this.fromDocumentType(documentTypeservice.findById(id).get()), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public List<DocumentTypeApi> getByUserId(@PathVariable String userId) {
        List<DocumentType> docuTypeList = documentTypeservice.findByUserId(userId);
        return docuTypeList.stream()
                .map(this::fromDocumentType)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<DocumentTypeApi> create(@RequestBody DocumentTypeApi documentType) {
        DocumentType created = documentTypeservice.create(this.toDocumentType(documentType));
        return new ResponseEntity<>(this.fromDocumentType(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeApi> update(@PathVariable String id,
            @RequestBody DocumentTypeApi documentType) {
        if (!documentTypeservice.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Optional<DocumentType> updated = documentTypeservice.update(this.toDocumentType(documentType));
        return ResponseEntity.ok(this.fromDocumentType(updated.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!documentTypeservice.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        documentTypeservice.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    private DocumentType toDocumentType(DocumentTypeApi api) {
        DocumentType documentType = new DocumentType();

        if (api.getId() != null && ObjectId.isValid(api.getId())) {
            documentType.setId(new ObjectId(api.getId()));
        }

        documentType.setUserID(api.getUserID());
        documentType.setName(api.getName());
        documentType.setState(api.getState());

        return documentType;
    }

    private DocumentTypeApi fromDocumentType(DocumentType documentType) {
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
