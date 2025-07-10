package com.certichain.data.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.certichain.data.model.UserSubType;
import com.certichain.data.model.UserSubTypeApi;
import com.certichain.data.service.UserSubTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserSubTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserSubTypeService userSubTypeService;

    @InjectMocks
    private UserSubTypeController userSubTypeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userSubTypeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_withResults_shouldReturnList() throws Exception {
        UserSubType u1 = new UserSubType();
        u1.setId(new ObjectId("507f1f77bcf86cd799439200"));
        u1.setName("SubA");
        u1.setState("active");
        UserSubType u2 = new UserSubType();
        u2.setName("SubB");
        u2.setState("inactive");

        when(userSubTypeService.getAll()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/userSubTypes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439200"))
            .andExpect(jsonPath("$[0].name").value("SubA"))
            .andExpect(jsonPath("$[0].state").value("active"))
            .andExpect(jsonPath("$[1].id").doesNotExist())
            .andExpect(jsonPath("$[1].name").value("SubB"))
            .andExpect(jsonPath("$[1].state").value("inactive"));

        verify(userSubTypeService, times(1)).getAll();
    }

    @Test
    void getAll_empty_shouldReturnEmptyList() throws Exception {
        when(userSubTypeService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/userSubTypes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(0));

        verify(userSubTypeService, times(1)).getAll();
    }

    @Test
    void getById_found_shouldReturnSubType() throws Exception {
        UserSubType u = new UserSubType();
        u.setId(new ObjectId("507f1f77bcf86cd799439201"));
        u.setName("SubX");
        u.setState("pending");

        when(userSubTypeService.getUserSubTypeById("507f1f77bcf86cd799439201"))
            .thenReturn(Optional.of(u));

        mockMvc.perform(get("/api/userSubTypes/507f1f77bcf86cd799439201"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439201"))
            .andExpect(jsonPath("$.name").value("SubX"))
            .andExpect(jsonPath("$.state").value("pending"));

        verify(userSubTypeService, times(1)).getUserSubTypeById("507f1f77bcf86cd799439201");
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UserSubTypeApi input = new UserSubTypeApi();
        input.setName("NewSub");
        input.setState("active");

        UserSubType created = new UserSubType();
        created.setId(new ObjectId("507f1f77bcf86cd799439202"));
        created.setName("NewSub");
        created.setState("active");

        when(userSubTypeService.createUserSubType(any(UserSubType.class))).thenReturn(created);

        mockMvc.perform(post("/api/userSubTypes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439202"))
            .andExpect(jsonPath("$.name").value("NewSub"))
            .andExpect(jsonPath("$.state").value("active"));

        verify(userSubTypeService, times(1)).createUserSubType(any(UserSubType.class));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        UserSubTypeApi input = new UserSubTypeApi();

        UserSubType updatedEntity = new UserSubType();
        updatedEntity.setId(new ObjectId("507f1f77bcf86cd799439203"));
        updatedEntity.setName("UpdatedSub");
        updatedEntity.setState("inactive");

        when(userSubTypeService.updateUserSubType(any(UserSubType.class)))
            .thenReturn(Optional.of(updatedEntity));

        mockMvc.perform(put("/api/userSubTypes/507f1f77bcf86cd799439203")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439203"))
            .andExpect(jsonPath("$.name").value("UpdatedSub"))
            .andExpect(jsonPath("$.state").value("inactive"));

        verify(userSubTypeService, times(1)).updateUserSubType(any(UserSubType.class));
    }

    @Test
    void delete_found_shouldReturnNoContent() throws Exception {
        when(userSubTypeService.deleteUserSubTypeById("507f1f77bcf86cd799439204"))
            .thenReturn(true);

        mockMvc.perform(delete("/api/userSubTypes/507f1f77bcf86cd799439204"))
            .andExpect(status().isNoContent());

        verify(userSubTypeService, times(1)).deleteUserSubTypeById("507f1f77bcf86cd799439204");
    }

    @Test
    void delete_notFound_shouldReturn404() throws Exception {
        when(userSubTypeService.deleteUserSubTypeById("notexist"))
            .thenReturn(false);

        mockMvc.perform(delete("/api/userSubTypes/notexist"))
            .andExpect(status().isNotFound());

        verify(userSubTypeService, times(1)).deleteUserSubTypeById("notexist");
    }
}
