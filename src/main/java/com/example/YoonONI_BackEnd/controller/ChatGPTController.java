package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/chat-bot")
    public ResponseEntity<String> chatBot(RequestDataSet requestDataSet) {
        System.out.println(requestDataSet.inGetString("content"));
        return chatGPTService.chat(requestDataSet);
    }
}
