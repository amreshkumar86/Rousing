package com.oen.core.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.domain.repository.AppVersionRepository;
import com.oen.core.response.filter.AppVersionFilter;
import com.oen.core.service.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService {
	
	@Autowired
	private AppVersionRepository appVersionRepository;

	@Override
	public String getLatestAppVersionData() throws JsonProcessingException, ParseException {
		return AppVersionFilter.filterVersionData(appVersionRepository.getLastActiveVersionData());
	}
	
	

}
