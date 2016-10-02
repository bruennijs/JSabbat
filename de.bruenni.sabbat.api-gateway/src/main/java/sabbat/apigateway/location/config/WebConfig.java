package sabbat.apigateway.location.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public WebConfig() {
    }

/*    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }*/



    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //super.extendMessageConverters(converters);
        //converters.add(mappingJackson2XmlHttpMessageConverter());
    }

/*    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        //converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
        return converter;
    }*/
}
