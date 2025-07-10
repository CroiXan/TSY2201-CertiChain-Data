package com.certichain.data.controller;

import java.util.Arrays;
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

import com.certichain.data.model.UserType;
import com.certichain.data.model.UserTypeApi;
import com.certichain.data.service.UserTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserTypeService userTypeService;

    @InjectMocks
    private UserTypeController userTypeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userTypeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        UserType t1 = new UserType();
        t1.setId(new ObjectId("507f1f77bcf86cd799439300"));
        t1.setName("TypeA");
        t1.setState("active");
        UserType t2 = new UserType();
        t2.setName("TypeB");
        t2.setState("inactive");

        when(userTypeService.getAll()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/userTypes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439300"))
            .andExpect(jsonPath("$[0].name").value("TypeA"))
            .andExpect(jsonPath("$[0].state").value("active"))
            .andExpect(jsonPath("$[1].id").doesNotExist())
            .andExpect(jsonPath("$[1].name").value("TypeB"))
            .andExpect(jsonPath("$[1].state").value("inactive"));

        verify(userTypeService, times(1)).getAll();
    }

    @Test
    void getById_shouldReturnUserType() throws Exception {
        UserType t = new UserType();
        t.setId(new ObjectId("507f1f77bcf86cd799439301"));
        t.setName("TypeX");
        t.setState("pending");

        when(userTypeService.getUserSubTypeById("507f1f77bcf86cd799439301"))
            .thenReturn(Optional.of(t));

        mockMvc.perform(get("/api/userTypes/507f1f77bcf86cd799439301"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439301"))
            .andExpect(jsonPath("$.name").value("TypeX"))
            .andExpect(jsonPath("$.state").value("pending"));

        verify(userTypeService, times(1)).getUserSubTypeById("507f1f77bcf86cd799439301");
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UserTypeApi input = new UserTypeApi();
        input.setName("NewType");
        input.setState("active");

        UserType created = new UserType();
        created.setId(new ObjectId("507f1f77bcf86cd799439302"));
        created.setName("NewType");
        created.setState("active");

        when(userTypeService.createUserType(any(UserType.class))).thenReturn(created);

        mockMvc.perform(post("/api/userTypes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439302"))
            .andExpect(jsonPath("$.name").value("NewType"))
            .andExpect(jsonPath("$.state").value("active"));

        verify(userTypeService, times(1)).createUserType(any(UserType.class));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        UserTypeApi input = new UserTypeApi();

        UserType updated = new UserType();
        updated.setId(new ObjectId("507f1f77bcf86cd799439303"));
        updated.setName("UpdType");
        updated.setState("inactive");

        when(userTypeService.updateUserSubType(any(UserType.class)))
            .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/userTypes/507f1f77bcf86cd799439303")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439303"))
            .andExpect(jsonPath("$.name").value("UpdType"))
            .andExpect(jsonPath("$.state").value("inactive"));

        verify(userTypeService, times(1)).updateUserSubType(any(UserType.class));
    }

    @Test
    void delete_found_shouldReturnNoContent() throws Exception {
        when(userTypeService.deleteUserSubTypeById("507f1f77bcf86cd799439304"))
            .thenReturn(true);

        mockMvc.perform(delete("/api/userTypes/507f1f77bcf86cd799439304"))
            .andExpect(status().isNoContent());

        verify(userTypeService, times(1)).deleteUserSubTypeById("507f1f77bcf86cd799439304");
    }

    @Test
    void delete_notFound_shouldReturn404() throws Exception {
        when(userTypeService.deleteUserSubTypeById("notExist"))
            .thenReturn(false);

        mockMvc.perform(delete("/api/userTypes/notExist"))
            .andExpect(status().isNotFound());

        verify(userTypeService, times(1)).deleteUserSubTypeById("notExist");
    }

}
