package com.nagp.mcpclient.chatweather.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/chat")
public class WeatherChatController {

    private final ChatClient chatClient;

    @Value("classpath:templates/propmts/systemWeatherAgentPromptTemplate.st")
    Resource systemWeatherAgentPromptTemplate;

    public WeatherChatController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/weather")
    public ResponseEntity<String> chatWeather(@RequestHeader("username") String username, @RequestParam String message) {
        String response = chatClient.prompt()
                .system(systemWeatherAgentPromptTemplate)
                .user(message)
                .advisors(
                        advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)
                )
                .call().content();
        return ResponseEntity.ok(response);
    }

}
