package sabbat.apigateway.location.systemtest;

import infrastructure.util.Tuple2;
import infrastructure.web.client.AuthenticationRestTemplate;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.data.geo.Point;
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
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.ActivityUpdatedResponse;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by bruenni on 02.10.16.
 */
public class MapMyTracksApiClient {

    private final RestTemplate restTemplate;
    private String url;

    public MapMyTracksApiClient(String url) {
        this.url = url;
        this.restTemplate = new AuthenticationRestTemplate().addBasicAuthentication("test", "password");
        //this.restTemplate = new AuthenticationRestTemplate().addBasicAuthentication("bruenni", "bruenni");
        //this.restTemplate = new RestTemplate();

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
        body.add("points", "0.0 0.0 0 0");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        return this.restTemplate.postForEntity(this.url, request, ActivityCreatedResponse.class);
    }

    /**
     * Updates activity
     * @return
     */
    public ResponseEntity<ActivityUpdatedResponse> updateActivity(long id, Iterable<Tuple2<Point, Date>> tuples) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        HttpHeaders headers = new HttpHeaders();

        body.add("request", "update_activity");
        body.add("activity_id", Long.valueOf(id).toString());

        Stream<String> seriallizesTuples = StreamSupport.stream(tuples.spliterator(), false).map(tuple -> SerializeTuple(tuple));


        Optional<String> pointsValue = seriallizesTuples.reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return String.format("%1s %2s", s, s2);
            }
        });

        body.add("points", pointsValue.get());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        return this.restTemplate.postForEntity(this.url, request, ActivityUpdatedResponse.class);
    }

    public ResponseEntity<ActivityStoppedResponse> stopActivity(long id) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        HttpHeaders headers = new HttpHeaders();

        body.add("request", "stop_activity");
        body.add("activity_id", Long.valueOf(id).toString());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

        return this.restTemplate.postForEntity(this.url, request, ActivityStoppedResponse.class);
    }

    private String SerializeTuple(Tuple2<Point, Date> tuple) {
        return String.format("%1s %2s %3s %4d", Double.valueOf(tuple.getT1().getY()).toString(), Double.valueOf(tuple.getT1().getX()).toString(), Long.valueOf(0l).toString(), tuple.getT2().getTime() / 1000);
    }
}
