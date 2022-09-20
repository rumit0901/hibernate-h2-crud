package com.in28minutes.springboot.jpa.hibernate.h2.example.student.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.in28minutes.springboot.jpa.hibernate.h2.example.student.DecodeHttpInputMessage;
import com.in28minutes.springboot.jpa.hibernate.h2.example.student.Student;

@ControllerAdvice
public class RequestAdvice extends RequestBodyAdviceAdapter  {
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
	  return methodParameter.hasParameterAnnotation(Base64Encoded.class);
	}

	/*
	@Override
	  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
	 
	      Map<String,String> input = mapper.convertValue(body, Map.class);	 
//	 
//	      System.out.println(input);
	      input.put("name", "Rumdev");
	 
	 
	      return mapper.convertValue(input, body.getClass());
	  }
	*/
	
	@Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

	  try (InputStream inputStream = inputMessage.getBody()) {

        // Read request body
        byte[] body = StreamUtils.copyToByteArray(inputStream);
        
//        log.info("raw: {}", new String(body));

        // Base64 Decode
        byte[] decodedBody = Base64.getDecoder().decode(body);
        
//        log.info("decode: {}", new String(decodedBody, StandardCharsets.UTF_8));

        // Return the decoded body
        return new DecodeHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(decodedBody));
    }
    }
}
