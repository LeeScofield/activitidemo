package com.demo.activitidemo.diagrams;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * @author Lee
 * date:2019-07-04
 */
public class ProcessDefinition_2 {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程定义
    @Test
    public void deploymentProcessDefinition_classpath(){

        Deployment processDefinition = processEngine.getRepositoryService()  //与定义和部署相关的server
                .createDeployment()   //创建一个部署对象
                .addClasspathResource("diagrams/helloworld.bpmn")  //从classpath路径中加载，一次只能加载一个文件
                .addClasspathResource("diagrams/helloworld.png")
                .name("流程定义") //添加部署名称
                .deploy();  //完成部署

        System.out.println("部署ID:"+ processDefinition.getId());
        System.out.println("部署名称:" + processDefinition.getName());
    }

    //从zip文件中部署一个流程
    @Test
    public void deploymentProcessDefinition_zip() throws Exception{
        Deployment processDefinition = processEngine.getRepositoryService()  //与定义和部署相关的server
                .createDeployment()   //创建一个部署对象
                .addZipInputStream(new ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip")))
                .name("流程定义") //添加部署名称
                .deploy();  //完成部署

        System.out.println("部署ID:"+ processDefinition.getId());
        System.out.println("部署名称:" + processDefinition.getName());
    }

    //生成测试流程定义
//    @Test
//    public void createProcessDefinition(){
//
//        Deployment deployment = processEngine.getRepositoryService()
//                .createDeployment()
//                .addClasspathResource("bpmn/first.bpmn")
//                .name("第一个流程定义")
//                .deploy();
//
//        System.out.println("部署ID:"+ deployment.getId());
//        System.out.println("部署名称:" + deployment.getName());
//    }

    //查询流程定义
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()

                //排序
                .orderByProcessDefinitionVersion().desc()
                //条件查询
                .processDefinitionName("helloworldProcess")
//                .processDefinitionId("helloworld:2:12504")

//                .list()  //查询多个结果
//                .singleResult()  //查询单个结果
//                .listPage(0,3) //分页查询
                .list();

        list.forEach(System.out::println);
    }

    //删除流程定义
    @Test
    public void deleteProcessDefinition(){
        //只能删除非启动流程定义，启用流程后用该删除会报错
//        processEngine.getRepositoryService()
//                .deleteDeployment("15001");


        //及联删除，不管流程是否启动，都能删除
        processEngine.getRepositoryService()
                .deleteDeployment("32501",true);


        System.out.println("删除成功");
    }

    //查看流程图
    @Test
    public void viewPic() throws Exception{

        //获取资源名称
        List<String> deploymentResourceNames = processEngine.getRepositoryService()
                .getDeploymentResourceNames("1");

        String resourceName = deploymentResourceNames.stream().filter(d -> d.endsWith(".png")).collect(Collectors.joining());
        System.out.println("资源名称:" +resourceName);

        InputStream resourceAsStream = processEngine.getRepositoryService()
                .getResourceAsStream("1", resourceName);

        FileUtils.copyInputStreamToFile(resourceAsStream,new File("D:" + File.separator + resourceName));

        System.out.println("图片下载成功");
    }

    //符加功能：查询最新版本流程定义
    @Test
    public void findLastVersionProcessDefinition(){

        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()
                .list();


        Map<String, Optional<ProcessDefinition>> map = list.stream()
                .collect(Collectors.groupingBy(ProcessDefinition::getName, Collectors.maxBy(Comparator.comparing(ProcessDefinition::getVersion))));

        List<ProcessDefinition> processDefinitionList = new ArrayList<>(map.values().stream().collect(Collectors.mapping(Optional::get, Collectors.toList())));

        processDefinitionList.forEach(System.out::println);

    }


    //根据name删除所有流程定义
    @Test
    public void deleteAllProcessDefinitionByName(){

        // 查询出所的相同name的流程定义
        List<ProcessDefinition> processDefinitionList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionName("helloworldProcess")
                .list();

        //循环指删除
        processDefinitionList.forEach(processDefinition -> processEngine.getRepositoryService().deleteDeployment(processDefinition.getDeploymentId(), true));

        System.out.println("删除成功");
    }

    //查询历史任务
    @Test
    public void findHistoryTask(){

        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee("张三")
                .list();

        list.forEach(historicTaskInstance -> {
            String str = JSON.toJSONString(historicTaskInstance);
            System.out.println(historicTaskInstance.getId() + "  " + str);
        });

    }


    //查询历史流程实例
    @Test
    public void findHistoryProcessInstance(){

        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId("42501")
                .singleResult();

        System.out.println(JSON.toJSONString(historicProcessInstance));

    }
}
