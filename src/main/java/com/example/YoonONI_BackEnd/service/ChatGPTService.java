package com.example.YoonONI_BackEnd.service;

import com.example.YoonONI_BackEnd.dto.ChatCompletionDto;

import java.util.List;
import java.util.Map;

public interface ChatGPTService {
    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> legacyPrompt(ChatCompletionDto completionDto);

    Map<String, Object> prompt(ChatCompletionDto chatCompletionDto);
}
