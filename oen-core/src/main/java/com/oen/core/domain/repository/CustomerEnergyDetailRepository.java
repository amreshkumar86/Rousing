package com.oen.core.domain.repository;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.CustomerEnergyDetail;
import com.oen.core.domain.model.ItemAttributeHistory;

@Repository
public interface CustomerEnergyDetailRepository extends JpaRepository<CustomerEnergyDetail, Long> {

	@Query(" SELECT energyDetailEntity.calculatedAt from CustomerEnergyDetail energyDetailEntity "
		 + " JOIN energyDetailEntity.customer customerEntity "
		 + " WHERE customerEntity.id = ?1 AND energyDetailEntity.recordStatus = 1"
		 + " AND energyDetailEntity.id = "
		 + " ( "
		 + "	SELECT MAX(energyDetailEntity2.id) FROM CustomerEnergyDetail energyDetailEntity2"
		 + "    JOIN energyDetailEntity2.customer customerEntity "
 		 + " 	WHERE customerEntity.id = ?1 AND energyDetailEntity2.recordStatus = 1"
	     + " )")
	LocalDateTime getLatestEnergyCalculatedTime(Long customerId);
	
	
	@Query(" SELECT energyDetailEntity from CustomerEnergyDetail energyDetailEntity "
		 + " JOIN energyDetailEntity.customer customerEntity "
		 + " WHERE customerEntity.id = ?1 AND energyDetailEntity.recordStatus = 1"
		 + " AND DATE(energyDetailEntity.calculatedAt) = ?2 ")
	List<CustomerEnergyDetail> getEnergyGraphData(Long custId, Date dateInput);
	
	
	@Query(" SELECT SUM(energyDetailEntity.powerConsumed) from CustomerEnergyDetail energyDetailEntity "
		 + " JOIN energyDetailEntity.customer customerEntity "
		 + " JOIN energyDetailEntity.itemMaster itemMasterEntity "
		 + " WHERE customerEntity.id = ?1 "
		 + " AND energyDetailEntity.recordStatus = 1 "
		 + " AND itemMasterEntity.id = ?2 "
		 + " AND MONTH(energyDetailEntity.updatedOn) = ?3")
	Double totalPowerConsumed(Long custId, Long itemMasterId, Integer presentMonth);
	
	
}
