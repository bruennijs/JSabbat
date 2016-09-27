package sabbat.apigateway.location.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bruenni on 14.07.16.
 */
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestConfig.class})
public class SpringTests {
    @Value("${application.name}")
    public String appName;

    @Autowired
    public Environment env;

    @Test
    public void When_environment_expect_property_radable()
    {
        Assert.assertEquals("de.bruenni.sabbat.api-gateway", env.getProperty("de.bruenni.sabbat.api-gateway"));
        Assert.assertEquals("de.bruenni.sabbat.api-gateway", appName);
    }
}
