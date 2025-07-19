package com.certichain.data.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.certichain.data.model.DocumentType;
import com.certichain.data.repository.DocumentTypeRepository;

public class DocumentTypeServiceTest {

    private DocumentTypeRepository repository;
    private DocumentTypeService service;

    @BeforeEach
    void setUp() {
        repository = mock(DocumentTypeRepository.class);
        service = new DocumentTypeService(repository);
    }

    @Test
    void findAll_shouldReturnList() {
        DocumentType doc = new DocumentType();
        doc.setId(new ObjectId("507f1f77bcf86cd799439011"));
        doc.setUserID("user1");
        when(repository.findAll()).thenReturn(Arrays.asList(doc));

        List<DocumentType> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserID());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnDocumentWhenExists() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439012");
        DocumentType doc = new DocumentType();
        doc.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(doc));

        Optional<DocumentType> result = service.findById(id.toHexString());

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(repository).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439013");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<DocumentType> result = service.findById(id.toHexString());

        assertFalse(result.isPresent());
    }

    @Test
    void findByUserId_shouldReturnList() {
        DocumentType doc = new DocumentType();
        doc.setUserID("userX");
        when(repository.findByUserID("userX")).thenReturn(Arrays.asList(doc));

        List<DocumentType> result = service.findByUserId("userX");

        assertEquals(1, result.size());
        assertEquals("userX", result.get(0).getUserID());
        verify(repository).findByUserID("userX");
    }

    @Test
    void create_shouldSaveAndReturnEntity() {
        DocumentType input = new DocumentType();
        input.setUserID("userC");
        when(repository.save(input)).thenReturn(input);

        DocumentType result = service.create(input);

        assertEquals("userC", result.getUserID());
        verify(repository).save(input);
    }

    @Test
    void update_shouldReturnUpdatedWhenExists() {
        DocumentType input = new DocumentType();
        ObjectId id = new ObjectId("507f1f77bcf86cd799439014");
        input.setId(id);
        input.setUserID("userUpd");
        when(repository.findById(id)).thenReturn(Optional.of(input));
        when(repository.save(input)).thenReturn(input);

        Optional<DocumentType> result = service.update(input);

        assertTrue(result.isPresent());
        assertEquals("userUpd", result.get().getUserID());
        verify(repository).findById(id);
        verify(repository).save(input);
    }

    @Test
    void update_shouldReturnEmptyWhenNotFound() {
        DocumentType input = new DocumentType();
        ObjectId id = new ObjectId("507f1f77bcf86cd799439015");
        input.setId(id);
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<DocumentType> result = service.update(input);

        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldReturnTrueWhenFoundAndDeleted() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439016");
        DocumentType doc = new DocumentType();
        doc.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(doc));
        doNothing().when(repository).deleteById(id);

        boolean result = service.delete(id.toHexString());

        assertTrue(result);
        verify(repository).deleteById(id);
    }

    @Test
    void delete_shouldReturnFalseWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439017");
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.delete(id.toHexString());

        assertFalse(result);
        verify(repository, never()).deleteById(any());
    }
    
}
