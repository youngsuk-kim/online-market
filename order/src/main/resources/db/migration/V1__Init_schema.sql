CREATE TABLE `orders`
(
    `id`         BIGINT AUTO_INCREMENT,
    `status`     VARCHAR(255) NOT NULL,
    `customerId` BIGINT       NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `order_item`
(
    `id`            BIGINT AUTO_INCREMENT,
    `orderId`       BIGINT,
    `productId` BIGINT       NOT NULL,
    `productPrice`  DECIMAL(15)  NOT NULL,
    `productName`   VARCHAR(255) NOT NULL,
    `quantity`      BIGINT       NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `payments`
(
    `id`         BIGINT AUTO_INCREMENT,
    `orderId`    BIGINT NOT NULL,
    `customerId` BIGINT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `payment_item`
(
    `id`           BIGINT AUTO_INCREMENT,
    `paymentId`    BIGINT       NOT NULL,
    `productId`    BIGINT       NOT NULL,
    `productName`  VARCHAR(255) NOT NULL,
    `productPrice` DECIMAL(15)  NOT NULL,
    `quantity`     BIGINT       NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
