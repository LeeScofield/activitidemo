package com.demo.activitidemo;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lee
 * date:2019-06-12
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ActivitiTest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(ActivitiTest.class);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.out.println("start......");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println("end......");
    }

    @Test
    public void test1(){

        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

        System.out.println(configuration.getJdbcUrl());

        logger.info("这个类实现不依懒spring:[{}]" , configuration);
    }

    @Test
    public void test2(){

        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();

        configuration.buildProcessEngine();

        System.out.println(configuration.getJdbcUrl());

        logger.info("这个类实现依懒spring:[{}]",configuration);
    }

}
