package org.example.tasklist.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.ResourceMappingException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.repository.DataSourceConfig;
import org.example.tasklist.repository.TaskRepository;
import org.example.tasklist.web.mappers.TaskRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.status          as task_status,
                   t.expiration_date as task_expiration_date
            FROM tasks t
            WHERE id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.status          as task_status,
                   t.expiration_date as task_expiration_date
            FROM tasks t
            JOIN users_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = ?
            """;

    private final String ASSIGN = """
            INSERT INTO users_tasks(task_id, user_id)
            VALUES (?, ?)
            """;

    private final String UPDATE = """
            UPDATE tasks
            SET title = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO tasks (title, description, expiration_date, status)
            VALUES (?,?,?,?)
            """;

    private final String DELETE = """
            DELETE from tasks
            WHERE id =?
            """;

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding task by id");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding all by user id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(ASSIGN);
            ps.setLong(1, taskId);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assigning to user");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                ps.setNull(3, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            ps.setString(4, task.getStatus().name());
            ps.setLong(5, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating task");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                ps.setNull(3, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            ps.setString(4, task.getStatus().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating task");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setLong(1, id);
           ps.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting task");
        }
    }
}
