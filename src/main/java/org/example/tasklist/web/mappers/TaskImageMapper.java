package org.example.tasklist.web.mappers;

import org.example.tasklist.domain.task.TaskImage;
import org.example.tasklist.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {

}
