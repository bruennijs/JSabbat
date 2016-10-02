package sabbat.apigateway.location.systemtest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruenni on 02.10.16.
 */
public class MapMyTracksApiClient {

    private final RestTemplate restTemplate;
    private String url;

    public MapMyTracksApiClient(String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //messageConverters.add(new MappingJacksonHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    /**
     * Starts activity
     * @param title
     * @return
     */
    public ResponseEntity<ActivityCreatedResponse> startActivity(String title) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        HttpHeaders headers = new HttpHeaders();

        body.add("request", "start_activity");
        body.add("title", title);
        body.add("points", "0.0 0.0");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        return this.restTemplate.postForEntity(this.url, request, ActivityCreatedResponse.class);
    }
}
