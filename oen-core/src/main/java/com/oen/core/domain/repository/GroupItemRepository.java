package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.GroupItemMapping;
import com.oen.core.domain.model.GroupMaster;

@Repository
public interface GroupItemRepository extends JpaRepository<GroupItemMapping, Long> {
	
	@Query(" SELECT groupCustItems.id from GroupItemMapping groupItems "
		 + " JOIN groupItems.custItem groupCustItems "
		 + " JOIN groupItems.group groupEntity "
		 + " WHERE groupEntity.id = ?1 AND groupItems.recordStatus = 1")
	List<Long> getAllGroupCustItemIds(Long groupId);
	
	@Query(" SELECT groupCustItems from GroupItemMapping groupItems "
		 + " JOIN groupItems.custItem groupCustItems "
		 + " JOIN groupItems.group groupEntity "
		 + " WHERE groupEntity.id = ?1 AND groupItems.recordStatus = 1")
	List<CustomerItems> getAllGroupCustItems(Long groupId);
	
	@Modifying
	@Transactional
	@Query(" DELETE from GroupItemMapping groupItems  "
		 + " WHERE groupItems.group = ?1")
	void deleteGroupItems(GroupMaster groupObj);

}
