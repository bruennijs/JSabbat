package sabbat.apigateway.location.unittest;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sabbat.apigateway.location.config.RootConfig;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.apigateway.location.controller.MapMyTracksApiController;
import sabbat.apigateway.location.command.IActivityCommandFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MockServletContext.class, UnitTestConfig.class, WebConfig.class})
@WebAppConfiguration
public class ExampleControllerTest {

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