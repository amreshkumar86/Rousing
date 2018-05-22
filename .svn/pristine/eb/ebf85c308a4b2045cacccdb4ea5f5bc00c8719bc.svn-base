SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `customer_items` 
DROP FOREIGN KEY `customer_items_fk2`;
ALTER TABLE `customer_items` 
CHANGE COLUMN `manufacture_id` `manufacture_id` BIGINT(20) NULL ;
ALTER TABLE `customer_items` 
ADD CONSTRAINT `customer_items_fk2`
  FOREIGN KEY (`manufacture_id`)
  REFERENCES `manufacturer` (`id`);
  
ALTER TABLE `customer_items` 
CHANGE COLUMN `item_batch_number` `item_batch_number` VARCHAR(100) NULL ;

SET FOREIGN_KEY_CHECKS=1;  