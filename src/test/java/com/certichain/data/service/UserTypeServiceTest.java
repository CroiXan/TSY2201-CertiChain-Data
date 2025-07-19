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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.certichain.data.model.UserType;
import com.certichain.data.repository.UserTypeRepository;

class UserTypeServiceTest {

    private UserTypeRepository repository;
    private UserTypeService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserTypeRepository.class);
        service = new UserTypeService(repository);
    }

    @Test
    void getAll_shouldReturnList() {
        UserType u1 = new UserType();
        u1.setName("Admin");
        when(repository.findAll()).thenReturn(Arrays.asList(u1));

        List<UserType> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void createUserType_shouldSaveAndReturn() {
        UserType u = new UserType();
        u.setName("Manager");
        when(repository.save(u)).thenReturn(u);

        UserType result = service.createUserType(u);

        assertEquals("Manager", result.getName());
        verify(repository).save(u);
    }

    @Test
    void getUserSubTypeById_found() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799449001");
        UserType u = new UserType();
        u.setId(id);
        u.setName("Guest");
        when(repository.findById(id)).thenReturn(Optional.of(u));

        Optional<UserType> result = service.getUserSubTypeById(id.toHexString());

        assertTrue(result.isPresent());
        assertEquals("Guest", result.get().getName());
        verify(repository).findById(id);
    }

    @Test
    void getUserSubTypeById_notFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799449002");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<UserType> result = service.getUserSubTypeById(id.toHexString());

        assertFalse(result.isPresent());
        verify(repository).findById(id);
    }

    @Test
    void updateUserSubType_shouldReturnUpdatedWhenExists() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799449003");
        UserType input = new UserType();
        input.setId(id);
        input.setName("Editor");
        when(repository.findById(id)).thenReturn(Optional.of(input));
        when(repository.save(input)).thenReturn(input);

        Optional<UserType> result = service.updateUserSubType(input);

        assertTrue(result.isPresent());
        assertEquals("Editor", result.get().getName());
        verify(repository).findById(id);
        verify(repository).save(input);
    }

    @Test
    void updateUserSubType_shouldReturnEmptyWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799449004");
        UserType input = new UserType();
        input.setId(id);
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<UserType> result = service.updateUserSubType(input);

        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteUserSubTypeById_shouldReturnFalseWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799449006");
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.deleteUserSubTypeById(id.toHexString());

        assertFalse(result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }
    
}
