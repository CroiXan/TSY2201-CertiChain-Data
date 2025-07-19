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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.certichain.data.model.UserSubType;
import com.certichain.data.repository.UserSubTypeRepository;

class UserSubTypeServiceTest {

    private UserSubTypeRepository repository;
    private UserSubTypeService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserSubTypeRepository.class);
        service = new UserSubTypeService(repository);
    }

    @Test
    void getAll_shouldReturnList() {
        UserSubType u1 = new UserSubType();
        u1.setName("sub1");
        when(repository.findAll()).thenReturn(Arrays.asList(u1));

        List<UserSubType> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("sub1", result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void createUserSubType_shouldSaveAndReturn() {
        UserSubType newSub = new UserSubType();
        newSub.setName("nuevo");
        when(repository.save(newSub)).thenReturn(newSub);

        UserSubType result = service.createUserSubType(newSub);

        assertEquals("nuevo", result.getName());
        verify(repository).save(newSub);
    }

    @Test
    void getUserSubTypeById_found() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439211");
        UserSubType sub = new UserSubType();
        sub.setId(id);
        sub.setName("sub");
        when(repository.findById(id)).thenReturn(Optional.of(sub));

        Optional<UserSubType> result = service.getUserSubTypeById(id.toHexString());

        assertTrue(result.isPresent());
        assertEquals("sub", result.get().getName());
        verify(repository).findById(id);
    }

    @Test
    void getUserSubTypeById_notFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439212");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<UserSubType> result = service.getUserSubTypeById(id.toHexString());

        assertFalse(result.isPresent());
        verify(repository).findById(id);
    }

    @Test
    void updateUserSubType_shouldReturnUpdatedWhenExists() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439213");
        UserSubType input = new UserSubType();
        input.setId(id);
        input.setName("update");
        when(repository.findById(id)).thenReturn(Optional.of(input));
        when(repository.save(input)).thenReturn(input);

        Optional<UserSubType> result = service.updateUserSubType(input);

        assertTrue(result.isPresent());
        assertEquals("update", result.get().getName());
        verify(repository).findById(id);
        verify(repository).save(input);
    }

    @Test
    void updateUserSubType_shouldReturnEmptyWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439214");
        UserSubType input = new UserSubType();
        input.setId(id);
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<UserSubType> result = service.updateUserSubType(input);

        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteUserSubTypeById_shouldReturnFalseWhenNotFoundInitially() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439216");
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.deleteUserSubTypeById(id.toHexString());

        assertFalse(result);
        verify(repository).deleteById(id);
        verify(repository).findById(id);
    }

}
