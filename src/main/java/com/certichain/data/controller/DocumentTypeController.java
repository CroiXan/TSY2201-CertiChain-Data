package com.certichain.data.controller;

import java.util.List;
import java.util.Optional;

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
import com.certichain.data.service.DocumentTypeService;

@RestController
@RequestMapping("/api/documenttypes")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeservice;

    public DocumentTypeController(DocumentTypeService documentTypeservice) {
        this.documentTypeservice = documentTypeservice;
    }

    @GetMapping
    public List<DocumentType> getAll() {
        return documentTypeservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getById(@PathVariable String id) {
        return documentTypeservice.findById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<DocumentType> getByUserId(@PathVariable String userId) {
        return documentTypeservice.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<DocumentType> create(@RequestBody DocumentType documentType) {
        DocumentType created = documentTypeservice.create(documentType);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> update(@PathVariable String id,
                                               @RequestBody DocumentType documentType) {
        if (!documentTypeservice.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Optional<DocumentType> updated = documentTypeservice.update(documentType);
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!documentTypeservice.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        documentTypeservice.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
