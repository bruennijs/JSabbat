package sabbat.apigateway.location.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.Application;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.apigateway.location.unittest.UnitTestConfig;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class, WebConfig.class, Application.class})
@WebIntegrationTest
public class MapMyTracksApiIntegrationTest {

}
