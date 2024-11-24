package com.testapplication.desktop;


import com.testapplication.desktop.controllers.RequestController;
import com.testapplication.desktop.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    @Mock
    private TaskRepository taskRepository;


    @InjectMocks
    private RequestController requestController;


    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }


    @Test
    void getTask() throws Exception{
        mockMvc.perform(get("/api/read/1")).andExpect(status().isOk());
    }

    @Test
    void postTask() throws Exception{
        mockMvc.perform(get("/api/add?title=тестовая задача1&description=отправить запрос&status=в процессе&priority=высокий&comment&author=Гаджимурад&executor=постман")).andExpect(status().isOk());
    }
    @Test
    void deleteTask() throws Exception{
        mockMvc.perform(get("/api/delete/1?title=тестовая задача1&description=отправить запрос&status=в процессе&priority=низкий&comment&author=Гаджимурад&executor=постман")).andExpect(status().isOk());
    }
    @Test
    void updateTask() throws Exception{
        mockMvc.perform(get("/api/update/1")).andExpect(status().isOk());
    }
    @Test
    void addUser() throws Exception{
        mockMvc.perform(get("/new-user")).andExpect(status().isOk());
    }
}
