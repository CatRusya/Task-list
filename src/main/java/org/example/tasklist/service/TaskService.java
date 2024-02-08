package org.example.tasklist.service;

import org.example.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long id);
}
