package com.certichain.data.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.certichain.data.model.DocumentType;
import com.certichain.data.repository.DocumentTypeRepository;

@Service
public class DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }

    public Optional<DocumentType> findById(String id) {
        ObjectId oid = new ObjectId(id);
        return documentTypeRepository.findById(oid);
    }

    public List<DocumentType> findByUserId(String userId) {
        return documentTypeRepository.findByUserID(userId);
    }

    public DocumentType create(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    public Optional<DocumentType> update(DocumentType documentType) {
        Optional<DocumentType> findedDocumentType = documentTypeRepository.findById(documentType.getId());

        if(findedDocumentType.isPresent()){
            return Optional.of(documentTypeRepository.save(documentType));
        }else{
            return Optional.empty();
        }
    }

    public boolean delete(String id) {  
        ObjectId oid = new ObjectId(id);
        Optional<DocumentType> findedDocumentType = documentTypeRepository.findById(oid);

        if(findedDocumentType.isPresent()){
            documentTypeRepository.deleteById(oid);
            return true;
        }else{
            return false;
        }
    }

}
