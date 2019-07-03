package com.demo.activitidemo.bpmn;

import junit.framework.TestCase;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lee
 * date:2019-06-27
 */
public class ActivitiTest1 extends TestCase{
    private static final Logger logger = LoggerFactory.getLogger(ActivitiTest1.class);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        logger.info("start>>>>>>>>>>>>>>>");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        logger.info("end>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void testInitConfig(){
        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();

        ProcessEngine processEngine = configuration.buildProcessEngine();
        //运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //任务服务
        TaskService taskService = processEngine.getTaskService();
        //存储服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/first.bpmn").deploy();

        ProcessInstance myProcess_1 = runtimeService.startProcessInstanceByKey("myProcess_1");//开始一个流程实例
        logger.info("实例ID：[{}],key:[{}]",myProcess_1.getId(),myProcess_1.getBusinessKey());

        Task task = taskService.createTaskQuery().processInstanceId(myProcess_1.getId()).singleResult();
        logger.info("当前流程结点:[{}]", task.getName());
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(myProcess_1.getId()).singleResult();
        logger.info("当前流程结点:[{}]", task.getName());
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(myProcess_1.getId()).singleResult();
        logger.info("当前流程结点:[{}]", task);



    }

}
