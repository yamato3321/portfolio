package com.example.portfolio.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.portfolio.entity.Task;
import com.example.portfolio.entity.User;
import com.example.portfolio.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // モック初期化
    }

    @Test
    public void testCreateTaskForUser() {
        // Arrange: テスト用のダミーデータを用意
        User user = new User();
        user.setId(1L);
        user.setName("テストユーザー");

        Task task = new Task();
        task.setTitle("テストタスク");

        // モックに対して何が起きてもいいようにしておく
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        taskService.createTaskForUser(task, user);

        // Assert: 保存処理が呼ばれたかどうかを検証
        verify(taskRepository, times(1)).save(task);
    }
}
