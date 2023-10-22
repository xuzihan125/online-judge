
-- create database
create database if not exists online_judge;

-- change database
use online_judge;

-- user table
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'account',
    userPassword varchar(512)                           not null comment 'password',
    unionId      varchar(256)                           null comment 'wechat platform',
    mpOpenId     varchar(256)                           null comment 'wechat platform, public account',
    userName     varchar(256)                           null comment 'user name',
    userAvatar   varchar(1024)                          null comment 'user avatar',
    userProfile  varchar(512)                           null comment 'description in user profile',
    userRole     varchar(256) default 'user'            not null comment 'user role. List in user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'create time of the account',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'last update time',
    isDelete     tinyint      default 0                 not null comment 'is this account delete ot not'
) comment 'user table' collate = utf8mb4_unicode_ci;

create table if not exists question
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment 'title of the problem',
    content    text                               null comment 'content of the problem',
    tags       varchar(1024)                      null comment 'tags of the problem, stored in json format',
    answer     text                               null comment 'answer of the problem',
    submitNum  int      default 0                 not null comment 'total time of submit',
    acceptNum  int      default 0                 not null comment 'total number of accept',
    judgeCase  text     null                      comment 'test case.(json format)',
    judgeConfig text    null                      comment 'test settings(json format)',
    likeNum    int      default 0                 not null comment 'number of likes',
    favourNum  int      default 0                 not null comment 'number of favor',
    userId     bigint                             not null comment 'user id of the creater',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'time of creation',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time of last update',
    isDelete   tinyint  default 0                 not null comment 'is this problem deleted',
    index idx_userId (userId)
) comment 'problem' collate = utf8mb4_unicode_ci;

create table if not exists solution_submit
(
    id         bigint auto_increment comment 'id' primary key,
    language   varchar(128)                       not null comment 'language used for coding',
    code       text                               not null comment 'code submitted by user',
    judgeInfo  text                               null comment 'result of judge. stored in a json format',
    status     int      default 0                 not null comment 'status of judging(0 - to be judged / 1 - judging / 2 - success / 3 - fail)',
    questionId  bigint                            not null comment 'id of the question',
    userId bigint not null comment 'user id of the creater',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'time of creation',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'time of last update',
    isDelete   tinyint  default 0                 not null comment 'is this problem deleted',
    index ids_questionId (questionId),
    index ids_userId (userId)
) comment 'solution submit by user'

