DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`               varchar(100)  NOT NULL COMMENT '名称',
    `login_account`      varchar(1024) NOT NULL COMMENT '登录账号',
    `login_password`     varchar(1024) NOT NULL COMMENT '登录密码',
    `creator_id`         bigint(20) COMMENT '创建人id',
    `updater_id`         bigint(20) COMMENT '修改人id',
    `created_time`       datetime      NOT NULL COMMENT '创建时间',
    `modified_time` datetime      NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='人员';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name`               varchar(100) NOT NULL COMMENT '名称',
    `creator_id`         bigint(20) COMMENT '创建人id',
    `updater_id`         bigint(20) COMMENT '修改人id',
    `created_time`       datetime     NOT NULL COMMENT '创建时间',
    `modified_time` datetime     NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色';

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id`          bigint(20)    NOT NULL COMMENT '父级id',
    `name`               varchar(100)  NOT NULL COMMENT '名称',
    `url`                varchar(1024) NOT NULL COMMENT 'url',
    `creator_id`         bigint(20) COMMENT '创建人id',
    `updater_id`         bigint(20) COMMENT '修改人id',
    `created_time`       datetime      NOT NULL COMMENT '创建时间',
    `modified_time` datetime      NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='菜单';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `parent_id`          bigint(20)   NOT NULL COMMENT '父级id',
    `name`               varchar(100) NOT NULL COMMENT '名称',
    `creator_id`         bigint(20) COMMENT '创建人id',
    `updater_id`         bigint(20) COMMENT '修改人id',
    `created_time`       datetime     NOT NULL COMMENT '创建时间',
    `modified_time` datetime     NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='部门';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`            bigint(20) NOT NULL COMMENT 'user_id',
    `role_id`            bigint(20) NOT NULL COMMENT 'role_id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='人员角色';

DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id`            bigint(20) NOT NULL COMMENT 'role_id',
    `menu_id`            bigint(20) NOT NULL COMMENT 'menu_id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色菜单';

DROP TABLE IF EXISTS `user_department`;
CREATE TABLE `user_department`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`            bigint(20) NOT NULL,
    `department_id`      bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='人员部门';