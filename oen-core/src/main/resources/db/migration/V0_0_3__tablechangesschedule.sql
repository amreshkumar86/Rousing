ALTER TABLE `item_attribute_schedule` 
CHANGE COLUMN `schedule_time` `schedule_time` DATETIME NULL ,
CHANGE COLUMN `timer_value` `timer_value` VARCHAR(20) NULL ,
CHANGE COLUMN `timer_executed` `timer_executed` TINYINT(1) NULL ;