package com.oen.core.response.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oen.core.domain.model.AppVersion;

public class AppVersionFilter {
	
	private static String[] IGNORABLE_FIELDS = {"recordStatus", "updatedOn"};
	
	public static String filterVersionData(AppVersion obj) throws JsonProcessingException {   
		ObjectWriter writer=null;
		String responseJson = null;
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		    .addFilter("APP_VERSION_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(IGNORABLE_FIELDS));
		writer = mapper.writer(filters);
		responseJson = writer.writeValueAsString(obj);
		return responseJson;
    }

}
