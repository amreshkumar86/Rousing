CREATE TABLE `customer_energy_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `time_value` datetime NOT NULL,
  `energy_consumed` double(8,2) NOT NULL,
  `record_status` int(2) NOT NULL,
  `update_on` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_energy_calculator_fk0` (`customer_id`),
  CONSTRAINT `customer_energy_calculator_fk0` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;