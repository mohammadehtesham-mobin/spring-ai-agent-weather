# Spring AI Weather Agent (MCP Client)

A **Spring Boot AI Agent** built using **Spring AI** that acts as an **MCP Client**.
The agent understands natural language weather queries and retrieves **real-time weather data** by calling an external **Weather MCP Server**.

This agent is designed with a **restricted scope**:

* Answers **weather-related queries only**
* Supports **cities in the United States only**
* Uses **LLM for intent understanding**
* Fetches data via **MCP tool calls (no hallucinated weather)**

---

## Related Projects

**MCP Server (Weather Tool Provider)**
https://github.com/mohammadehtesham-mobin/spring-ai-mcp-weather

This client connects to the above server, which fetches data from **weather.gov**.

---

## Architecture Overview

```
User Request
     ↓
REST API (Spring Boot)
     ↓
Spring AI ChatClient
     ↓
LLM (intent understanding)
     ↓
MCP Tool Invocation
     ↓
Weather MCP Server
     ↓
api.weather.gov API
     ↓
Response returned to user
```

### Flow

1. User sends a natural language query
2. LLM identifies weather intent
3. LLM calls MCP weather tool
4. MCP Server fetches data from weather.gov
5. Response is returned to the user

---

## Features

* Natural language weather queries
* MCP Client implementation using Spring AI
* Tool-based architecture (no direct weather generation)
* Scope control:

    * Only **weather-related questions**
    * Only **US cities supported**
* REST API interface
* Header-based user identification

---

## Example Request

### cURL

```
curl --location 'http://localhost:8080/chat/weather?message=what%20is%20weather%20today%20in%20New%20York' \
--header 'username: USER001'
```

### Sample Response

```
The current weather in New York is 40F and sunny. The forecast for tonight is partly cloudy with a low of 32F, and for tomorrow, it's mostly sunny with a high of 46F.
```

---

## API Details

**Endpoint**

```
GET /chat/weather
```

**Query Parameters**

| Parameter | Description                           |
| --------- | ------------------------------------- |
| message   | User's natural language weather query |

**Headers**

| Header   | Description     |
| -------- | --------------- |
| username | User identifier |

---

## Supported Queries

Examples:

* Weather in New York
* Forecast for Chicago today
* Temperature in Los Angeles
* Weather tomorrow in Boston

---

## Unsupported Scenarios

The agent will reject or limit responses for:

* Non-weather queries
  Example: *"Tell me a joke"*

* Non-US cities
  Example: *"Weather in London"*

* Unsupported domains (news, finance, etc.)

---

# Configuration

The application is configured using **environment variables** and values from `application.properties`.

---

## Environment Variables

| Variable | Description | Default Value |
|---------|-------------|---------------|
| `OPEN_AI_API_KEY` | API key for the LLM provider | — |
| `OPEN_AI_BASE_URL` | Base URL for OpenAI-compatible endpoint (e.g., Groq or other providers) | — |
| `OPEN_AI_PROVIDER` | Enable OpenAI chat client (`true` or `false`) | `false` |
| `OPEN_AI_CHAT_MODEL` | Model name used by the application | `llama-3.3-70b-versatile` |
| `MCP_SERVER_URL` | SSE endpoint of the Weather MCP Server | `http://localhost:8090/sse` |

---

## Application Defaults

| Setting | Value |
|--------|-------|
| Application Name | `chatweather` |
| Server Port | `8080` |
| MCP Connection Type | SSE (Server-Sent Events) |
| Chat Memory | Persistent (JDBC-based) |

---

## Chat Memory (H2 Database)

The agent stores conversation history using a local **H2 database**.

| Property | Default |
|---------|---------|
| Database URL | `jdbc:h2:file:C:\localStore\h2db\chatWeatherMemory` |
| Username | `admin` |
| Password | `iamlazy` |
| H2 Console | http://localhost:8080/h2-console |

The schema is automatically initialized during application startup.

---

## Example Environment Setup

```bash
OPEN_AI_API_KEY=your_api_key
OPEN_AI_BASE_URL=https://api.groq.com/openai
OPEN_AI_PROVIDER=false
OPEN_AI_CHAT_MODEL=llama-3.3-70b-versatile
MCP_SERVER_URL=http://localhost:8090/sse
````


Make sure the **MCP Weather Server** is running before starting this application.

---

## How to Run

### 1. Clone the repository

```
git clone https://github.com/mohammadehtesham-mobin/spring-ai-agent-weather.git
cd spring-ai-agent-weather
```

### 2. Start MCP Weather Server

Follow instructions in:
https://github.com/mohammadehtesham-mobin/spring-ai-mcp-weather

### 3. Run the application

```
mvn clean install
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## System Behavior & Guardrails

The agent is designed with strict constraints:

* Only weather-related responses
* Only US city support
* Always uses MCP tool for weather data
* Does not generate weather information on its own
* Returns fallback messages for unsupported requests

---

## Technology Stack

* Java 17
* Spring Boot
* Spring AI
* MCP (Model Context Protocol)
* OpenAI-compatible LLM
* Maven

---


## Use Cases

* Demonstration of Spring AI + MCP integration
* Example of tool-based AI agent architecture
* Learning project for AI agents in Java
* Portfolio project for AI/Backend roles

---

## Author

**Md Ehtesham Mobin**

---

## License

This project is for learning and demonstration purposes.
