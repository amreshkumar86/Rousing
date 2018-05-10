package com.oen.intelligence.util;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Override
	public String rfeturnSomeString() {
		return "Value returned from oen intelligence";
	}

}
