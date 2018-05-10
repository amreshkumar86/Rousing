package com.oen.core.response.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oen.core.domain.model.ItemAttributeSchedule;

public class ItemAttributeScheduleFilter {
	
	private ItemAttributeScheduleFilter() {
		//hiding this class
	}
	
	private static String[] ignorableFieldSetForSleeper = {"recordStatus", "updatedOn",
			"customerItem", "scheduleTime", "timerExecutedStatus", "scheduleDateTime", "utcZone"};

	
	public static String filterSleeperObj(ItemAttributeSchedule object) throws JsonProcessingException {   
		ObjectWriter writer=null;
		String responseJson = null;
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		    .addFilter("SCHEDULE_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldSetForSleeper));
		writer = mapper.writer(filters);
		responseJson = writer.writeValueAsString(object);
		return responseJson;
    }
	
	
}
