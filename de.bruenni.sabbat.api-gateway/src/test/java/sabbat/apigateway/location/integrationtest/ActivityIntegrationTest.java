package sabbat.apigateway.location.integrationtest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sabbat.apigateway.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bruenni on 13.07.16.
 */
//@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, TestConfig.class })
@WebAppConfiguration
public class ActivityIntegrationTest {

    public MockMvc mockMvc;

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

    @Test
    public void When_startactivity_expect_response_contains_activity_id() throws Exception {
        mockMvc.perform(post(locationApiPoC)
                .content("request=start_activity"))
                .andExpect(status().isOk());
    }
}
