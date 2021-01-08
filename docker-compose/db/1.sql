USE weather;

CREATE TABLE `boards` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `description` text NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `locations` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `latitude` decimal(5,2) not null,
    `longitude` decimal(5,2) not null,
    `location` text NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `board_locations` (
    `board_id` bigint(20) NOT NULL,
    `location_id` bigint(20) NOT NULL,
    PRIMARY KEY(`board_id`, `location_id`)
);

CREATE TABLE `forecasts` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `location_id` bigint(20) NOT NULL,
    `date_time` timestamp NOT NULL,
    `temp_min` decimal(4,2) NOT NULL,
    `temp_max` decimal(4,2) NOT NULL,
    `humidity` integer(3) NOT NULL,
    `wind_speed` decimal(4,2) NOT NULL,
    `description` text not null,
    `clouds` integer(3) NOT NULL,
    `pop` decimal(3,2) NOT NULL,
    `rain` decimal(6,2) NULL,
    `snow` decimal(6,2) NULL,
    PRIMARY KEY(`id`)
);

