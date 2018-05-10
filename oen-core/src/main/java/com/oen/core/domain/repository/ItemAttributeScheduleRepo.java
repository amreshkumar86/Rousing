package com.oen.core.domain.repository;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.ItemAttributeSchedule;

@Repository
public interface ItemAttributeScheduleRepo extends JpaRepository< ItemAttributeSchedule, Long> {
	
	@Query(" SELECT scheduleEntity from ItemAttributeSchedule scheduleEntity "
		 + " JOIN scheduleEntity.customerItem custItemEntity "
		 + " WHERE custItemEntity.id = ?1 "
		 + " AND scheduleEntity.recordStatus = 1"
		 + " AND scheduleEntity.timerValue IS NOT NULL "
		 + " AND scheduleEntity.id = "
		 + " ( "
		 + "	SELECT MAX(scheduleEntity2.id) FROM ItemAttributeSchedule scheduleEntity2 "
		 + "    JOIN scheduleEntity2.customerItem custItemEntity2 "
 		 + " 	WHERE custItemEntity2.id = ?1 "
 		 + "    AND scheduleEntity2.recordStatus = 1 "
 		 + "    AND scheduleEntity2.timerValue IS NOT NULL  "
	     + " )")
	ItemAttributeSchedule getLatestItemSleepTimer(Long customerItemId);

}
