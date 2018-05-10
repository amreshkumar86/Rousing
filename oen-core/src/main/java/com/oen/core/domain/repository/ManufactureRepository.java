package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.Manufacture;

@Repository
public interface ManufactureRepository extends JpaRepository<Manufacture, Long> {
	
//	List<Manufacture> getAll

}
