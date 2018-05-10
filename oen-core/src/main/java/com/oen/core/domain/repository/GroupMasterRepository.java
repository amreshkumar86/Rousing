package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.GroupMaster;

@Repository
public interface GroupMasterRepository extends JpaRepository<GroupMaster, Long> {
	
	
	@Query(" SELECT groupEntity from GroupMaster groupEntity "
		 + " JOIN groupEntity.createdBy groupOwner "
		 + " JOIN groupOwner.customer custEntity "
		 + " WHERE custEntity.id = ?1 "
		 + " AND groupEntity.recordStatus = 1")
	List<GroupMaster> getActiveCustomerGroups(Long custId);
	
	
	

}
