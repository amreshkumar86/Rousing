ALTER TABLE `customer_energy_detail` 
ADD COLUMN `item_master_id` BIGINT(20) NOT NULL DEFAULT 1 AFTER `customer_id`,
ADD INDEX `customer_energy_calculator_fk1_idx` (`item_master_id` ASC);
ALTER TABLE `customer_energy_detail` 
ADD CONSTRAINT `customer_energy_calculator_fk1`
  FOREIGN KEY (`item_master_id`)
  REFERENCES `item_master` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;