package sabbat.apigateway.location.integrationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sabbat.apigateway.location.unittest.UnitTestConfig;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bruenni on 13.07.16.
 */
@Configuration
@PropertySource("classpath:test/application.properties")
@ImportResource(locations = {"classpath:test/spring-api-gateway-test.xml"})
public class IntegrationTestConfig {
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = {MockServletContext.class, UnitTestConfig.class, WebConfig.class})
    @WebAppConfiguration
    public static class ExampleControllerTest {

        private MockMvc mvc;

        @Before
        public void setUp() throws Exception {
            IActivityCommandFactory factory = mock(IActivityCommandFactory.class);

            MapMyTracksApiController controller = new MapMyTracksApiController();
            controller.setActivityCommandFactory(factory);

            mvc = MockMvcBuilders.standaloneSetup(controller).build();
        }

        @Test
        public void when_send_no_params_expect_415() throws Exception {
            mvc.perform(MockMvcRequestBuilders.post("/location/api/v1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(415));
        }
    }
}
