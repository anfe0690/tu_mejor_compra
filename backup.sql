DROP TABLE IF EXISTS `Usuario`;

CREATE TABLE `Usuario` (
  `nombre` varchar(255) NOT NULL,
  `banco` varchar(255) DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombreContacto` varchar(255) DEFAULT NULL,
  `numeroCuenta` varchar(255) DEFAULT NULL,
  `telefonos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Usuario` VALUES ('andres','Banco Bogota','Cali','123','andresfe@gmail.com','Carrera 24 #45-05','Andres Felipe Munoz','123-4623','310 234 5678'),('carlos','Bancolombia','Medellin','456','carlosperez@gmail.com','Calle 56 #83-41','Carlos Jose Perez','456-1856','311 934 3045'),('fernando','Avvillas','Bogota','789','fernandorodriguez@gmail.com','Carrera 89 #34-84','Fernando Rodriguez','845-3029','315 285 0948');


DROP TABLE IF EXISTS `Producto`;

CREATE TABLE `Producto` (
  `id` bigint(20) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `direccionImagen` varchar(255) DEFAULT NULL,
  `fechaDeCreacion` datetime(6) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `precio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Producto` VALUES (40,'TELEFONOS_INTELIGENTES','andres/samsung-galaxy-s4.jpg','2019-07-15 16:24:15.223000','Samsung Galaxy S4 I9500 8.nucleos 2gb.ram 13mpx.cam 32gb.me','1.139.000'),(41,'TELEFONOS_INTELIGENTES','andres/iphone-5s.jpg','2019-07-15 16:24:15.223000','Iphone 5s 16gb Lte Libre Caja Sellada Lector Huella','1.619.900'),(42,'TELEFONOS_INTELIGENTES','andres/lg-g2.jpg','2019-07-15 16:24:15.223000','Lg G2 D805 Android 4.2 Quad Core 2.26 Ghz 16gb 13mpx 2gb Ram','1.349.990'),(43,'CONSOLAS_VIDEO_JUEGOS','carlos/playstation-4.jpg','2019-07-15 16:24:15.286000','Ps4 500gb Con Dualshock 4 + Bluray,wifi,hdmi,membresia Plus','1.400.000'),(44,'CONSOLAS_VIDEO_JUEGOS','carlos/wii-u.jpg','2019-07-15 16:24:15.286000','Nintendo Wii U 32gb Negro + Juego Nintendo Land + Hdmi+base','704.990'),(45,'CONSOLAS_VIDEO_JUEGOS','carlos/xbox-one.jpg','2019-07-15 16:24:15.286000','Xbox One 500gb + Control + Hdmi + Auricular+ Sensor Kinect 2','1.449.990'),(46,'TABLETAS','fernando/google-nexus-10.jpg','2019-07-15 16:24:15.346000','Tablet Samsung Google Nexus 10pul 16gb Gorilla Glass Ram 2gb','919.000'),(47,'TABLETAS','fernando/tablet-sony-xperia-z.jpg','2019-07-15 16:24:15.346000','Xperia Tablet Sony Z 32gb','840.000'),(48,'TABLETAS','fernando/toshiba-excite.jpg','2019-07-15 16:24:15.346000','Tablet Toshiba Excite Se 305 Original Ram 1gb Android 4.1.1','598.000');


DROP TABLE IF EXISTS `Usuario_Producto`;

CREATE TABLE `Usuario_Producto` (
  `Usuario_nombre` varchar(255) NOT NULL,
  `productos_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Usuario_nombre`,`productos_id`),
  UNIQUE KEY `UK_15swsl2vodemeitobl80rm800` (`productos_id`),
  CONSTRAINT `FK8nytij3i5q6dd7qky2f78huyp` FOREIGN KEY (`productos_id`) REFERENCES `Producto` (`id`),
  CONSTRAINT `FKnrioqrvs24tpjd14p4qn0e9ud` FOREIGN KEY (`Usuario_nombre`) REFERENCES `Usuario` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Usuario_Producto` VALUES ('andres',40),('andres',41),('andres',42),('carlos',43),('carlos',44),('carlos',45),('fernando',46),('fernando',47),('fernando',48);


DROP TABLE IF EXISTS `Transaccion`;

CREATE TABLE `Transaccion` (
  `id` int(11) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `usuarioComprador_nombre` varchar(255) DEFAULT NULL,
  `usuarioVendedor_nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKejq7nyp0766954rff304eh0t` (`producto_id`),
  KEY `FKrwg5u9hs2m7uv9mo58y6gwqsd` (`usuarioComprador_nombre`),
  KEY `FKe2hip5vnkgkqc9u8xf5ukjnrt` (`usuarioVendedor_nombre`),
  CONSTRAINT `FKe2hip5vnkgkqc9u8xf5ukjnrt` FOREIGN KEY (`usuarioVendedor_nombre`) REFERENCES `Usuario` (`nombre`),
  CONSTRAINT `FKejq7nyp0766954rff304eh0t` FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`),
  CONSTRAINT `FKrwg5u9hs2m7uv9mo58y6gwqsd` FOREIGN KEY (`usuarioComprador_nombre`) REFERENCES `Usuario` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Transaccion` VALUES (49,'ESPERANDO_PAGO',43,'andres','carlos'),(50,'EN_ENVIO',46,'andres','fernando'),(51,'ESPERANDO_PAGO',41,'carlos','andres'),(52,'TERMINADO',40,'fernando','andres');


DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `hibernate_sequence` VALUES (53),(53);