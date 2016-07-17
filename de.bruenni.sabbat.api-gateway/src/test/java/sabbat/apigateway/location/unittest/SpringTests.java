package sabbat.apigateway.location.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bruenni on 14.07.16.
 */
@EnableAutoConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestConfig.class})
public class SpringTests {
    @Value("${test.location.poc}")
    public String locationApiPoC;

    @Autowired
    public Environment env;

    @Test
    public void When_environment_expect_property_radable()
    {
        Assert.assertEquals("/location/api/v1", env.getProperty("test.location.poc"));
        Assert.assertEquals("/location/api/v1", locationApiPoC);
    }
}
