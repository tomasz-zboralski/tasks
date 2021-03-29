package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
class DbServiceTest {

    @Autowired
    DbService dbService;

    @Test
    void testSaveTask() {
        //Given
        Task task1 = new Task("test1", "test1");

        //When
        dbService.saveTask(task1);
        Long taskId = task1.getId();

        Optional<Task> savedTask = dbService.getTask(taskId);

        //Then
        assertTrue(savedTask.isPresent());

        //CleanUp
        dbService.deleteTask(taskId);
    }

    @Test
    void testGetAllTasks() {
        //Given
        Task task1 = new Task("test1", "test1");
        Task savedTask = dbService.saveTask(task1);

        //When
        List<Task> tasks = dbService.getAllTasks();

        //Then
        assertEquals(1, tasks.size());

        //CleanUp
        Long taskId = savedTask.getId();
        dbService.deleteTask(taskId);

    }

    @Test
    void testDeleteTask() {
        //Given
        Task task1 = new Task("test1", "test1");

        //When
        dbService.saveTask(task1);
        Long taskId = task1.getId();
        dbService.deleteTask(taskId);
        Optional<Task> savedTask = dbService.getTask(taskId);

        //Then
        assertFalse(savedTask.isPresent());
    }

    @Test
    void testGetTask() {
        //Given
        Task task1 = new Task("test1", "test1");
        dbService.saveTask(task1);

        //When
        Long taskId = task1.getId();
        Optional<Task> savedTask = dbService.getTask(taskId);

        //Then
        assertTrue(savedTask.isPresent());

        //CleanUp
        dbService.deleteTask(taskId);
    }
}