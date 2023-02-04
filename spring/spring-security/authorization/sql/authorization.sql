SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(32)  DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `enabled`  tinyint(1)   DEFAULT NULL,
    `locked`   tinyint(1)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;
BEGIN;
INSERT INTO `user`
VALUES (1, 'admin', '{noop}123', 1, 0);
INSERT INTO `user`
VALUES (2, 'user', '{noop}123', 1, 0);
INSERT INTO `user`
VALUES (3, 'blr', '{noop}123', 1, 0);
COMMIT;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `uid` int(11) DEFAULT NULL,
    `rid` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `uid` (`uid`),
    KEY `rid` (`rid`),
    CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES
        `user` (`id`),
    CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES
        `role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

BEGIN;
INSERT INTO `user_role`
VALUES (1, 1, 1);
INSERT INTO `user_role`
VALUES (2, 1, 2);
INSERT INTO `user_role`
VALUES (3, 2, 2);
INSERT INTO `user_role`
VALUES (4, 3, 3);
COMMIT;

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `pattern` varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

BEGIN;
INSERT INTO `menu`
VALUES (1, '/admin/**');
INSERT INTO `menu`
VALUES (2, '/user/**');
INSERT INTO `menu`
VALUES (3, '/guest/**');
COMMIT;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT,
    `name`   varchar(32) DEFAULT NULL,
    `nameZh` varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

BEGIN;
INSERT INTO `role`
VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO `role`
VALUES (2, 'ROLE_USER', '普通⽤户');
INSERT INTO `role`
VALUES (3, 'ROLE_GUEST', '游客');
COMMIT;

DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`
(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `mid` int(11) DEFAULT NULL,
    `rid` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `mid` (`mid`),
    KEY `rid` (`rid`),
    CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES
        `menu` (`id`),
    CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES
        `role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

BEGIN;
INSERT INTO `menu_role`
VALUES (1, 1, 1);
INSERT INTO `menu_role`
VALUES (2, 2, 2);
INSERT INTO `menu_role`
VALUES (3, 3, 3);
INSERT INTO `menu_role`
VALUES (4, 3, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;