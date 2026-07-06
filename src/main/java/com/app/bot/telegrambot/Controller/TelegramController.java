package com.app.bot.telegrambot.Controller;


import com.app.bot.telegrambot.Service.ConversationService;
import com.app.bot.telegrambot.Service.GrokService;
import com.app.bot.telegrambot.Service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/telegram")
    public class TelegramController {

        @Autowired
        private GrokService grokService;

        @Autowired
        private ConversationService conversationService;

        @Autowired
        private TelegramService telegramService;

        @PostMapping("/webhook")
        public String handleMessage(@RequestBody Map<String, Object> payload) {
            try {
                Map message = (Map) payload.get("message");
                if (message == null) return "OK";

                Map chat = (Map) message.get("chat");
                String chatId = chat.get("id").toString();
                String userText = (String) message.get("text");

                if (userText == null) return "OK";

                System.out.println("Telegram message from: " + chatId);
                System.out.println("Text: " + userText);

                // Save to Redis
                conversationService.saveMessage(chatId, "user", userText);

                // Get history
                List<String> history = conversationService.getHistory(chatId);
                String context = String.join("\n", history);

                // Get AI reply
                String aiReply = grokService.getReply(context);

                // Save AI reply
                conversationService.saveMessage(chatId, "assistant", aiReply);

                // Send reply
                telegramService.sendMessage(chatId, aiReply);

                System.out.println("=== TELEGRAM REPLY SENT ===");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "OK";
        }
    }

