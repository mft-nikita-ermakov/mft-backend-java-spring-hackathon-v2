create table role (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table user_role_set (user_id bigint not null, role_set_id bigint not null, primary key (user_id, role_set_id)) engine=InnoDB
alter table user_role_set add constraint FKrlwwt31pa7e1lelri8djtuvw4 foreign key (role_set_id) references role (id)
alter table user_role_set add constraint FKrsg3bo3l1d7in4e1g0onk7m14 foreign key (user_id) references user (id)