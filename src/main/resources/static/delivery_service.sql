CREATE TABLE `fooddelivery`.`DELIVERY` (
  `delivery_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `delivery_partner_id` INT NOT NULL,
  `status` VARCHAR(500) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `picked_up_at` TIMESTAMP ,
  `delivered_at` TIMESTAMP ,
  PRIMARY KEY (`delivery_id`, `status`),
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`delivery_partner_id` ASC) VISIBLE );

CREATE TABLE `fooddelivery`.`DELIVERY_PARTNER_DETAILS` (
  `delivery_partner_id` INT NOT NULL,
  `current_loc_lat` VARCHAR(30) NOT NULL,
  `current_loc_long` VARCHAR(30) NOT NULL,
  `is_available` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`delivery_partner_id`),
  INDEX `user_id_idx` (`delivery_partner_id` ASC) VISIBLE );