ALTER TABLE `customer_energy_detail` 
CHANGE COLUMN `energy_consumed` `wattage` DOUBLE(8,2) NOT NULL ,
ADD COLUMN `power_consumed` DOUBLE(8,5) NOT NULL AFTER `update_on`;
