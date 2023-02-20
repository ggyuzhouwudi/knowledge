package module;

import flowable.FlowableApplication;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oliver
 * @date 2023年02月20日 16:57
 */
@SpringBootTest(classes = FlowableApplication.class)
public class FlowableInitTests {

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;

    @BeforeEach
    public void before() {
        ProcessEngineConfiguration configuration = new StandaloneInMemProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("1");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable?serverTimezone=UTC&nullCatalogMeansCurrent=true");
        // 没有表结构就新增
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
    }

    /**
     * 部署一个流程
     */
    @Test
    void deploy() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .name("请假流程")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 查询流程
     */
    @Test
    void deployQuery() {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        Deployment deployment = deploymentQuery.deploymentId("1").singleResult();
        System.out.println("deployment = " + deployment);
    }

    @Test
    void deleteQuery() {
        // 删除 参数是ID 和 级联
        // 默认如果启动了就不能删除，级联如果真的话启动了也可以删除，相关的任务一并会删除掉
        // 重复删除会报错：org.flowable.engine.common.api.FlowableObjectNotFoundException: Could not find a deployment with id '1'.
        repositoryService.deleteDeployment("1");
    }

    /**
     * 启动流程实例
     */
    @Test
    void runProcess() {
        Map<String, Object> variables = new HashMap<>(3);
        variables.put("employee", "zhang");
        variables.put("nrOfHolidays", 3);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holidayRequest:2:2503", variables);
        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

}
