package org.example.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.example.tasklist.web.dto.validation.OnCreate;
import org.example.tasklist.web.dto.validation.OnUpdate;
import org.hibernate.validator.constraints.Length;

public class UserDto {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull(message = "Password confirmation must be not null", groups = {OnCreate.class})
    private String passwordConfirmation;
}
