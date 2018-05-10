package com.oen.core.response.filter;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.oen.core.domain.model.CustomerItems;

public class CustomerItemFilter {
	
	private CustomerItemFilter() {
		//hiding this class
	}
	
	private static String[] custItemsIgnorableFlds = {"recordStatus", "updatedOn",
			"customer", "item", "manufacture", "itemBatchNumber", "itemAttributeState", "itemAttributeHistory", "ItemAttributeSchedule", "groupItems"};
	private static String[] itemMasterIgnorableFlds = {"recordStatus", "updatedOn"};
	private static String[] manufactureIgnorableFlds = {"recordStatus", "updatedOn", "authUserName", "authPassword"};
	
 
	
	public static String filterItemList(List<CustomerItems> custItems) throws JsonProcessingException {   
		ObjectWriter writer=null;
		String responseJson = null;
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		    .addFilter("CUSTOMER_ITEMS_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(custItemsIgnorableFlds));
		writer = mapper.writer(filters);
		responseJson = writer.writeValueAsString(custItems);
		return responseJson;
    }
	
	public static String filterItemListAndData(List<CustomerItems> custItems) throws JsonProcessingException {   
		ObjectWriter writer=null;
		String responseJson = null;
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		    .addFilter("CUSTOMER_ITEMS_FILTER",SimpleBeanPropertyFilter.serializeAllExcept("recordStatus","updatedOn","customer","itemBatchNumber"))
		    .addFilter("ITEM_MASTER_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(itemMasterIgnorableFlds))
		    .addFilter("MANUFACTURE_FILTER",SimpleBeanPropertyFilter.serializeAllExcept(manufactureIgnorableFlds));
		writer = mapper.writer(filters);
		responseJson = writer.writeValueAsString(custItems);
		return responseJson;
    }
}
