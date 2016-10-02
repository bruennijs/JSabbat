package sabbat.apigateway.location.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by bruenni on 10.07.16.
 */
@Configuration
@EnableWebMvc
//@ComponentScan(basePackages = "sabbat.apigateway.location.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

    Logger logger = org.slf4j.LoggerFactory.getLogger(WebConfig.class);

    public WebConfig() {
    }

/*    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }*/

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        logger.debug("Add jacksonxml");

        converters.add(mappingJackson2XmlHttpMessageConverter());
        //super.extendMessageConverters(converters);
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {

        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper objectMapper = xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return  new MappingJackson2XmlHttpMessageConverter(objectMapper);
        //converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }
}
