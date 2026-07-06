# 🤖 Telegram AI Agent

An AI-powered Telegram bot built with **Spring Boot** and **Groq LLaMA** that responds intelligently to user messages with Redis-backed conversation memory. Deployed 24/7 on Render.

---

## 🚀 Live Demo

> Try it on Telegram: [@PrathamAI_bot](https://t.me/PrathamAI_bot)

---

## 🏗️ Architecture

```
User (Telegram)
      ↓
Telegram Bot API
      ↓
Spring Boot Webhook (/telegram/webhook)
      ↓
Redis (Conversation History)
      ↓
Groq LLaMA API (llama-3.1-8b-instant)
      ↓
Reply sent back to User
```

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 4.1 |
| AI Model | Groq LLaMA (`llama-3.1-8b-instant`) |
| Messaging | Telegram Bot API |
| Memory | Redis (30-min TTL per session) |
| HTTP Client | Spring WebFlux (WebClient) |
| Containerization | Docker (Multi-stage build) |
| Deployment | Render (Free tier, 24/7) |

---

## ✨ Features

- 💬 **Real-time AI responses** via Groq LLaMA
- 🧠 **Conversation memory** — bot remembers context across messages (Redis, 30-min TTL)
- 🔄 **Webhook-based** — no polling, instant message delivery
- 🐳 **Dockerized** — multi-stage build for optimized image size
- 🔐 **Secure** — API keys via environment variables, never hardcoded
- 🌐 **24/7 Deployed** on Render

---

## 🛠️ Local Setup

### Prerequisites
- Java 17+
- Maven
- Docker (for Redis)
- Telegram Bot Token (from [@BotFather](https://t.me/BotFather))
- Groq API Key (from [console.groq.com](https://console.groq.com))
- ngrok (for webhook tunneling)

### 1. Clone the repository
```bash
git clone https://github.com/Prathamdiwakar/Telegram_AIBOT.git
cd Telegram_AIBOT
```

### 2. Start Redis
```bash
docker run -d -p 6379:6379 redis
```

### 3. Configure environment variables
In IntelliJ → Edit Configurations → Environment Variables:
```
TELEGRAM_BOT_TOKEN=your_telegram_bot_token
GROK_API_KEY=your_groq_api_key
REDIS_HOST=localhost
REDIS_PORT=6379
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```

### 5. Expose with ngrok
```bash
ngrok http 8080
```

### 6. Set Telegram Webhook
```bash
curl -X POST "https://api.telegram.org/bot{YOUR_TOKEN}/setWebhook" \
-H "Content-Type: application/json" \
-d '{"url": "https://your-ngrok-url.ngrok-free.app/telegram/webhook"}'
```

---

## 🐳 Docker Compose (Local)

```bash
docker-compose up
```

---

## ☁️ Deployment (Render)

1. Push code to GitHub
2. Create a **Key Value (Redis)** instance on Render
3. Create a **Web Service** on Render — connect GitHub repo
4. Add environment variables:
   - `TELEGRAM_BOT_TOKEN`
   - `GROK_API_KEY`
   - `REDIS_HOST` (Render internal Redis hostname)
   - `REDIS_PORT` (6379)
5. Deploy!
6. Set webhook to Render URL:
```bash
curl -X POST "https://api.telegram.org/bot{YOUR_TOKEN}/setWebhook" \
-H "Content-Type: application/json" \
-d '{"url": "https://your-app.onrender.com/telegram/webhook"}'
```

---

## 📁 Project Structure

```
src/main/java/com/app/bot/telegrambot/
│
├── Controller/
│   └── TelegramController.java    ← Webhook endpoint
│
├── Service/
│   ├── GrokService.java           ← Groq LLaMA API integration
│   ├── ConversationService.java   ← Redis conversation history
│   └── TelegramService.java       ← Send messages via Telegram API
│
└── TelegramBotApplication.java
```

---

## 🔑 Environment Variables

| Variable | Description |
|----------|-------------|
| `TELEGRAM_BOT_TOKEN` | Bot token from @BotFather |
| `GROK_API_KEY` | Groq API key |
| `REDIS_HOST` | Redis hostname |
| `REDIS_PORT` | Redis port (default: 6379) |

---

## 👨‍💻 Author

**Pratham Diwakar**
- GitHub: [@Prathamdiwakar](https://github.com/Prathamdiwakar)
- LinkedIn: [Pratham Diwakar](https://linkedin.com)
