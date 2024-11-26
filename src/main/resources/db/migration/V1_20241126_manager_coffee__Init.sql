-- role --
CREATE TABLE IF NOT EXISTS `role` (
    `role_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(255) NOT NULL,
    `role_description` VARCHAR(255),
    `delete_flag` TINYINT(1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `status` (
    `status_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'code' TINYINT(1),
    `name` VARCHAR(125),
    `delete_fag` TINYINT(1)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- User --
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    `user_full_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `user_sex` VARCHAR(10) NULL,
    `user_birthdate` DATE NULL,
    `user_address` VARCHAR(125),
    `user_image` VARCHAR(255),
    `password` VARCHAR(255) NULL,
    `delete_flag` TINYINT(1),
    `status_id` BIGINT NOT NULL,
    FOREIGN KEY (`status_id`) REFERENCES `status`(`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- User rolle--
CREATE TABLE IF NOT EXISTS `user_role` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;


-- shift (ca làm việc) --
# CREATE TABLE IF NOT EXISTS `shift` (
#     shift_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
#     user_id INT NOT NULL,
#     time_start DATE NOT NULL,
#     time_end DATE NOT NULL,
#     text_note VARCHAR(255),
#     FOREIGN KEY (user_id) REFERENCES `user`(user_id)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Pay statement (Bảng lương) --
# CREATE TABLE IF NOT EXISTS `pay_statement` (
#     pay_statement_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
#     user_id INT NOT NULL,
#     shift_id INT NOT NULL,
#     total_hourly_rate INT NOT NULL,
#     total_salary INT NOT NULL,
#     date_created DATE NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES `user`(user_id),
#     FOREIGN KEY (shift_id) REFERENCES `shift`(shift_id)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Product (Sản phẩm) --
CREATE TABLE IF NOT EXISTS `product` (
    `product_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(100) NOT NULL,
    `product_price` INT NOT NULL,
    `product_image` VARCHAR(255),
    `category_id` INT,
    `quantity` INT,
    `delete_flag` TINYINT(1),
    `status_id` BIGINT NOT NULL,
    FOREIGN KEY (`status_id`) REFERENCES `status`(`status_id`),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Category (Loại hàng) --
CREATE TABLE IF NOT EXISTS `category` (
    `category_id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(255) NOT NULL,
    `category_description` VARCHAR(255) NOT NULL,
    `product_id` BIGINT NOT NULL,
    `status_id` BIGINT NOT NULL,
    `delete_flag` TINYINT(1),
    FOREIGN KEY (`status_id`) REFERENCES `status`(`status_id`),
    FOREIGN KEY (`product_id`) REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- status order --
CREATE TABLE IF NOT EXISTS `status_order` (
    `status_order_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'code' TINYINT(1),
    `name` VARCHAR(125),
    `delete_fag` TINYINT(1)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- order (đặt hàng) --
CREATE TABLE IF NOT EXISTS `order` (
    `order_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_code` VARCHAR(125) NOT NULL,
    `order_date` DATETIME NOT NULL ,
    `order_address` VARCHAR(255) NOT NULL,
    `order_description` VARCHAR(255),
    `payment` INT(3),
    `total_money` DECIMAL(19,4),
    `user_id` BIGINT NOT NULL,
    `status_order_id` BIGINT NOT NULL,
    `delete_flag` TINYINT(1),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
    FOREIGN KEY (`status_order_id`) REFERENCES `status_order`(`status_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- order detail --
CREATE TABLE IF NOT EXISTS `order_detail` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `total_money` DECIMAL(19,4),
    `order_id` BIGINT,
    `status_order_id` BIGINT NOT NULL,
    `delete_flag` TINYINT(1),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
    FOREIGN KEY (`status_order_id`) REFERENCES `status_order`(`status_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# --  Data user_role
# INSERT INTO user_role VALUES (1, 'Admin');
# INSERT INTO user_role VALUES (2, 'Member');
#
# -- Data user
# INSERT INTO user VALUES (1, 'admin', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'admin@gmail.com', 'admin', 'Name', '2000-10-10','a','a',1);
# INSERT INTO user VALUES (2, 'a', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'a@gmail.com', 'a', 'Name', '2000-10-10','a','a',2);
#
