package com.app.bot.telegrambot.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

    @Service
    public class TelegramService {

        @Value("${telegram.bot.token}")
        private String botToken;

        private final WebClient webClient = WebClient.builder().build();

        public void sendMessage(String chatId, String text) {
            Map<String, Object> body = Map.of(
                    "chat_id", chatId,
                    "text", text
            );

            webClient.post()
                    .uri("https://api.telegram.org/bot" + botToken + "/sendMessage")
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }

