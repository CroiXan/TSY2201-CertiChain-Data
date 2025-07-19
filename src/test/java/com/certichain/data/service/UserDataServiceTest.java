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

import com.certichain.data.model.UserData;
import com.certichain.data.repository.UserDataRepository;

public class UserDataServiceTest {

    private UserDataRepository userDataRepository;
    private UserDataService userDataService;

    @BeforeEach
    void setUp() {
        userDataRepository = mock(UserDataRepository.class);
        userDataService = new UserDataService(userDataRepository);
    }

    @Test
    void findAll_shouldReturnList() {
        UserData u1 = new UserData();
        u1.setUserID("user1");
        when(userDataRepository.findAll()).thenReturn(Arrays.asList(u1));

        List<UserData> result = userDataService.findAll();

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserID());
        verify(userDataRepository).findAll();
    }

    @Test
    void findById_shouldReturnUserWhenExists() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439111");
        UserData user = new UserData();
        user.setId(id);
        when(userDataRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<UserData> result = userDataService.findById(id.toHexString());

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(userDataRepository).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439112");
        when(userDataRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UserData> result = userDataService.findById(id.toHexString());

        assertFalse(result.isPresent());
    }

    @Test
    void findByUserID_shouldReturnUser() {
        UserData user = new UserData();
        user.setUserID("userX");
        when(userDataRepository.findByUserID("userX")).thenReturn(Optional.of(user));

        Optional<UserData> result = userDataService.findByUserID("userX");

        assertTrue(result.isPresent());
        assertEquals("userX", result.get().getUserID());
        verify(userDataRepository).findByUserID("userX");
    }

    @Test
    void create_shouldSaveAndReturn() {
        UserData newUser = new UserData();
        newUser.setUserID("userC");
        when(userDataRepository.save(newUser)).thenReturn(newUser);

        UserData result = userDataService.create(newUser);

        assertEquals("userC", result.getUserID());
        verify(userDataRepository).save(newUser);
    }

    @Test
    void update_shouldReturnUpdatedWhenExists() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439113");
        UserData input = new UserData();
        input.setId(id);
        input.setUserID("userUp");
        when(userDataRepository.findById(id)).thenReturn(Optional.of(input));
        when(userDataRepository.save(input)).thenReturn(input);

        Optional<UserData> result = userDataService.update(id.toHexString(), input);

        assertTrue(result.isPresent());
        assertEquals("userUp", result.get().getUserID());
        verify(userDataRepository).findById(id);
        verify(userDataRepository).save(input);
    }

    @Test
    void update_shouldReturnEmptyWhenNotFound() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439114");
        UserData input = new UserData();
        input.setId(id);
        when(userDataRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UserData> result = userDataService.update(id.toHexString(), input);

        assertFalse(result.isPresent());
        verify(userDataRepository).findById(id);
        verify(userDataRepository, never()).save(any());
    }

    @Test
    void deleteById_shouldInvokeRepositoryDelete() {
        ObjectId id = new ObjectId("507f1f77bcf86cd799439115");

        userDataService.deleteById(id.toHexString());

        verify(userDataRepository).deleteById(id);
    }

    @Test
    void findByUserTypeId_shouldReturnList() {
        UserData u1 = new UserData();
        u1.setUserTypeId("type1");
        when(userDataRepository.findAllByUserTypeId("type1")).thenReturn(Arrays.asList(u1));

        List<UserData> result = userDataService.findByUserTypeId("type1");

        assertEquals(1, result.size());
        assertEquals("type1", result.get(0).getUserTypeId());
        verify(userDataRepository).findAllByUserTypeId("type1");
    }
    
}
