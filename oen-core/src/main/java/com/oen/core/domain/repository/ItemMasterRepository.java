package com.oen.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.ItemMaster;


@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long>{

}
