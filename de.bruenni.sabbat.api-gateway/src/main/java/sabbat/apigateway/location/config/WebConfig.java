package sabbat.apigateway.location.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        logger.debug("configureMessageConverters");

        converters.stream().forEach(c -> logger.debug("converter [type=" + c.getClass() + "]"));

        converters.add(myMappingJackson2XmlHttpMessageConverter(xmlMapper()));

        super.configureMessageConverters(converters);
    }

    /*    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }*/

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        logger.debug("extendMessageConverters");

        converters.stream().forEach(c -> logger.debug("converter [type=" + c.getClass() + "]"));

        /*

        Optional<HttpMessageConverter<?>> converter = converters.stream().filter(c -> c instanceof MappingJackson2XmlHttpMessageConverter).findFirst();
        if (converter.isPresent())
        {
            // change xmlmapper instance
            MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter = (MappingJackson2XmlHttpMessageConverter) converter.get();
            mappingJackson2XmlHttpMessageConverter.setObjectMapper(xmlMapper());
        }
*/

        super.extendMessageConverters(converters);
    }

    @Bean
    public XmlMapper xmlMapper()
    {
        logger.debug("xmlMapper");

        com.fasterxml.jackson.dataformat.xml.XmlMapper mapper = new XmlMapper();
        mapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter myMappingJackson2XmlHttpMessageConverter(XmlMapper xmlMapper) {

        logger.debug("mappingJackson2XmlHttpMessageConverter");

        return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
        //converter.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }
}
