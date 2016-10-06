package sabbat.apigateway.location.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import sabbat.apigateway.Application;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import test.matcher.LambdaMatcher;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by bruenni on 13.07.16.
 */
//@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class, WebConfig.class, Application.class })
@WebAppConfiguration
public class MapMyTracksApiIntegrationTest {

    @Value("${apigateway.mapmytracksapi.url}")
    public String locationApiPoC;

    @Autowired
    public WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @org.junit.Before
    public void setup()
    {
        //this.mockMvc = new MockMvcBuilder().build();
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void When_startactivity_expect_response_contains_activity_id() throws Exception {

        String requestId = "start_activity";

        ObjectMapper mapper = new XmlMapper();
        //mapper.writeValueAsString()


        mockMvc.perform(post(locationApiPoC)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("request", requestId))
                //.content("request=start_activity&title=hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(new LambdaMatcher<>(content ->
                {
                    try {
                        ActivityCreatedResponse response = mapper.readValue(content, ActivityCreatedResponse.class);
                        return response.type.equals("activity_started");
                    } catch (IOException e) {
                        return false;
                    }
                }, "check response content")));

        //UUID.fromString()
    }
}