package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void shouldReturnAllTasks() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);

        Mockito.when(taskService.getAllTasks(Mockito.any()))
                .thenReturn(new org.springframework.data.domain.PageImpl<>(Collections.singletonList(task)));

        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Test Task"))
                .andExpect(jsonPath("$.content[0].description").value("Test Description"))
                .andExpect(jsonPath("$.content[0].completed").value(false));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);

        Mockito.when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Task\",\"description\":\"Test Description\",\"completed\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void shouldReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Test Description\",\"completed\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Updated Task");
        task.setDescription("Updated Description");
        task.setCompleted(true);

        Mockito.when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Task\",\"description\":\"Updated Description\",\"completed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchTasks() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Search Task");
        task.setDescription("Search Description");
        task.setCompleted(false);

        Mockito.when(taskService.searchTasks("Search"))
                .thenReturn(Collections.singletonList(task));

        mockMvc.perform(get("/api/tasks/search?query=Search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Search Task"))
                .andExpect(jsonPath("$[0].description").value("Search Description"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    public void shouldReturnNotFoundForInvalidTaskId() throws Exception {
        Mockito.when(taskService.updateTask(eq(999L), any(Task.class)))
                .thenThrow(new com.example.exception.ResourceNotFoundException("Task not found with id: 999"));

        mockMvc.perform(put("/api/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Non-existent Task\",\"description\":\"Does not exist\",\"completed\":true}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id: 999"));
    }


}
