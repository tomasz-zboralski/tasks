package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");
        Task task = taskMapper.mapToTask(taskDto);

        //When
        Long testedId = task.getId();
        String testedTitle = task.getTitle();
        String testedContent = task.getContent();

        //Then
        assertEquals(1L, testedId);
        assertEquals("test title", testedTitle);
        assertEquals("test content", testedContent);
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test title", "test content");
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //When
        Long testedId = taskDto.getId();
        String testedTitle = taskDto.getTitle();
        String testedContent = taskDto.getContent();

        //Then
        assertEquals(1L, testedId);
        assertEquals("test title", testedTitle);
        assertEquals("test content", testedContent);
    }

    @Test
    void MapToTaskDtoList() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "test title", "test content"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertThat(taskDtoList).isNotNull();
        assertThat(taskDtoList.size()).isEqualTo(1);

        taskDtoList.forEach(taskDto -> {
            assertThat(taskDto.getId()).isEqualTo(1L);
            assertThat(taskDto.getTitle()).isEqualTo("test title");
            assertThat(taskDto.getContent()).isEqualTo("test content");
        });
    }
}
