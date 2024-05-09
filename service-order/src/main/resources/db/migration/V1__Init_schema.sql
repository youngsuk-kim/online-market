CREATE TABLE `delivery`
(
    `id`                  BIGINT AUTO_INCREMENT,
    `orderId`             BIGINT       NOT NULL,
    `customerEmail`       VARCHAR(255) NOT NULL,
    `customerName`        VARCHAR(255) NOT NULL,
    `customerPostalCode`  VARCHAR(255) NOT NULL,
    `customerDestination` VARCHAR(255) NOT NULL,
    `created_at`          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders`
(
    `id`         BIGINT AUTO_INCREMENT,
    `status`     VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `order_item`
(
    `id`            BIGINT AUTO_INCREMENT,
    `orderId`       BIGINT,
    `productItemId` BIGINT         NOT NULL,
    `productPrice`  DECIMAL(15, 2) NOT NULL,
    `productName`   VARCHAR(255)   NOT NULL,
    `quantity`      BIGINT         NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `payments`
(
    `id`            BIGINT AUTO_INCREMENT,
    `orderId`       BIGINT       NOT NULL,
    `customerEmail` VARCHAR(255) NOT NULL,
    `customerName`  VARCHAR(255) NOT NULL,
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `payment_item`
(
    `id`         BIGINT AUTO_INCREMENT,
    `paymentId`  BIGINT         NOT NULL,
    `productId`  BIGINT         NOT NULL,
    `name`       VARCHAR(255)   NOT NULL,
    `price`      DECIMAL(15, 2) NOT NULL,
    `quantity`   BIGINT         NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
