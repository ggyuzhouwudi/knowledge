package module;

import flowable.FlowableApplication;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Oliver
 * @date 2023年02月20日 23:06
 */
@SpringBootTest(classes = FlowableApplication.class)
public class ProcessEngineTests {

    @Test
    public void getEngine() {
        // 获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("processEngine = " + processEngine);
    }
}
