package module;

import flowable.FlowableApplication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oliver
 * @date 2023年02月20日 16:57
 */
@SpringBootTest(classes = FlowableApplication.class)
public class FlowableInitTests {

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;

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
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
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
        System.out.println("deploy.getName() = " + deploy.getName());
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
        repositoryService.deleteDeployment("12501", true);
    }

    /**
     * 启动流程实例
     */
    @Test
    void runProcess() {
        Map<String, Object> variables = new HashMap<>(3);
        variables.put("employee", "zhang");
        variables.put("nrOfHolidays", 3);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holidayRequest:1:15003", variables);
        System.out.println("processInstance = " + processInstance);
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 查询任务
     */
    @Test
    void queryProcess() {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("li")
                .list();
        for (Task task : list) {
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
        }
    }

    @Test
    void executeProcess() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee("li").singleResult();
        Map<String,Object> variables = new HashMap<>(1);
        variables.put("approved",false);
        taskService.complete(task.getId(),variables);
    }

    @Test
    void queryHistory() {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("holidayRequest:1:15003")
                .finished() // 记录是已经完成的
                .orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println("historicActivityInstance.getActivityId() = " + historicActivityInstance.getActivityId());
            System.out.println("historicActivityInstance.getActivityName() = " + historicActivityInstance.getActivityName());
            System.out.println("historicActivityInstance.getAssignee() = " + historicActivityInstance.getAssignee());
            // 据上个节点过了多久
            System.out.println("historicActivityInstance.getDurationInMillis() = " + historicActivityInstance.getDurationInMillis());
        }
    }

}
