package com.example.YoonONI_BackEnd.service;

import com.example.YoonONI_BackEnd.config.ChatGPTConfig;
import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.dto.ChatCompletionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService{

    private final ChatGPTConfig chatGPTConfig;

    private final RestTemplate restTemplate;


    @Value("${openai.url.model}")
    private String modelUrl;

    @Value("${openai.url.model-list}")
    private String modelListUrl;

    @Value("${openai.url.prompt}")
    private String promptUrl;

    @Value("${openai.url.legacy-prompt}")
    private String legacyPromptUrl;

    /**
     * 사용 가능한 모델 리스트를 조회하는 비즈니스 로직
     *
     * @return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> modelList() {
        log.info("[+] 모델 리스트를 조회합니다.");
        List<Map<String, Object>> resultList = null;

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP2] 통신을 위한 RestTemplate을 구성합니다.
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(modelUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        try {
            // [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> data = om.readValue(response.getBody(), new TypeReference<>() {
            });

            // [STEP4] 응답 값을 결과값에 넣고 출력을 해봅니다.
            resultList = (List<Map<String, Object>>) data.get("data");
            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object: " + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("JsonProcessingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultList;
    }



    @Override
    public Map<String, Object> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
        Map<String, Object> result = new HashMap<>();

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP2] 통신을 위한 RestTemplate을 구성합니다.
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(modelListUrl + "/" + modelName, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        try {
            // [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
            ObjectMapper om = new ObjectMapper();
            result = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return result;
    }


    /**
     * ChatGTP 프롬프트 검색
     *
     * @param completionDto completionDto
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> legacyPrompt(ChatCompletionDto completionDto) {
        log.debug("[+] 레거시 프롬프트를 수행합니다.");

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP5] 통신을 위한 RestTemplate을 구성합니다.
        HttpEntity<ChatCompletionDto> requestEntity = new HttpEntity<>(completionDto, headers);
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(legacyPromptUrl, HttpMethod.POST, requestEntity, String.class);

        Map<String, Object> resultMap = new HashMap<>();
        try {
            ObjectMapper om = new ObjectMapper();
            // [STEP6] String -> HashMap 역직렬화를 구성합니다.
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }


    /**
     * 신규 모델에 대한 프롬프트
     *
     * @param chatCompletionDto {}
     * @return chatCompletionDto
     */
    @Override
    public Map<String, Object> prompt(ChatCompletionDto chatCompletionDto) {
        log.debug("[+] 신규 프롬프트를 수행합니다.");

        Map<String, Object> resultMap = new HashMap<>();

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP5] 통신을 위한 RestTemplate을 구성합니다.
        HttpEntity<ChatCompletionDto> requestEntity = new HttpEntity<>(chatCompletionDto, headers);
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(promptUrl, HttpMethod.POST, requestEntity, String.class);
        try {
            // [STEP6] String -> HashMap 역직렬화를 구성합니다.
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }

    @Override
    public ResponseEntity<String> chat(RequestDataSet requestDataSet) throws JSONException {
        HttpHeaders headers = new HttpHeaders(); // HTTP 헤더 생성
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청 본문 타입 설정
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 수신할 응답 타입 설정
        String apiKey = ""; // 발급받은 API 키 설정 (여기서는 지움)
        headers.set("Authorization", "Bearer " + apiKey); // 인증 헤더에 API 키 추가

        JSONObject messageSystem = new JSONObject(); // 시스템 메시지 JSON 객체 생성
        messageSystem.put("role", "system");  // 역할 설정
        messageSystem.put("content", "너는 스프링 챗지피티 프로젝트 도우미야. 모든 답변은 간단한 자기소개 후에 해줘."); // 시스템 메시지 추가

        JSONObject messageUser = new JSONObject(); // 사용자 메시지 JSON 객체 생성
        messageUser.put("role", "user"); // 역할 설정
        messageUser.put("content", requestDataSet.inGetString("content")); // 사용자 메시지 추가

        JSONObject requestBody = new JSONObject(); // 요청 본문을 위한 JSON 객체 생성
        requestBody.put("model", "gpt-4o-2024-05-13"); // 사용할 모델 설정
        requestBody.put("messages", new JSONArray(Arrays.asList(messageSystem, messageUser))); // 메시지 배열 추가

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers); // HTTP 요청 엔티티 생성

        String apiEndpoint = "https://api.openai.com/v1/chat/completions"; // API 엔드포인트 설정
        try {
            // REST API 호출을 통해 응답 받기
            ResponseEntity<String> response = restTemplate.postForEntity(apiEndpoint, request, String.class);

            // 응답 상태 코드 확인
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response;  // 성공적인 응답 반환
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("api 호출 중 오류 발생!"); // 오류 메시지 반환
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("api 호출 중 예외 발생: " + e.getMessage()); // 예외 메시지 반환
        }
    }
    }
}