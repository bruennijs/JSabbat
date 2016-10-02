package sabbat.apigateway.location.builder;

import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;

/**
 * Created by bruenni on 02.10.16.
 */
public class LocationApiDtoConverterBuilder {
    public LocationApiDtoConverter build() {
        return new LocationApiDtoConverter();
    }
}
