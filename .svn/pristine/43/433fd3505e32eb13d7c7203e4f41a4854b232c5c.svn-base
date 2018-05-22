package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.ItemAttributeHistory;

@Repository
public interface ItemAttributeHistoryRepository extends JpaRepository<ItemAttributeHistory, Long> {

	@Query(" SELECT history from ItemAttributeHistory history "
		 + " JOIN history.customerItem custItem "
		 + " JOIN custItem.customer custEntity "
		 + " WHERE custEntity.id = ?1 "
		 + " AND "
		 + " MONTH(history.updatedOn) = ?2")
	List<ItemAttributeHistory> getThisMonthPowerHistory(Long custId, Integer presentMonth);
	
	
	@Query(" SELECT history.consumedPower from ItemAttributeHistory history "
		 + " JOIN history.customerItem custItem "
		 + " JOIN custItem.customer custEntity "
		 + " WHERE custEntity.id = ?1 "
		 + " AND "
		 + " CONVERT(DATE_FORMAT(history.updatedOn,'%Y-%m-%d-%H:%i:00'),DATETIME) = ?2 ")
	List<Double> powerConsumedValues(Long custId, String timeValue);
	
	
}
