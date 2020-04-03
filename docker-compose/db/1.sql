USE weather;

CREATE TABLE `boards` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `description` text NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `locations` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `woeid` text,
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
    `newsId` bigint(20) NOT NULL,
    `woeid` text NOT NULL,
    `date` text NOT NULL,
    `high` int(11) NOT NULL,
    `low` int(11) NOT NULL,
    `forecast` text NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `news` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `woeid` text NOT NULL,
    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `date` text NOT NULL,
    `temp` text NOT NULL,
    `condition` text NOT NULL,
    PRIMARY KEY (`id`)
);
