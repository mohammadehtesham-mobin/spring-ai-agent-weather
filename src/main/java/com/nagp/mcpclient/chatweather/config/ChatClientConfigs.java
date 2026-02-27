package com.nagp.mcpclient.chatweather.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfigs {
    @Bean
    public ChatClient groqChatClient(OpenAiChatModel chatModel, ChatMemory chatMemory
            , ToolCallbackProvider toolCallbackProvider) {
        ChatClient.Builder chaBuilder= ChatClient.builder(chatModel);
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chaBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

}
