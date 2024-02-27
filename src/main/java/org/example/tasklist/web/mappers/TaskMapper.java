package org.example.tasklist.web.mappers;

import org.example.tasklist.domain.task.Task;
import org.example.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {

}
