package sabbat.apigateway.location.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bruenni on 13.07.16.
 */
//@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class })
@WebAppConfiguration
public class ActivityIntegrationTest {

    @Value("${test.location.poc}")
    public String locationApiPoC;

    private MockMvc mockMvc;

    @org.junit.Before
    public void setup()
    {
        //this.mockMvc = new MockMvcBuilder().build();
    }

    @Test
    public void When_startactivity_expect_response_contains_activity_id() throws Exception {
        mockMvc.perform(post(locationApiPoC)
                .content("request=start_activity"))
                .andExpect(status().isOk());
    }
}
