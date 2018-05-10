INSERT INTO `attribute_master` (`id`, `name`, `record_status`, `update_on`) VALUES ('3', 'COLOR', '1', '2017-06-05 18:17:46');
INSERT INTO `attribute_master` (`id`, `name`, `record_status`, `update_on`) VALUES ('4', 'COLOR BRIGHTNESS', '1', '2017-06-05 18:17:46');
INSERT INTO `attribute_master` (`id`, `name`, `record_status`, `update_on`) VALUES ('5', 'TEMPARATURE', '1', '2017-06-05 18:17:46');
INSERT INTO `attribute_master` (`id`, `name`, `record_status`, `update_on`) VALUES ('6', 'MODE', '1', '2017-06-05 18:17:46');

INSERT INTO `item_attributes` (`id`, `item_id`, `attribute_master_id`, `minimum_input_value`, `maximum_input_value`, `record_status`, `update_on`) VALUES ('3', '1', '3', '0', '255', '1', '2017-06-05 18:17:46');
INSERT INTO `item_attributes` (`id`, `item_id`, `attribute_master_id`, `minimum_input_value`, `maximum_input_value`, `record_status`, `update_on`) VALUES ('4', '1', '4', '0', '100', '1', '2017-06-05 18:17:46');
INSERT INTO `item_attributes` (`id`, `item_id`, `attribute_master_id`, `minimum_input_value`, `maximum_input_value`, `record_status`, `update_on`) VALUES ('5', '1', '5', '170', '480', '1', '2017-06-05 18:17:46');
INSERT INTO `item_attributes` (`id`, `item_id`, `attribute_master_id`, `minimum_input_value`, `maximum_input_value`, `record_status`, `update_on`) VALUES ('6', '1', '6', '1', '3', '1', '2017-06-05 18:17:46');