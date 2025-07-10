package com.certichain.data.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import com.certichain.data.model.UserData;
import com.certichain.data.model.UserDataApi;
import com.certichain.data.service.UserDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserDataService userDataService;

    @InjectMocks
    private UserDataController userDataController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userDataController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        UserData u1 = new UserData();
        u1.setId(new ObjectId("507f1f77bcf86cd799439100"));
        u1.setUserID("user1");
        u1.setName("Name1");
        u1.setUserTypeId("type1");
        u1.setUserSubTypeId("sub1");
        u1.setStatus("active");
        UserData u2 = new UserData();
        u2.setUserID("user2");
        u2.setName("Name2");
        u2.setUserTypeId("type2");
        u2.setUserSubTypeId("sub2");
        u2.setStatus("inactive");

        when(userDataService.findAll()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/userdata"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439100"))
                .andExpect(jsonPath("$[0].userID").value("user1"))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[0].userTypeId").value("type1"))
                .andExpect(jsonPath("$[0].userSubTypeId").value("sub1"))
                .andExpect(jsonPath("$[0].status").value("active"))
                .andExpect(jsonPath("$[1].id").doesNotExist())
                .andExpect(jsonPath("$[1].userID").value("user2"))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[1].userTypeId").value("type2"))
                .andExpect(jsonPath("$[1].userSubTypeId").value("sub2"))
                .andExpect(jsonPath("$[1].status").value("inactive"));

        verify(userDataService, times(1)).findAll();
    }

    @Test
    void getById_found_shouldReturnUser() throws Exception {
        UserData u = new UserData();
        u.setId(new ObjectId("507f1f77bcf86cd799439101"));
        u.setUserID("userX");
        u.setName("NameX");
        u.setUserTypeId("typeX");
        u.setUserSubTypeId("subX");
        u.setStatus("active");

        when(userDataService.findById("507f1f77bcf86cd799439101"))
                .thenReturn(Optional.of(u));

        mockMvc.perform(get("/api/userdata/507f1f77bcf86cd799439101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439101"))
                .andExpect(jsonPath("$.userID").value("userX"))
                .andExpect(jsonPath("$.name").value("NameX"))
                .andExpect(jsonPath("$.userTypeId").value("typeX"))
                .andExpect(jsonPath("$.userSubTypeId").value("subX"))
                .andExpect(jsonPath("$.status").value("active"));

        verify(userDataService, times(1)).findById("507f1f77bcf86cd799439101");
    }

    @Test
    void getById_notFound_shouldReturn404() throws Exception {
        when(userDataService.findById("nope")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/userdata/nope"))
                .andExpect(status().isNotFound());

        verify(userDataService, times(1)).findById("nope");
    }

    @Test
    void getByUserID_found_shouldReturnUser() throws Exception {
        UserData u = new UserData();
        u.setId(new ObjectId("507f1f77bcf86cd799439102"));
        u.setUserID("abc123");
        u.setName("NameA");
        u.setUserTypeId("typeA");
        u.setUserSubTypeId("subA");
        u.setStatus("pending");

        when(userDataService.findByUserID("abc123"))
                .thenReturn(Optional.of(u));

        mockMvc.perform(get("/api/userdata/userid/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439102"))
                .andExpect(jsonPath("$.userID").value("abc123"))
                .andExpect(jsonPath("$.name").value("NameA"))
                .andExpect(jsonPath("$.userTypeId").value("typeA"))
                .andExpect(jsonPath("$.userSubTypeId").value("subA"))
                .andExpect(jsonPath("$.status").value("pending"));

        verify(userDataService, times(1)).findByUserID("abc123");
    }

    @Test
    void getByUserID_notFound_shouldReturn404() throws Exception {
        when(userDataService.findByUserID("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/userdata/userid/missing"))
                .andExpect(status().isNotFound());

        verify(userDataService, times(1)).findByUserID("missing");
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UserDataApi input = new UserDataApi();
        input.setUserID("newUser");
        input.setName("New Name");
        input.setUserTypeId("t1");
        input.setUserSubTypeId("s1");
        input.setStatus("ok");

        UserData created = new UserData();
        created.setId(new ObjectId("507f1f77bcf86cd799439103"));
        created.setUserID("newUser");
        created.setName("New Name");
        created.setUserTypeId("t1");
        created.setUserSubTypeId("s1");
        created.setStatus("ok");

        when(userDataService.create(any(UserData.class))).thenReturn(created);

        mockMvc.perform(post("/api/userdata")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439103"))
                .andExpect(jsonPath("$.userID").value("newUser"))
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.userTypeId").value("t1"))
                .andExpect(jsonPath("$.userSubTypeId").value("s1"))
                .andExpect(jsonPath("$.status").value("ok"));

        verify(userDataService, times(1)).create(any(UserData.class));
    }

    @Test
    void update_found_shouldReturnUpdated() throws Exception {
        UserDataApi input = new UserDataApi();
        input.setId("507f1f77bcf86cd799439104");
        input.setUserID("updUser");
        input.setName("Upd Name");
        input.setUserTypeId("t2");
        input.setUserSubTypeId("s2");
        input.setStatus("done");

        UserData upd = new UserData();
        upd.setId(new ObjectId("507f1f77bcf86cd799439104"));
        upd.setUserID("updUser");
        upd.setName("Upd Name");
        upd.setUserTypeId("t2");
        upd.setUserSubTypeId("s2");
        upd.setStatus("done");

        when(userDataService.update(eq("507f1f77bcf86cd799439104"), any(UserData.class)))
                .thenReturn(Optional.of(upd));

        mockMvc.perform(put("/api/userdata/507f1f77bcf86cd799439104")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("507f1f77bcf86cd799439104"))
                .andExpect(jsonPath("$.userID").value("updUser"))
                .andExpect(jsonPath("$.name").value("Upd Name"))
                .andExpect(jsonPath("$.userTypeId").value("t2"))
                .andExpect(jsonPath("$.userSubTypeId").value("s2"))
                .andExpect(jsonPath("$.status").value("done"));

        verify(userDataService, times(1)).update(eq("507f1f77bcf86cd799439104"), any(UserData.class));
    }

    @Test
    void update_notFound_shouldReturn404() throws Exception {
        UserDataApi input = new UserDataApi();
        input.setUserID("none");
        when(userDataService.update(eq("noid"), any(UserData.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/userdata/noid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());

        verify(userDataService, times(1)).update(eq("noid"), any(UserData.class));
    }

    @Test
    void delete_found_shouldReturnNoContent() throws Exception {
        UserData u = new UserData();
        u.setId(new ObjectId("507f1f77bcf86cd799439105"));
        when(userDataService.findById("507f1f77bcf86cd799439105"))
                .thenReturn(Optional.of(u));

        mockMvc.perform(delete("/api/userdata/507f1f77bcf86cd799439105"))
                .andExpect(status().isNoContent());

        verify(userDataService, times(1)).findById("507f1f77bcf86cd799439105");
        verify(userDataService, times(1)).deleteById("507f1f77bcf86cd799439105");
    }

    @Test
    void delete_notFound_shouldReturn404() throws Exception {
        when(userDataService.findById("missing")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/userdata/missing"))
                .andExpect(status().isNotFound());

        verify(userDataService, times(1)).findById("missing");
        verify(userDataService, never()).deleteById(anyString());
    }

    @Test
    void getByUserTypeId_empty_shouldReturnNoContent() throws Exception {
        when(userDataService.findByUserTypeId("tX")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/userdata/type/tX"))
                .andExpect(status().isNoContent());

        verify(userDataService, times(1)).findByUserTypeId("tX");
    }

    @Test
    void getByUserTypeId_withResults_shouldReturnList() throws Exception {
        UserData u1 = new UserData();
        u1.setId(new ObjectId("507f1f77bcf86cd799439106"));
        u1.setUserID("uA");
        u1.setName("nA");
        u1.setUserTypeId("tA");
        u1.setUserSubTypeId("sA");
        u1.setStatus("stA");
        List<UserData> list = Collections.singletonList(u1);

        when(userDataService.findByUserTypeId("tA")).thenReturn(list);

        mockMvc.perform(get("/api/userdata/type/tA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("507f1f77bcf86cd799439106"))
                .andExpect(jsonPath("$[0].userID").value("uA"))
                .andExpect(jsonPath("$[0].name").value("nA"))
                .andExpect(jsonPath("$[0].userTypeId").value("tA"))
                .andExpect(jsonPath("$[0].userSubTypeId").value("sA"))
                .andExpect(jsonPath("$[0].status").value("stA"));

        verify(userDataService, times(1)).findByUserTypeId("tA");
    }

}
