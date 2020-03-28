CREATE TABLE wf_process (
     id                 VARCHAR(32) PRIMARY KEY NOT NULL,
     name               VARCHAR(100),
     display_Name       VARCHAR(200),
     type               VARCHAR(100),
     instance_Url       VARCHAR(200),
     state              TINYINT(1),
     content            LONGBLOB,
     version            INT(2),
     create_Time        VARCHAR(50),
     creator            VARCHAR(50)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE wf_order (
      id                VARCHAR(32) NOT NULL PRIMARY KEY ,
      parent_Id         VARCHAR(32) ,
      process_Id        VARCHAR(32) NOT NULL ,
      creator           VARCHAR(100) ,
      create_Time       VARCHAR(50) NOT NULL ,
      expire_Time       VARCHAR(50) ,
      last_Update_Time  VARCHAR(50) ,
      last_Updator      VARCHAR(100) ,
      priority          TINYINT(1) ,
      parent_Node_Name  VARCHAR(100) ,
      order_No          VARCHAR(100) ,
      variable          VARCHAR(2000) ,
      version           INT(3) comment '版本'
)comment='流程实例表';

CREATE TABLE wf_task (
     id                VARCHAR(32) NOT NULL PRIMARY KEY ,
     order_Id          VARCHAR(32) NOT NULL ,
     task_Name         VARCHAR(100) NOT NULL ,
     display_Name      VARCHAR(200) NOT NULL ,
     task_Type         TINYINT(1) NOT NULL ,
     perform_Type      TINYINT(1) ,
     operator          VARCHAR(100) ,
     create_Time       VARCHAR(50) ,
     finish_Time       VARCHAR(50) ,
     expire_Time       VARCHAR(50) ,
     action_Url        VARCHAR(200) ,
     parent_Task_Id    VARCHAR(100) ,
     variable          VARCHAR(2000) ,
     version           TINYINT(1)
)comment='任务表';

CREATE TABLE wf_task_actor (
       task_Id           VARCHAR(32) not null ,
       actor_Id          VARCHAR(100) not null
)comment='任务参与者表';

create table wf_hist_order (
       id                VARCHAR(32) not null primary key ,
       process_Id        VARCHAR(32) not null ,
       order_State       TINYINT(1) not null,
       creator           VARCHAR(100) ,
       create_Time       VARCHAR(50) not null ,
       end_Time          VARCHAR(50) ,
       expire_Time       VARCHAR(50) ,
       priority          TINYINT(1) ,
       parent_Id         VARCHAR(32) ,
       order_No          VARCHAR(100) ,
       variable          VARCHAR(2000)
)comment='历史流程实例表';

create table wf_hist_task (
      id                VARCHAR(32) not null primary key ,
      order_Id          VARCHAR(32) not null ,
      task_Name         VARCHAR(100) not null ,
      display_Name      VARCHAR(200) not null ,
      task_Type         TINYINT(1) not null ,
      perform_Type      TINYINT(1) ,
      task_State        TINYINT(1) not null ,
      operator          VARCHAR(100) ,
      create_Time       VARCHAR(50) not null ,
      finish_Time       VARCHAR(50) ,
      expire_Time       VARCHAR(50) ,
      action_Url        VARCHAR(200) ,
      parent_Task_Id    VARCHAR(32) ,
      variable          VARCHAR(2000)
)comment='历史任务表';

create table wf_hist_task_actor (
    task_Id           VARCHAR(32) not null ,
    actor_Id          VARCHAR(100) not null
)comment='历史任务参与者表';

create index IDX_PROCESS_NAME on wf_process (name);
create index IDX_ORDER_PROCESSID on wf_order (process_Id);
create index IDX_ORDER_NO on wf_order (order_No);
create index IDX_TASK_ORDER on wf_task (order_Id);
create index IDX_TASK_TASKNAME on wf_task (task_Name);
create index IDX_TASK_PARENTTASK on wf_task (parent_Task_Id);
create index IDX_TASKACTOR_TASK on wf_task_actor (task_Id);
create index IDX_HIST_ORDER_PROCESSID on wf_hist_order (process_Id);
create index IDX_HIST_ORDER_NO on wf_hist_order (order_No);
create index IDX_HIST_TASK_ORDER on wf_hist_task (order_Id);
create index IDX_HIST_TASK_TASKNAME on wf_hist_task (task_Name);
create index IDX_HIST_TASK_PARENTTASK on wf_hist_task (parent_Task_Id);
create index IDX_HIST_TASKACTOR_TASK on wf_hist_task_actor (task_Id);
/**
测试表
 */
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` int(11) NOT NULL,
  `city_name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;

