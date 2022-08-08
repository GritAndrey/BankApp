package ru.gritandrey.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static ru.gritandrey.notification.config.RabbitMQConfig.QUEUE_DEPOSIT;

@Service
@RequiredArgsConstructor
public class DepositMessageHandler {
    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        final var body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        final var depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        System.out.println(depositResponseDto);

        final var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(depositResponseDto.getEmail());
        simpleMailMessage.setFrom("test@xyz.com");
        simpleMailMessage.setSubject("Deposit");
        simpleMailMessage.setText("Make deposit sum: " + depositResponseDto.getAmount());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}

