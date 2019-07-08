# activitidemo
这是一个activiti练手的例子

select * from act_re_deployment;  //部署对象表

select * from act_re_procdef;  //流程定义表

select * from act_ge_bytearray;  //资源文件表

select * from act_ge_property;  //主键生成策略表



select * from act_ru_execution; //正在执行的对象表 (如果是单例流程【没有分支和聚合】的流程，那么流程实例ID和执行对象ID是相同的（ID和PROC_INST_ID_）)

select * from act_hi_procinst; //流程实例历史表 (开启一个流程，只能对应一个实例)

select * from act_ru_task; // 正在执行的任务表 （只有节点是userTask的时候，该表中才存在数据）

select * from act_hi_taskinst; //任务历史表（只有节点是userTask的时候，该表中才存在数据）

select * from act_hi_actinst; //所有活动节点的历史表（包括start和end节点）
