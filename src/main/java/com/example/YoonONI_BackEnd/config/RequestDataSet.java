package com.example.YoonONI_BackEnd.config;

import com.example.YoonONI_BackEnd.config.util.DateUtil;
import com.example.YoonONI_BackEnd.service.auth.UserDetailsImpl;
import com.example.YoonONI_BackEnd.service.auth.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class RequestDataSet {

    // 클라이언트로부터 들어온 파라미터를 저장
    private Map<String, Object> input = new HashMap<>();

    @Setter
    private UserDetailsServiceImpl userService;

    @Setter
    private UserDetailsImpl userDetails;

    public void arrangeInputParameters(HttpServletRequest request) {
        input.clear();

        String contentType = request.getContentType();

        try{
            if(contentType != null && contentType.contains("application/json")) {
                // JSON 본문 처리
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                String jsonBody = sb.toString();

                ObjectMapper objectMapper = new ObjectMapper();

                TypeReference<Map<String, Object>> stringType = new TypeReference<>() {};
                Map<String, Object> jsonMap = objectMapper.readValue(jsonBody, stringType);

                if (jsonMap != null) {
                    input.putAll(jsonMap);
                }
            } else {
                Map<String, String[]> paramMap = request.getParameterMap();

                for (String key : paramMap.keySet()) {
                    String[] values = paramMap.get(key);
                    String cleanKey = key.toLowerCase();

                    // 배열 값 처리
                    if (values.length > 1) {
                        input.put(cleanKey, values);
                        input.put(cleanKey + "_str", String.join("#", values));
                    } else {
                        String value = values[0];
                        input.put(cleanKey, value);
                    }

                    // 특별 키 변경 로직
                    if ("page".equals(key)) input.put("ctrl_page_num", values[0]);
                    if ("rows".equals(key)) input.put("ctrl_max_list_count", values[0]);
                }

                // 예: 기본 날짜값 세팅
                if (!input.containsKey("s_current_dt")) {
                    input.put("s_current_dt", new java.text.SimpleDateFormat("yyyyMMdd").format(new Date()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse request body", e);
        }

        setBaseInput(request, input);
    }

    private void setBaseInput(HttpServletRequest request, Map<String, Object> map) {
        map.put("ctrl_ip", request.getRemoteAddr());

        String url = request.getRequestURL().toString();

        String uri = request.getRequestURI();

        String host = StringUtils.removeEnd(url, uri);
        host = StringUtils.endsWith(host, ":80") ? StringUtils.removeEnd(host, ":80") : host;

        map.put("ctrl_request_uri", StringUtils.removeEnd(uri, "/"));
        map.put("ctrl_request_url", url);
        map.put("ctrl_host", host);
        map.put("ctrl_method", request.getMethod());
        map.put("ctrl_query_string", request.getQueryString());

        // forLog
        map.put("ctrl_req_date", DateUtil.getCurrentDate("yyMMddHHmmssSSS"));

        if ("#".equals(inGetString("ctrl_action_url"))) {
            inSet("ctrl_action_url", uri);
        }

        }

    public void setUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(input.containsKey("userId")) return;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetailsImpl userDetails) {
                input.put("userId", userDetails.getUsername());
            } else if (principal instanceof String && !"anonymousUser".equals(principal)) {
                input.put("userId", principal.toString());
            }
        }
    }



    public void inSetIfBlank(String key, Object val) {
        if (this.input.containsKey(key)) {
            if (StringUtils.isBlank(inGetString(key))) {
                inSet(key, val);
            }
        } else {
            inSet(key, val);
        }
    }

    public String inGetString(String key) {
        Object value = input.get(key);
        return value != null ? value.toString() : "";
    }

    public void inSet(String key, Object value) {
        input.put(key.toLowerCase(), value);
    }

    public void inSetIfNotKey(String key, Object value) {
        if (!input.containsKey(key.toLowerCase())) {
            input.put(key.toLowerCase(), value);
        }
    }

    public void trim(String... fields) {
        for (String field : fields) {
            if (input.containsKey(field)) {
                Object value = input.get(field);
                if (value instanceof String) {
                    input.put(field, ((String) value).trim());
                }
            }
        }
    }

    public void removeSymbol(String symbol, String... fields) {
        for (String field : fields) {
            if (input.containsKey(field)) {
                Object value = input.get(field);
                if (value instanceof String) {
                    input.put(field, ((String) value).replace(symbol, ""));
                }
            }
        }
    }

    public String printInputParams() {
        return this.input.toString();
    }
}




