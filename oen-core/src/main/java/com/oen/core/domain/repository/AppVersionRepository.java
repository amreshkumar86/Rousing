package com.oen.core.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.AppVersion;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersion, Long>{
	
	@Query(" SELECT appVersion from AppVersion appVersion "
		 + " WHERE appVersion.recordStatus = 1")
	AppVersion getLastActiveVersionData();

}
