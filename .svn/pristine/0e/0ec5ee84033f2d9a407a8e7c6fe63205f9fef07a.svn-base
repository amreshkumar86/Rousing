package com.oen.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oen.core.domain.GroupDeviceData;
import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.GroupItemMapping;
import com.oen.core.domain.model.GroupMaster;
import com.oen.core.domain.model.ItemAttributeHistory;
import com.oen.core.domain.model.ItemAttributes;
import com.oen.core.domain.model.User;
import com.oen.core.domain.repository.CustomerItemRepository;
import com.oen.core.domain.repository.GroupItemRepository;
import com.oen.core.domain.repository.GroupMasterRepository;
import com.oen.core.domain.repository.ItemAttributeHistoryRepository;
import com.oen.core.domain.repository.ItemAttributeRepository;
import com.oen.core.exception.OENPermissionException;
import com.oen.core.service.DeviceHandlerService;
import com.oen.core.service.GroupManagementService;
import com.oen.core.service.UserService;
import com.oen.core.util.OENUtils;

@Service
public class GroupManagementServiceImpl implements GroupManagementService {
	
	@Autowired
	private GroupMasterRepository groupMasterRepository;
	
	@Autowired
	private GroupItemRepository groupItemRepository;
	
	@Autowired
	private CustomerItemRepository customerItemRepository;
	
	@Autowired
	private ItemAttributeRepository itemAttributeRepository;
	
	@Autowired
	private ItemAttributeHistoryRepository itemAttributeHistoryRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeviceHandlerService deviceHandlerService;

	
	@Override
	@Transactional
	public void createNewGroup(String token, GroupDeviceData groupInfo) {
		GroupMaster group = new GroupMaster();
		group.setGroupName(groupInfo.getGroupName());
		group.setCreatedBy(userService.getUserByToken(token));
		group.setCreatedOn(LocalDateTime.now());
		groupMasterRepository.saveAndFlush(group);
		addGroupDevices(group, groupInfo);
	}
	
	public void addGroupDevices(GroupMaster group, GroupDeviceData devicesInfo){
		devicesInfo.getCustomerItemIds().forEach(custItemId -> {
			GroupItemMapping groupItem = new GroupItemMapping();
			CustomerItems custItem = customerItemRepository.findOne(custItemId);
			groupItem.setCustItem(custItem);
			groupItem.setGroup(group);
			groupItemRepository.saveAndFlush(groupItem);
		});
	}

	@Override
	public void updateGroupName(String token, JSONObject requestObject) {
		Long groupId = Long.valueOf(requestObject.get("group_id").toString());
		String groupName = requestObject.get("group_name").toString();
		GroupMaster group = groupMasterRepository.findOne(groupId);
		group.setGroupName(groupName);
		groupMasterRepository.saveAndFlush(group);
	}

	@Override
	public void saveGroupDeviceAttributeState(String token, GroupDeviceData devicesInfo) {
		saveAsHistoryData(token, devicesInfo);
	}
	
	
	private void saveAsHistoryData(String token, GroupDeviceData devicesInfo) {
		User user = userService.getUserByToken(token);
		List<Long> groupCustItemIds = groupItemRepository.getAllGroupCustItemIds(devicesInfo.getGroupId());
		groupCustItemIds.forEach(custItemId -> {
			//looping all items
			ItemAttributeHistory historyObj = new ItemAttributeHistory();
			
			CustomerItems custItem = customerItemRepository
					.findOne(custItemId);
			historyObj.setChangedBy(user);
			historyObj.setCustomerItem(custItem);
			
			devicesInfo.getDeviceInfo().forEach(eachAttributeData -> {
				ItemAttributes itemAttribute = itemAttributeRepository.findOne(eachAttributeData.getItemAttributeId());
				String attributeValue = eachAttributeData.getAttributeValue();
				
				String attributeName = itemAttribute.getAttribute().getName();
				Double consumedPower = new Double(0);
				if(attributeName.equalsIgnoreCase("power")) {
					consumedPower = OENUtils
							.calculateConsumedPower(
								custItem.getItemThresholdKWH(),
								attributeName, 
								attributeValue);
				}
				historyObj.setItemAttribute(itemAttribute);
				historyObj.setAttributeValue(attributeValue);
				historyObj.setConsumedPower(consumedPower);
				
				itemAttributeHistoryRepository.save(historyObj);
				deviceHandlerService.saveCurrentState(
						custItem, 
						itemAttribute.getAttribute(), 
						attributeValue, consumedPower);
			});
		});
	}
	
	@Override
	public void updateGroupDeviceMapping(String token, GroupDeviceData groupInfo) {
		GroupMaster groupObj = groupMasterRepository.findOne(groupInfo.getGroupId());
		//step 1 delete old group items.
		groupItemRepository.deleteGroupItems(groupObj);
		groupInfo.getCustomerItemIds().forEach(custItemId -> {
			GroupItemMapping groupItem = new GroupItemMapping();
			CustomerItems custItem = customerItemRepository.findOne(custItemId);
			groupItem.setCustItem(custItem);
			groupItem.setGroup(groupObj);
			groupItemRepository.saveAndFlush(groupItem);
		});
	}

	@Override
	public void deleteGroup(String token, GroupDeviceData groupInfo) {
		Long userId = userService.getUserIdByToken(token);
		GroupMaster groupObj = groupMasterRepository.findOne(groupInfo.getGroupId());
		if(!groupObj.getId().equals(userId))
			throw new OENPermissionException("You don't have permission to delete this group.!!!!");
		//deleting group items.
		groupItemRepository.deleteGroupItems(groupObj);
		//deleting group.
		groupMasterRepository.delete(groupObj);
	}

}
