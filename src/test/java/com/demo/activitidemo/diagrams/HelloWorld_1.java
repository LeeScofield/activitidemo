package com.demo.activitidemo.diagrams;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @author Lee
 * date:2019-07-03
 */
public class HelloWorld_1 {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程定义
    @Test
    public void delopmentProcessDefinition(){

        Deployment deployment = processEngine.getRepositoryService()  //与流程定义和部署相关的service
                .createDeployment()  //创建一个部署对象
                .name("helloworld入门程序") //添加部署名称
                .addClasspathResource("diagrams/helloworld.bpmn") //从classpath下加载文件，一次只能加载一个文件
                .addClasspathResource("diagrams/helloworld.png")
                .deploy();//完成部署

        System.out.println(String.format("部署id:%s",deployment.getId()));  //  1
        System.out.println(String.format("部署name:%s",deployment.getName())); // helloworld入门程序
    }
    //启动流程实例
    @Test
    public void startProcessInstance(){
        ProcessInstance processInstance = processEngine.getRuntimeService() //正在执行的流程实例和对象相关的service
                .startProcessInstanceByKey("helloworld");//bpmn文件中的ID就是这里的KEY，默认是按照最新版本启动的

        System.out.println(String.format("流程实例ID:%s",processInstance.getId()));  //  2501
        System.out.println(String.format("流程定义ID:%s",processInstance.getProcessDefinitionId())); // helloworld:1:4

    }

    //查询当前个人任务
    @Test
    public void findMyPersonalTask(){

        List<Task> taskList = processEngine.getTaskService()  //正在执行的任务相关service
                .createTaskQuery()   //创建查询对象
                .taskAssignee("王五")  //指定查询人
                .list();

        taskList.forEach(task -> {
            System.out.println(String.format("任务ID:%s",task.getId()));  // 2505
            System.out.println(String.format("任务name:%s",task.getName())); // 提交审批
            System.out.println(String.format("创建时间:%s",task.getCreateTime()));  // 2505
            System.out.println(String.format("任务办理人:%s",task.getAssignee())); // 提交审批
            System.out.println(String.format("执行对象id:%s",task.getExecutionId()));  // 2502
            System.out.println(String.format("执行定义id:%s",task.getProcessDefinitionId())); // helloworld:1:4
        });
    }

    //完成我的个人任务
    @Test
    public void completeMyPersonalTask(){

        String taskId = "7502";
        processEngine.getTaskService().complete(taskId);

        System.out.println(String.format("完成任务ID:%s",taskId));  // 2505
    }

}
