package sabbat.apigateway.location.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive
import rx.Observable;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;

import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 04.08.16.
 */

@RestController
//@ResponseBody
@RequestMapping(path = "/location/api/v2")
public class ReactiveMapMyTracksController {

    /**
     * https://spring.io/blog/2016/02/09/reactive-spring
     * @param requestType
     * @param title
     * @param points
     * @param tags
     * @return
     * @throws Exception
     */
    @RequestMapping(
            path = "",
            method = RequestMethod.POST,
            produces = "application/xml",
            consumes = "application/x-www-form-urlencoded")

    public Observable<ResponseEntity<ActivityCreatedResponse>> startActivity(
            @RequestParam(value = "request") String requestType,
            @RequestParam(value=  "title", required = false) String title,
            @RequestParam(value=  "points") String points,
            @RequestParam(value = "tags", required = false) String tags)  throws Exception {
        return Observable.interval(3, TimeUnit.SECONDS).take(1).map(n -> {
            return new ResponseEntity<ActivityCreatedResponse>(HttpStatus.CREATED);
        });
    }
}
