package com.oen.scheduler.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oen.core.domain.model.Customer;
import com.oen.core.domain.model.CustomerEnergyDetail;
import com.oen.core.domain.model.ItemAttributeHistory;
import com.oen.core.domain.model.ItemAttributeState;
import com.oen.core.domain.model.ItemMaster;
import com.oen.core.domain.repository.CustomerEnergyDetailRepository;
import com.oen.core.domain.repository.CustomerItemRepository;
import com.oen.core.domain.repository.CustomerRepository;
import com.oen.core.domain.repository.ItemAttributeHistoryRepository;
import com.oen.core.domain.repository.ItemAttributeStateRepository;

import ch.qos.logback.core.net.SyslogOutputStream;

@Configuration
@EnableScheduling
public class EnergyCalculationUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(EnergyCalculationUtil.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerEnergyDetailRepository customerEnergyDetailRepository;
	
	@Autowired
	private ItemAttributeHistoryRepository itemAttributeHistoryRepository;
	
	@Autowired
	private ItemAttributeStateRepository itemAttributeStateRepository;
	
	@Autowired
	private CustomerItemRepository customerItemRepository;
	
	@SuppressWarnings("static-access")
	@Scheduled(cron = "0 */2 * * * *") //cron runs for every 2 minutes.
    public void calculateEnergyConsumptionTemp() throws IOException, InterruptedException {
		LocalDateTime currentTime = new LocalDateTime().now();
    	LOG.info("ENERGY CONSUMPTION CALCULATION :: The time is now {}", currentTime.toString("dd/MM/yyyy HH:mm:ss"));
    	List<Long> listCustomerIds = customerRepository.getAllActiveCustomersIds();
    	listCustomerIds.forEach(custId -> {
    		LOG.info("##########################");
    		LOG.info("CUSTOMER ID : "+custId);
    		LOG.info("##########################");
    		List<ItemMaster> masterItemListOfCust = customerItemRepository.getUniqueItemMasterOfCust(custId);
    		masterItemListOfCust.forEach(itemMaster -> {
    			String lastExecuted = null;
        		Double wattage = new Double(0);
    			lastExecuted = currentTime.toString("yyyy-MM-dd HH:mm");
    			lastExecuted = lastExecuted + ":00";
    			wattage = calculateWattage(custId, lastExecuted, itemMaster.getId());
        		updateCustomerEnergyDetail(custId, currentTime, wattage, itemMaster);
    		});
    	});
    }
	
	public void updateCustomerEnergyDetail(Long custId, LocalDateTime currentTime, Double wattage, ItemMaster itemMaster) {
		CustomerEnergyDetail energyConsumptions = new CustomerEnergyDetail();
		Customer cust = customerRepository.findOne(custId);
		energyConsumptions.setCustomer(cust);
		energyConsumptions.setCalculatedAt(currentTime);
		energyConsumptions.setWattage(wattage);
		energyConsumptions.setItemMaster(itemMaster);
		energyConsumptions.setPowerConsumed(calculatePowerConsumption(wattage));
		customerEnergyDetailRepository.saveAndFlush(energyConsumptions);
	}
	
	
	public Double calculateWattage(Long custId, String timeValue, Long itemMasterId) {
		List<Double> itemCurrentStates = itemAttributeStateRepository
				.getObjForPowerConsumptionCalculation(custId,itemMasterId);
		LOG.info("##########################");
		LOG.info("itemCurrentStates LIST SIZE ::: "+itemCurrentStates.size());
		Double totalWattage = new Double(0);
		if(itemCurrentStates.size() != 0) {
			for (Double wattage : itemCurrentStates) {
				if (wattage != null)
					totalWattage = totalWattage + wattage;
			}
		}
		LOG.info("Total Wattage ::: "+totalWattage);
		LOG.info("##########################");
		return totalWattage;
	}
	
	
	public Double calculatePowerConsumption(Double wattage) {
		Double powerConsumed = new Double(0);
		powerConsumed = (wattage * 2)/(1000 * 60);
		LOG.info("##########################");
		LOG.info("Power consumed ::: "+powerConsumed);
		LOG.info("##########################");
		return powerConsumed;
	}

}
