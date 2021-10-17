package com.astar.ots.service;

import com.astar.ots.exception.PollingException;
import com.astar.ots.util.Util;
import com.astar.ots.web.request.OvensTemperaturesRequest;
import com.astar.ots.web.request.UserSignInRequest;
import com.astar.ots.web.response.OvensWithTemperatureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static com.astar.ots.util.Constants.*;
import static com.astar.ots.util.Constants.ErrorMessages.GET_JWT_TOKEN;
import static com.astar.ots.util.Constants.RestUrls.*;
import static com.astar.ots.util.Constants.UserDetail.PASSWORD;
import static com.astar.ots.util.Constants.UserDetail.USERNAME;

@Slf4j
@Service
public class PollingService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.polling-result-file-path::ovens_temperatures.json}")
    private String outputFilePath = FILE_NAME;

    public Optional<String> getJwtToken() throws Exception {

        HttpHeaders headers = Util.getHttpHeaders();
        UserSignInRequest userSignInRequest = new UserSignInRequest(USERNAME, PASSWORD);
        final URI uri = Util.getUri(BASE_URL + FORWARD_SLASH + APP_NAME + FORWARD_SLASH + USERS + FORWARD_SLASH + SIGN_IN);

        HttpEntity<UserSignInRequest> request = new HttpEntity<>(userSignInRequest, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        // Verify request succeed
        if(result.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.ofNullable(result.getBody());
        } else {
            log.error(GET_JWT_TOKEN);
            throw new PollingException(GET_JWT_TOKEN);
        }
    }

    public void getOvenData() {

        try {

            // Get JwtToken
            Optional<String> jwtToken = getJwtToken();

            if(jwtToken.isPresent()) {

                log.info("JWT token value: {}", jwtToken.get());

                HttpHeaders headers = Util.getHttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AUTHORIZATION, BEARER + jwtToken.get());

                OvensTemperaturesRequest ovensTemperaturesRequest = new OvensTemperaturesRequest("", "");

                final URI uri = Util.getUri(BASE_URL + FORWARD_SLASH + APP_NAME + FORWARD_SLASH + OVEN_TEMPERATURES);

                HttpEntity request = new HttpEntity<>(ovensTemperaturesRequest, headers);
                ResponseEntity<OvensWithTemperatureResponse> result = restTemplate.postForEntity(uri, request, OvensWithTemperatureResponse.class);

                // Verify request succeed
                if(result.getStatusCode().equals(HttpStatus.OK)) {

                    OvensWithTemperatureResponse response = result.getBody();

                    if(response != null) {

                        // Write into JSON file
                        try {
                            Util.doWriteIntoJsonFile(outputFilePath, response);
                        } catch (Exception e) {
                            log.error("Exception occurred while writing response into JsonFile:\nResponse-{}\nError-{}", result.getBody(), e.getMessage());
                        }

                    } else {
                        log.error("Empty response for Ovens-Temperatures request");
                    }

                } else {
                    log.error("Invalid response for Ovens-Temperatures request:\nStatus-{}\nResult-{}", result.getStatusCode(), result.getBody());
                }

            } else {
                log.error("Empty JWT token value");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
