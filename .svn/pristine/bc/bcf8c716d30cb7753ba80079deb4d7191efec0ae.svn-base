package com.oen.core.domain;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.GroupItemMapping;
import com.oen.core.domain.model.GroupMaster;

public class CustItemDashboardObject implements Serializable {

	private static final long serialVersionUID = 7531833195076413967L;
	
	private Long id;
	private String type; //group or device
	private String name; //group name or device name
	private JSONArray device_list;
	private JSONObject attrib_info;
	
	@SuppressWarnings("unused")
	private CustItemDashboardObject() {
	}
	
	
	@SuppressWarnings("unchecked")
	public CustItemDashboardObject(CustomerItems individualDeviceData, JSONObject attributeInfo) {
		this.id = individualDeviceData.getId();
		this.type = "device";
		this.name = individualDeviceData.getNickName();
		JSONArray devicelist = new JSONArray();
		JSONObject deviceData = new JSONObject();
		deviceData.put("id", individualDeviceData.getId());
		deviceData.put("name", individualDeviceData.getNickName());
		deviceData.put("unique_number", individualDeviceData.getItemUniqueNumber());
		devicelist.add(deviceData);
		this.device_list = devicelist;
		this.attrib_info = attributeInfo;
	}
	
	@SuppressWarnings("unchecked")
	public CustItemDashboardObject(GroupMaster group, List<CustomerItems> groupItemList, JSONObject attributeInfo) {
		this.id = group.getId();
		this.type = "group";
		this.name = group.getGroupName();
		JSONArray devicelist = new JSONArray();
		groupItemList.forEach( individualDeviceData -> {
			JSONObject deviceData = new JSONObject();
			deviceData.put("id", individualDeviceData.getId());
			deviceData.put("name", individualDeviceData.getNickName());
			deviceData.put("unique_number", individualDeviceData.getItemUniqueNumber());
			devicelist.add(deviceData);
		});
		this.device_list = devicelist;
		this.attrib_info = attributeInfo;
	}
	
	
	public Long getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public JSONArray getDevice_list() {
		return device_list;
	}
	public JSONObject getAttrib_info() {
		return attrib_info;
	}
}
