package com.virtukch.security.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 객체 변환 유틸리티 클래스입니다. 이 클래스는 Java 객체를 JSON 객체로 변환하는 기능을 제공합니다.
 *
 * @author Virtus_Chae
 */
public class ConvertUtil {

    /**
     * 주어진 객체를 JSON 객체로 변환하는 메서드입니다.
     *
     * @param obj 변환할 Java 객체
     * @return 변환된 JSON 객체
     * @throws RuntimeException JSON 변환 중 발생한 예외
     */
    public static Object convertObjectToJsonObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper(); // Jackson ObjectMapper 인스턴스 생성
        JSONParser parser = new JSONParser(); // JSONParser 인스턴스 생성
        String convertJsonString;
        Object convertObj;

        try {
            // Java 객체를 JSON 문자열로 변환
            convertJsonString = mapper.writeValueAsString(obj);
            convertObj = parser.parse(convertJsonString);
        } catch (JsonProcessingException | ParseException e) {
            throw new RuntimeException(e); // 예외 발생 시 RuntimeException 던짐
        }

        return convertObj;
    }
}