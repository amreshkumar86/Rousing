package com.oen.core.service.impl;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.domain.EnergyDashboardInfo;
import com.oen.core.domain.model.CustomerEnergyDetail;
import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.ItemAttributeHistory;
import com.oen.core.domain.model.ItemMaster;
import com.oen.core.domain.repository.CustomerEnergyDetailRepository;
import com.oen.core.domain.repository.CustomerItemRepository;
import com.oen.core.domain.repository.ItemAttributeHistoryRepository;
import com.oen.core.domain.repository.ItemAttributeStateRepository;
import com.oen.core.response.filter.CustomerEnergyDetailFilter;
import com.oen.core.service.EnergyService;
import com.oen.core.util.OENUtils;

@Service
public class EnergyServiceImpl implements EnergyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EnergyServiceImpl.class);
	
	@SuppressWarnings("unused")
	@Autowired
	private ItemAttributeStateRepository itemAttributeStateRepository;
	
	@Autowired
	private ItemAttributeHistoryRepository itemAttributeHistoryRepository;
	
	@Autowired
	private CustomerEnergyDetailRepository customerEnergyDetailRepository;
	
	@Autowired
	private CustomerItemRepository customerItemRepository;

	@Override
	public List<EnergyDashboardInfo> frameEnergyDashboardObj(JSONObject requestObject) {
		Long custId = Long.valueOf(requestObject.get("cust_id").toString());
		//Long custItemId = Long.valueOf(requestObject.get("cust_item_id").toString());
		List<EnergyDashboardInfo> dashboardObj = new ArrayList<>();
		EnergyDashboardInfo bulbInfo = getDeatilsByDeviceType(custId);
		if(bulbInfo != null)
			dashboardObj.add(bulbInfo);
		EnergyDashboardInfo gatewaysInfo = frameGatewayDashboardObj(custId);
		if(gatewaysInfo != null)
			dashboardObj.add(gatewaysInfo);
		return dashboardObj;
	}

	@Override
	public EnergyDashboardInfo getDeatilsByDeviceType(Long custId) {
		EnergyDashboardInfo device= new EnergyDashboardInfo();
		List<Object[]> dashboardBulbInfo = customerItemRepository.getBulbInfoForDashboard(custId); //should pass type& query changes.
		device = frameDashboardObj(custId, dashboardBulbInfo);
		return device;
	}
	
	public EnergyDashboardInfo frameDashboardObj(Long custId, List<Object[]> dashboardBulbInfo) {
		EnergyDashboardInfo device= new EnergyDashboardInfo();
		for (Object[] row : dashboardBulbInfo) {
			device.setDeviceType("Bulb"); //hardcoded need to be change. No time to work...
			device.setTotalCount(Integer.valueOf(row[0].toString()));
			device.setActiveCount(Integer.valueOf(row[1].toString()));
			device.setActiveBelowMidValue(Integer.valueOf(row[2].toString()));
		}
		Double totalKWHofMonth = calculatPowerConsumption(custId, new Long(1)); //get Bulb KWH of month. 
		device.setTotalKWH(totalKWHofMonth);
		return device;
	}
	
	public EnergyDashboardInfo frameGatewayDashboardObj(Long custId) {
		List<CustomerItems> gateways  = customerItemRepository.getCustomerGateWays(custId);
		if(gateways.size() >= 1) {
			EnergyDashboardInfo device= new EnergyDashboardInfo();
			device.setDeviceType("Gateway");
			device.setTotalCount(gateways.size());
			device.setActiveCount(gateways.size());
			device.setActiveBelowMidValue(null);
			Double totalKWHofMonth = calculatPowerConsumption(custId, new Long(2)); //get Gateway KWH of month. 
			device.setTotalKWH(totalKWHofMonth);
			return device;
		}
		return null;
	}
	
	public Double calculatPowerConsumption(Long custId, Long itemMasterId) {
		LocalDateTime currentTime = LocalDateTime.now();
		Integer presentMonth = Integer.valueOf(currentTime.toString("yyyy-MM-dd").substring(5, 7));
		Double totalPowerConsumed = customerEnergyDetailRepository.totalPowerConsumed(custId, itemMasterId, presentMonth);
		DecimalFormat formatter = new DecimalFormat("#.###");
		formatter.setRoundingMode(RoundingMode.UP);
		LOG.info("###############################");
		LOG.info(" POWER CONSUMED  :: "+totalPowerConsumed);
		totalPowerConsumed = Double.valueOf(formatter.format(totalPowerConsumed));
		LOG.info(" POWER CONSUMED (after format)  :: "+totalPowerConsumed);
		LOG.info("###############################");
		return totalPowerConsumed;
	}
	
	
	
	
	
	public Long getTotalActiveSeconds(List<ItemAttributeHistory> history) {
		int count = 0;
		long totalSeconds=(long)0;
		int presentRowPowerValue=0;
		LocalDateTime presentRowValue, previousRowValue = null;
		for (ItemAttributeHistory row : history) {
			if(count==0){
				presentRowPowerValue = Integer.valueOf(row.getAttributeValue());
				if(presentRowPowerValue == 1)
					continue;
				presentRowValue = previousRowValue = row.getUpdatedOn();
				count++;
				continue;
			} else {
				presentRowValue = row.getUpdatedOn();
				presentRowPowerValue = Integer.valueOf(row.getAttributeValue());
				if(presentRowPowerValue == 1 ) {
					Period period = new Period(previousRowValue, presentRowValue);
					totalSeconds += period.getSeconds();
					previousRowValue = presentRowValue;
					count++;
				} else {
					count++;
					continue;
				}
			}
		}
		return totalSeconds;
	}
	
	public Double getKWH(Long totalSeconds, Integer deviceWattValue) {
		Double totalKwh =((Double.valueOf(deviceWattValue.toString())* Double.valueOf(totalSeconds))/Double.valueOf("3600")); //per hour total 3600 seconds
		return totalKwh;
	}

	@Override
	public String getEnergyGraphData(String token, JSONObject requestObject) throws JsonProcessingException,ParseException{
		Long customerId = Long.valueOf(requestObject.get("customer_id").toString());
		String graphDate = null;
		Date dateInput = null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(requestObject.containsKey("graph_date")) {
			graphDate = requestObject.get("graph_date").toString();
			dateInput = formatter.parse(graphDate);
		} else {
			graphDate = LocalDateTime.now().toString("yyyy-MM-dd");
			dateInput = formatter.parse(graphDate);
		}
		List<CustomerEnergyDetail> energyData = customerEnergyDetailRepository
				.getEnergyGraphData(customerId, dateInput);
		if(energyData.size() == 0)
			return null;
		return CustomerEnergyDetailFilter
				.filterGraphData(frameGraphObj(energyData));
	}
	
	public List<CustomerEnergyDetail> frameGraphObj(List<CustomerEnergyDetail> energyData) {
		List<CustomerEnergyDetail> energyConsumptionData = new ArrayList<>();
		energyData.forEach(singleData -> {
			CustomerEnergyDetail data = new CustomerEnergyDetail();
			
			//ugly fix. Staging server time is in UTC.
			LOG.info("###############################");
			LOG.info("TIME VALUE BEFORE UTC TO LOCAL CONVERTION :: "+singleData.getCalculatedAt().toString("HH.mm"));
			LocalDateTime ldt = singleData.getCalculatedAt().plusHours(5).plusMinutes(30);
			LOG.info("TIME VALUE AFTER UTC TO LOCAL CONVERTION :: "+ldt.toString("HH.mm"));
			data.setTimeValue(ldt.toString("HH.mm"));
			LOG.info("###############################");
			data.setWattage(singleData.getWattage());
			energyConsumptionData.add(data);
		});
		return energyConsumptionData;
	}
	
}
