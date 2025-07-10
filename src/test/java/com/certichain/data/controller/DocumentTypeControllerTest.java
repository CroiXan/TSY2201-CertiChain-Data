package com.certichain.data.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
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

import com.certichain.data.model.DocumentType;
import com.certichain.data.model.DocumentTypeApi;
import com.certichain.data.service.DocumentTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

class DocumentTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentTypeService documentTypeService;

    @InjectMocks
    private DocumentTypeController documentTypeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentTypeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        DocumentType doc1 = new DocumentType();
        doc1.setId(new ObjectId("507f1f77bcf86cd799439011"));
        doc1.setUserID("user1");
        doc1.setName("Name1");
        doc1.setState("active");
        DocumentType doc2 = new DocumentType();
        doc2.setUserID("user2");
        doc2.setName("Name2");
        doc2.setState("inactive");
        when(documentTypeService.findAll()).thenReturn(Arrays.asList(doc1, doc2));

        mockMvc.perform(get("/api/documenttypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439011"))
                .andExpect(jsonPath("$[0].userID").value("user1"))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[0].state").value("active"))
                .andExpect(jsonPath("$[1].id").doesNotExist())
                .andExpect(jsonPath("$[1].userID").value("user2"))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[1].state").value("inactive"));

        verify(documentTypeService, times(1)).findAll();
    }

    @Test
    void getById_found_shouldReturnDocument() throws Exception {
        DocumentType doc = new DocumentType();
        doc.setId(new ObjectId("507f1f77bcf86cd799439011"));
        doc.setUserID("user1");
        doc.setName("Name1");
        doc.setState("active");
        when(documentTypeService.findById("507f1f77bcf86cd799439011")).thenReturn(Optional.of(doc));

        mockMvc.perform(get("/api/documenttypes/507f1f77bcf86cd799439011"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439011"))
                .andExpect(jsonPath("$.userID").value("user1"))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.state").value("active"));

        verify(documentTypeService, times(1)).findById("507f1f77bcf86cd799439011");
    }

    @Test
    void getByUserId_shouldReturnList() throws Exception {
        DocumentType doc = new DocumentType();
        doc.setId(new ObjectId("507f1f77bcf86cd799439012"));
        doc.setUserID("userX");
        doc.setName("NameX");
        doc.setState("active");
        when(documentTypeService.findByUserId("userX")).thenReturn(Collections.singletonList(doc));

        mockMvc.perform(get("/api/documenttypes/user/userX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439012"))
                .andExpect(jsonPath("$[0].userID").value("userX"))
                .andExpect(jsonPath("$[0].name").value("NameX"))
                .andExpect(jsonPath("$[0].state").value("active"));

        verify(documentTypeService, times(1)).findByUserId("userX");
    }

    @Test
    void create_shouldReturnCreatedDocument() throws Exception {
        DocumentTypeApi apiRequest = new DocumentTypeApi();
        apiRequest.setUserID("user3");
        apiRequest.setName("Name3");
        apiRequest.setState("active");
        DocumentType created = new DocumentType();
        created.setId(new ObjectId("507f1f77bcf86cd799439013"));
        created.setUserID("user3");
        created.setName("Name3");
        created.setState("active");
        when(documentTypeService.create(any(DocumentType.class))).thenReturn(created);

        mockMvc.perform(post("/api/documenttypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439013"))
                .andExpect(jsonPath("$.userID").value("user3"))
                .andExpect(jsonPath("$.name").value("Name3"))
                .andExpect(jsonPath("$.state").value("active"));

        verify(documentTypeService, times(1)).create(any(DocumentType.class));
    }

    @Test
    void update_shouldReturnUpdatedDocument() throws Exception {
        DocumentTypeApi apiRequest = new DocumentTypeApi();
        apiRequest.setId("507f1f77bcf86cd799439014");
        apiRequest.setUserID("user4");
        apiRequest.setName("Name4");
        apiRequest.setState("inactive");

        DocumentType existing = new DocumentType();
        existing.setId(new ObjectId("507f1f77bcf86cd799439014"));
        when(documentTypeService.findById("507f1f77bcf86cd799439014"))
                .thenReturn(Optional.of(existing));

        DocumentType updatedEntity = new DocumentType();
        updatedEntity.setId(new ObjectId("507f1f77bcf86cd799439014"));
        updatedEntity.setUserID("user4");
        updatedEntity.setName("Name4");
        updatedEntity.setState("inactive");
        when(documentTypeService.update(any(DocumentType.class)))
                .thenReturn(Optional.of(updatedEntity));

        mockMvc.perform(put("/api/documenttypes/507f1f77bcf86cd799439014")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439014"))
                .andExpect(jsonPath("$.userID").value("user4"))
                .andExpect(jsonPath("$.name").value("Name4"))
                .andExpect(jsonPath("$.state").value("inactive"));

        verify(documentTypeService, times(1)).findById("507f1f77bcf86cd799439014");
        verify(documentTypeService, times(1)).update(any(DocumentType.class));
    }

    @Test
    void delete_notFound_shouldReturn404() throws Exception {
        when(documentTypeService.findById("invalid")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/documenttypes/invalid"))
                .andExpect(status().isNotFound());

        verify(documentTypeService, times(1)).findById("invalid");
        verify(documentTypeService, never()).delete(anyString());
    }

    @Test
    void delete_found_shouldReturnNoContent() throws Exception {
        DocumentType doc = new DocumentType();
        doc.setId(new ObjectId("507f1f77bcf86cd799439015"));
        when(documentTypeService.findById("507f1f77bcf86cd799439015")).thenReturn(Optional.of(doc));

        mockMvc.perform(delete("/api/documenttypes/507f1f77bcf86cd799439015"))
                .andExpect(status().isNoContent());

        verify(documentTypeService, times(1)).findById("507f1f77bcf86cd799439015");
        verify(documentTypeService, times(1)).delete("507f1f77bcf86cd799439015");
    }
}
