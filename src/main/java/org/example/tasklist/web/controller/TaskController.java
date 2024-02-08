package org.example.tasklist.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.service.TaskService;
import org.example.tasklist.web.dto.task.TaskDto;
import org.example.tasklist.web.dto.validation.OnUpdate;
import org.example.tasklist.web.mappers.TaskMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class)@RequestBody TaskDto taskDto){
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.delete(id);
    }
}
