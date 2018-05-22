package com.oen.core.response.filter;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oen.core.domain.model.CustomerEnergyDetail;

public class CustomerEnergyDetailFilter {
	
	private static String[] energyIgnorableFields = {"recordStatus", "updatedOn",
			"customer", "calculatedAt"};
	
	public static String filterGraphData(List<CustomerEnergyDetail> energyData) throws JsonProcessingException {   
		ObjectWriter writer=null;
		String responseJson = null;
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		    .addFilter("CUSTOMER_ENERGY_DETAIL_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(energyIgnorableFields));
		writer = mapper.writer(filters);
		responseJson = writer.writeValueAsString(energyData);
		return responseJson;
    }

}
