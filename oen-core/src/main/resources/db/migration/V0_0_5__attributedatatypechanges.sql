ALTER TABLE `item_attribute_history` 
CHANGE COLUMN `attribute_value` `attribute_value` VARCHAR(50) NOT NULL ;

ALTER TABLE `item_attribute_state` 
CHANGE COLUMN `attribute_value` `attribute_value` VARCHAR(50) NOT NULL ;

