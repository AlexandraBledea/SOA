package com.ubbcluj.task.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubbcluj.task.client.RabbitClient;
import com.ubbcluj.task.dto.*;
import com.ubbcluj.task.persistence.entity.TaskEntity;
import com.ubbcluj.task.persistence.entity.UserEntity;
import com.ubbcluj.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadlineReminderJob {

    private final TaskService taskService;

    private final RabbitClient rabbitClient;

    @Value("${lambdaURL}")
    private String lambdaUrl;

    private final RestTemplate restTemplate = new RestTemplate();

//    @Scheduled(cron = "0 * * * * ?") // Runs at the start of every minute
    public void reminder() throws JsonProcessingException {
        log.info("Reminder Job started");
        List<TaskEntity> tasks = this.taskService.getAllTasksEntity();
        for (TaskEntity task: tasks) {
            DeadlineReminderDto deadlineReminderDto = new DeadlineReminderDto(task.getDueDate().toString(), task.getTitle());

            log.info("Sending request to Lambda: {}", new ObjectMapper().writeValueAsString(deadlineReminderDto));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entityLambda =
                    new HttpEntity<>(deadlineReminderDto, headers);

            ResponseEntity<DeadlineReminderResponseDto> responseLambda =
                    restTemplate.exchange(lambdaUrl, HttpMethod.POST, entityLambda, DeadlineReminderResponseDto.class);

            DeadlineReminderResponseDto deadlineReminderResponseDto = responseLambda.getBody();
            log.info("DeadlineReminderResponseDto: {}", deadlineReminderResponseDto);
            if (!deadlineReminderResponseDto.reminder().isEmpty()) {
                if (task.getAssignedTo() != null) {
                    sendEmail(task.getAssignedTo(), deadlineReminderResponseDto.reminder());
                } else {
                    sendEmail(task.getCreatedBy(), deadlineReminderResponseDto.reminder());
                }
            }
        }
        log.info("Reminder Job ended");
    }

    public void sendEmail(UserEntity userEntity, String emailDescription) {
        EmailNotificationDto emailNotificationDto = new EmailNotificationDto(userEntity.getEmail(), "Deadline approaching fast!", emailDescription, LocalDate.now());
        rabbitClient.sendMessage(emailNotificationDto);
    }

}
