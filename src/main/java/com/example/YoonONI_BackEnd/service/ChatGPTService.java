package com.example.YoonONI_BackEnd.service;

import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.dto.ChatCompletionDto;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ChatGPTService {
    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> legacyPrompt(ChatCompletionDto completionDto);

    Map<String, Object> prompt(ChatCompletionDto chatCompletionDto);
    ResponseEntity<String> chat(RequestDataSet requestDataSet);
}
