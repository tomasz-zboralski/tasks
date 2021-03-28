package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "test name", "test content");
        TaskDto taskDto = new TaskDto(1L, "Dto name", "Dto content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTask(task.getId())).thenReturn(Optional.ofNullable(task));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Dto name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Dto content")));
    }



    @Test
    void shouldGetTasks() throws Exception {

        //Given
        List<Task> taskList = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(taskList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "test title", "test content");
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test content")));

    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "test name", "test content");
        TaskDto taskDto = new TaskDto(1L, "Dto name", "Dto content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTask(task.getId())).thenReturn(Optional.ofNullable(task));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception {

        //Given
        Task task = new Task(1L, "test  title", "test content");
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test content")));

    }

}