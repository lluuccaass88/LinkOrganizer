# 📎 LinkOrganizer

Aplicação Spring Boot que lê mensagens de canais do Discord, processa o conteúdo com IA (Gemini) e envia um resumo diário por e-mail.

---

## 🚀 Funcionalidades

- 📰 **Leitura de notícias** — lê mensagens de um canal de notícias no Discord das últimas 24h
- 🎬 **Leitura de reels** — lê links de reels de um canal do Discord e extrai título e e link
- 🤖 **Resumo com IA** — usa o Gemini (Google) para gerar título e resumo de cada notícia
- 📧 **Envio de e-mail** — envia um e-mail diário com notícias, reels e anotações formatados em HTML
- 📝 **Anotações categorizadas** — suporte a anotações com categorias definidas via enum

---

## 🏗️ Arquitetura

```
Controller → Service → Client
```

```
Discord (canal de notícias)  ──►  DiscordClient  ──►  GeminiClient  ──►  EmailService
Discord (canal de reels)     ──►  DiscordClient  ──►  ReelScraperClient  ──►  EmailService
```

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| **Spring Boot 3** | Framework principal |
| **JDA (Java Discord API)** | Leitura de mensagens do Discord |
| **Gemini API (Google)** | Geração de resumos com IA |
| **Spring Mail** | Envio de e-mails |
| **Thymeleaf** | Template HTML do e-mail |
| **Lombok** | Redução de boilerplate |
| **Gradle** | Gerenciamento de dependências |

---

## ⚙️ Configuração

### Pré-requisitos

- Java 17+
- Conta no [Discord Developer Portal](https://discord.com/developers/applications)
- Chave de API do [Google AI Studio](https://aistudio.google.com/apikey)
- Conta Gmail com [Senha de App](https://myaccount.google.com/apppasswords) configurada

### `application.yml`

```yaml
server:
  port: 8080

discord:
  token: SEU_TOKEN_DO_BOT
  channel-name: nome-do-canal-de-noticias
  reels-channel-name: nome-do-canal-de-reels

gemini:
  token: SUA_API_KEY_GEMINI

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: SEU_EMAIL@gmail.com
    password: SUA_SENHA_DE_APP
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

email:
  recipient: EMAIL_DESTINO@gmail.com
```

---

## ▶️ Como Rodar

```bash
./gradlew bootRun
```

---

## 📧 E-mail Diário

O e-mail é enviado automaticamente todo dia às **8h** com o seguinte conteúdo:

- 📰 Notícias do dia com título, resumo e link
- 🎬 Reels com título e link
- 📝 Anotações com categoria

Para disparar manualmente durante o desenvolvimento, chame o endpoint configurado em `ProcessorService`.

---

## 📂 Categorias de Anotações

| Enum | Slugs aceitos |
|---|---|
| `TRABALHO` | trabalho, Trabalho |
| `ESTUDOS` | escola, estudo, Escola, Estudo |
| `TECNOLOGIA` | tec, Tec, Tecnologia |
| `RECEITAS` | receita, Receitas |
| `RENDA_EXTRA` | renda extra, Renda extra |

---

## ⚠️ Observações

- O bot do Discord precisa da permissão **Message Content Intent** ativada no Developer Portal
- O Gemini possui limite de requisições no plano gratuito — use `gemini-2.0-flash-lite` para menor consumo de cota
