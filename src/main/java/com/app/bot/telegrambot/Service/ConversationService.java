package com.app.bot.telegrambot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ConversationService {

        @Autowired
        private StringRedisTemplate redisTemplate;

        private static final String PREFIX = "chat:";
        private static final long TTL_MINUTES = 30;

        public void saveMessage(String userId, String role, String content) {
            String key = PREFIX + userId;
            String message = role + "::" + content;
            redisTemplate.opsForList().rightPush(key, message);
            redisTemplate.expire(key, TTL_MINUTES, TimeUnit.MINUTES);
        }

        public List<String> getHistory(String userId) {
            String key = PREFIX + userId;
            List<String> raw = redisTemplate.opsForList().range(key, -10, -1);
            List<String> messages = new ArrayList<>();
            if (raw == null) return messages;
            for (String entry : raw) {
                String[] parts = entry.split("::", 2);
                if (parts.length == 2) {
                    messages.add(parts[0] + ": " + parts[1]);
                }
            }
            return messages;
        }
    }


