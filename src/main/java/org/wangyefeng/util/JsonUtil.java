package org.wangyefeng.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class JsonUtil {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
	}
	
	public static String toJson(Object data) {
		if (data == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T parseJson(String json, Class<T> clazz) {
		if (json == null) {
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseJson(String json, TypeReference<T> typeReference){
		if (json == null) {
			return null;
		}
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
