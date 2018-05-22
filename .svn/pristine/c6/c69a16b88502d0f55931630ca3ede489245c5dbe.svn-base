package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.AttributeMaster;
import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.ItemAttributeHistory;
import com.oen.core.domain.model.ItemAttributeState;

@Repository
public interface ItemAttributeStateRepository extends JpaRepository<ItemAttributeState, Long> {
	
	@Query(" SELECT custItemState from ItemAttributeState custItemState "
		 + " WHERE custItemState.customerItem = ?1 AND custItemState.attribute = ?2 "
		 + " AND custItemState.recordStatus = 1")
	ItemAttributeState getExistingItemDataByAttribute(CustomerItems custItem, 
			AttributeMaster attribute);
	/*
	 * MYSQL QUERY
	 * 
	select 
	(SELECT COUNT(*) FROM customer_items 
	join item_master on customer_items.item_id = item_master.id
	join customer on customer_items.customer_id = customer.id 
	where customer_id = 1 AND item_master.name like 'bulb') as total_count,
	(SELECT COUNT(*) FROM item_attribute_state 
	join customer_items on item_attribute_state.customer_item_id = customer_items.id
	join attribute_master on item_attribute_state.attributes_master_id = attribute_master.id
	join item_master on customer_items.item_id = item_master.id
	join customer on customer_items.customer_id = customer.id 
	where customer_id = 1 AND item_master.name like 'bulb' AND attribute_master.name like 'power' 
	AND item_attribute_state.attribute_value = 1) as active_count,
	(SELECT COUNT(*) FROM item_attribute_state 
	join customer_items on item_attribute_state.customer_item_id = customer_items.id
	join attribute_master on item_attribute_state.attributes_master_id = attribute_master.id
	join item_master on customer_items.item_id = item_master.id
	join customer on customer_items.customer_id = customer.id 
	where customer_id = 1 AND item_master.name like 'bulb' AND attribute_master.name like 'brigthness' 
	AND item_attribute_state.attribute_value <= 50) as lights_brightness_below_50
	from item_attribute_state; 
	*/
	@Query(" SELECT DISTINCT "
			+ "(SELECT COUNT(*) FROM CustomerItems custItem "
				+ " JOIN custItem.item itemEntity "
				+ " JOIN custItem.customer custEntity "
				+ " WHERE custEntity.id = ?1 AND itemEntity.name LIKE 'bulb' ) AS totalCount, "
			+ "(SELECT COUNT(*) FROM ItemAttributeState itemAttribInfo "
				+ " JOIN itemAttribInfo.customerItem custItem "
				+ " JOIN custItem.item itemEntity"
				+ " JOIN custItem.customer custEntity"
				+ " JOIN itemAttribInfo.attribute attribEntity"
				+ " WHERE custEntity.id = ?1 AND itemEntity.name LIKE 'bulb' "
				+ " AND attribEntity.name LIKE 'power'"
				+ " AND itemAttribInfo.attributeValue = 1 ) AS activeCount, "
			+ "(SELECT COUNT(*) FROM ItemAttributeState itemAttribInfo "
				+ " JOIN itemAttribInfo.customerItem custItem "
				+ " JOIN custItem.item itemEntity "
				+ " JOIN custItem.customer custEntity "
				+ " JOIN itemAttribInfo.attribute attribEntity "
				+ " WHERE custEntity.id = ?1 AND itemEntity.name LIKE 'bulb' "
				+ " AND attribEntity.name LIKE 'brigthness' "
				+ " AND itemAttribInfo.attributeValue <= 50 ) AS brightnessBelow50 "
		+ "FROM CustomerItems custItemBase ")
    public List<Object[]> getBulbInfoForDashboard(Long custmerID);
    
    
	@Query(" SELECT attributeEntity.name, custItemState.attributeValue from ItemAttributeState custItemState "
		 + " JOIN custItemState.attribute attributeEntity "
		 + " JOIN custItemState.customerItem custItemEntity "
		 + " WHERE custItemEntity.id = ?1 "
		 + " AND custItemState.recordStatus = 1")
	List<Object[]> getDevicePresentState(Long customerItemId);
	
	@Query(" SELECT custItemState.energyConsumed from ItemAttributeState custItemState "
		 + " JOIN custItemState.customerItem custItem "
		 + " JOIN custItem.customer custEntity "
		 + " JOIN custItem.item itemMasterEntity "
		 + " WHERE custEntity.id = ?1 "
		 + " AND itemMasterEntity.id = ?2 "
		 + " GROUP BY custItem.id ")
	List<Double> getObjForPowerConsumptionCalculation(Long custId, Long itemMasterId);
    
    
}
