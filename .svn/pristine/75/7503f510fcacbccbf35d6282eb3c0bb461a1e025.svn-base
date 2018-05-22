package com.oen.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.domain.CustItemDashboardObject;
import com.oen.core.domain.DeviceData;
import com.oen.core.domain.model.AttributeMaster;
import com.oen.core.domain.model.Customer;
import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.GroupMaster;
import com.oen.core.domain.model.ItemAttributeHistory;
import com.oen.core.domain.model.ItemAttributeSchedule;
import com.oen.core.domain.model.ItemAttributeState;
import com.oen.core.domain.model.ItemAttributes;
import com.oen.core.domain.model.ItemMaster;
import com.oen.core.domain.model.Manufacture;
import com.oen.core.domain.model.User;
import com.oen.core.domain.repository.CustomerItemRepository;
import com.oen.core.domain.repository.GroupItemRepository;
import com.oen.core.domain.repository.GroupMasterRepository;
import com.oen.core.domain.repository.ItemAttributeHistoryRepository;
import com.oen.core.domain.repository.ItemAttributeRepository;
import com.oen.core.domain.repository.ItemAttributeScheduleRepo;
import com.oen.core.domain.repository.ItemAttributeStateRepository;
import com.oen.core.response.filter.CustomerItemFilter;
import com.oen.core.response.filter.ItemAttributeScheduleFilter;
import com.oen.core.service.DeviceHandlerService;
import com.oen.core.service.UserService;
import com.oen.core.util.NbanoMasterConstants;
import com.oen.core.util.OENUtils;

@Service 
public class DeviceHandlerServiceImpl implements DeviceHandlerService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerItemRepository customerItemRepository;
	
	@Autowired
	private ItemAttributeHistoryRepository itemAttributeHistoryRepository;
	
	@Autowired
	private ItemAttributeRepository itemAttributeRepository;
	
	@Autowired
	private ItemAttributeStateRepository itemAttributeStateRepository;
	
	@Autowired
	private ItemAttributeScheduleRepo itemAttributeScheduleRepo;
	
	
	@Autowired
	private GroupMasterRepository groupMasterRepository;
	
	@Autowired
	private GroupItemRepository groupItemRepository;

	@Override
	public void addNewDevice(String token, DeviceData deviceObj) {
		CustomerItems customerItem = new CustomerItems();
		frameAddNewDeviceobj(customerItem, deviceObj);
		customerItemRepository.saveAndFlush(customerItem);
	}
	
	private void frameAddNewDeviceobj(CustomerItems customerItem, DeviceData deviceObj) {
		Customer customer = new Customer();
		ItemMaster item = new ItemMaster();
		Manufacture manufacture = new Manufacture();
		customer.setId(deviceObj.getCustomerId());
		item.setId(deviceObj.getItemId());
		if(deviceObj.getManufactureId() != null)
			manufacture.setId(deviceObj.getManufactureId());
		else 
			manufacture.setId(Long.valueOf(1)); //setting default manufacture
		customerItem.setCustomer(customer);
		customerItem.setItem(item);
		customerItem.setManufacture(manufacture);
		customerItem.setItemBatchNumber(deviceObj.getItemBatchNumber());
		customerItem.setItemUniqueNumber(deviceObj.getItemUniqueNumber());
		customerItem.setNickName(deviceObj.getDeviceNickName());
		customerItem.setItemThresholdKWH(9); //SETTING DEFAULT WATT OF DEVICE
	}

	@Override
	public String getAllCustomerItems(String customerId) throws JsonProcessingException {
		List<CustomerItems> custItemList = customerItemRepository
				.getAllCustomerItems(Long.valueOf(customerId));
		return CustomerItemFilter.filterItemList(custItemList);
	}

	@Override
	public String getAllCustomerItemsAndData(String customerId) throws JsonProcessingException {
		List<CustomerItems> custItemList = customerItemRepository
				.getAllCustomerItems(Long.valueOf(customerId));
		return CustomerItemFilter.filterItemListAndData(custItemList);
	}

	@Override
	public String saveDeviceAttributeState(String token, DeviceData deviceObj) {
		ItemAttributeHistory itemData = new ItemAttributeHistory();
		User user = userService.getUserByToken(token);
		Long customerItemId = deviceObj.getCustomerItemId();  
		CustomerItems custItem = customerItemRepository.findOne(customerItemId);
		ItemAttributes itemAttribute = itemAttributeRepository.findOne(deviceObj.getItemAttributeId());
		itemData.setChangedBy(user);
		itemData.setCustomerItem(custItem);
		itemData.setItemAttribute(itemAttribute);
		itemData.setAttributeValue(deviceObj.getAttributeValue());
		
		String attributeName = itemAttribute.getAttribute().getName();
		Double consumedPower = new Double(0);
		if(attributeName.equalsIgnoreCase("power")) {
			consumedPower = OENUtils
					.calculateConsumedPower(
						custItem.getItemThresholdKWH(),
						attributeName, 
						deviceObj.getAttributeValue());
		}
		itemData.setConsumedPower(consumedPower);
		itemData = itemAttributeHistoryRepository.save(itemData);
		saveCurrentState(custItem, itemAttribute.getAttribute(), deviceObj.getAttributeValue(), consumedPower);
		return null;
	}
	
	@Override
	public ItemAttributeState saveCurrentState(CustomerItems custItem, 
			AttributeMaster attribute, String attributeValue, Double consumedPower) {
		//get existing data
		ItemAttributeState itemAttribute = itemAttributeStateRepository
				.getExistingItemDataByAttribute(custItem, attribute);
		if(itemAttribute == null) {
			// 1st entry.
			itemAttribute = new ItemAttributeState();
			itemAttribute.setAttribute(attribute);
			itemAttribute.setCustomerItem(custItem);
			itemAttribute.setAttributeValue(attributeValue);
			itemAttribute.setEnergyConsumed(consumedPower);
			itemAttribute = itemAttributeStateRepository.save(itemAttribute);
			return itemAttribute;
		}
		if(!attributeValue.equals(itemAttribute.getAttributeValue())) {
			itemAttribute.setAttributeValue(attributeValue);
			itemAttribute.setEnergyConsumed(consumedPower);
			itemAttribute = itemAttributeStateRepository.save(itemAttribute);
		}
		return itemAttribute;
	}

	@Override
	public void scheduleAnCustItem(ItemAttributeSchedule itemAttrSchedule) {
		
//		CustomerItems custItem = customerItemRepository.findOne(itemAttrSchedule.getCustomerItem().getId());
//		itemAttrSchedule.setCustomerItem(custItem);
		DateTime utcDateTime = OENUtils.
				convertDateTimeLocalToUTC(itemAttrSchedule.getScheduleDateTime(), itemAttrSchedule.getUtcZone());
		itemAttrSchedule.setScheduleTime(utcDateTime.toLocalDateTime());
		itemAttributeScheduleRepo.saveAndFlush(itemAttrSchedule);
//		return null;
	}

	@Override
	public void changeDeviceNickName(JSONObject obj) {
		Long customerDeviceId = Long.valueOf(obj.get("cust_item_id").toString());
		String nickName = obj.get("nickname").toString();
		CustomerItems custItem = customerItemRepository.findOne(customerDeviceId);
		custItem.setNickName(nickName);
		customerItemRepository.saveAndFlush(custItem);
	}

	@Override
	public List<CustItemDashboardObject> getOenDeviceDashbordData(String customerId) {
		List<CustItemDashboardObject> dashboardList = new ArrayList<>();
		List<CustomerItems> custItemList = customerItemRepository
				.getAllCustomerItems(Long.valueOf(customerId));
		
		//frame individual item data
		custItemList.forEach(individualDeviceData -> {
			JSONObject attributeInfo = frameItemAttributeInfoObj(individualDeviceData.getId());
			CustItemDashboardObject dashboardObj = new CustItemDashboardObject(individualDeviceData, attributeInfo);
			dashboardList.add(dashboardObj);
		});
		
		//frame group device data
		List<GroupMaster> customerGroupList = groupMasterRepository.getActiveCustomerGroups(Long.valueOf(customerId));
		customerGroupList.forEach(individualGrp -> {
			List<CustomerItems> groupItemList = groupItemRepository.getAllGroupCustItems(individualGrp.getId());
			//below code needs to be changed.
			//since group history is not captured getting attribute info from one of device history.
			if( groupItemList != null) {
				if(groupItemList.size() > 0) {
					CustomerItems individualDeviceData = groupItemList.get(0);
					JSONObject attributeInfo = frameItemAttributeInfoObj(individualDeviceData.getId()); //should get attrib info from group history. 
					CustItemDashboardObject dashboardObj = new CustItemDashboardObject(individualGrp, groupItemList, attributeInfo);
					dashboardList.add(dashboardObj);
				}
			}
		});
		return dashboardList;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject frameItemAttributeInfoObj(Long custItemId) {
		JSONObject attribInfo = new JSONObject();
		List<Object[]> itemAttribValues = itemAttributeStateRepository.getDevicePresentState(custItemId);
		for (Object[] row : itemAttribValues) {
			attribInfo.put(row[0].toString(), row[1].toString());
		}
		return attribInfo;
	}

	@Override
	public void updateSleepTimer(String token, JSONObject requestObject) {
		Long custItemId = Long.valueOf(requestObject.get("cust_item_id").toString());
		String sleepTimeOn = requestObject.get("timer_val").toString();
		CustomerItems custItem = customerItemRepository.findOne(custItemId);
		ItemAttributeSchedule itemAttributeSchedule = new ItemAttributeSchedule();
		itemAttributeSchedule.setCustomerItem(custItem);
		itemAttributeSchedule.setTimerValue(sleepTimeOn);
		itemAttributeSchedule.setTimerExecutedStatus(0);
		itemAttributeScheduleRepo.saveAndFlush(itemAttributeSchedule);
	}

	@Override
	public String getLastSleeperVal(Long custItemId) throws JsonProcessingException {
		ItemAttributeSchedule obj = itemAttributeScheduleRepo.getLatestItemSleepTimer(custItemId);
		return ItemAttributeScheduleFilter.filterSleeperObj(obj);
	}

	@Override
	public void deleteDevice(String token, Long custItemId) {
		customerItemRepository.delete(custItemId);
	}
	
}
