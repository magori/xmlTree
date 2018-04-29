CREATE TABLE `file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `doc_name` varchar(255) DEFAULT NULL UNIQUE,
  `content` varchar(4000) DEFAULT NULL,
PRIMARY KEY (`id`)
);