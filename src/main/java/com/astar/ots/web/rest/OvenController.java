package com.astar.ots.web.rest;

import com.astar.ots.util.Constants;
import com.astar.ots.web.request.OvenTemperaturesRequest;
import com.astar.ots.web.request.OvensTemperaturesRequest;
import com.astar.ots.web.response.OvensWithTemperatureResponse;
import com.astar.ots.web.response.OvensWithoutTemperatureResponse;
import com.astar.ots.web.response.TemperaturesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Oven Detail", description = "REST API for store Oven details")
public interface OvenController {

    /**
     * Get all available Ovens without their Temperature
     * @return List of Ovens without their Temperature
     *
     * Ex: GET https://dev-harmony-tools.southeastasia.cloudapp.azure.com:44391/ovens
     */
    @Operation(summary = "Get all Ovens", description = "Get detail about all Ovens")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all the available Ovens",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OvensWithoutTemperatureResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = Constants.ErrorMessages.CANNOT_FOUND_OVEN)
    })
    @RequestMapping(
            value = Constants.FORWARD_SLASH + Constants.RestUrls.OVENS,
            method = RequestMethod.GET,
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity getAllOvensWithoutTemperatures();

    /**
     * Get all available Temperatures for given Oven
     * @return List of Temperatures
     *
     * Ex: POST https://dev-harmony-tools.southeastasia.cloudapp.azure.com:44391/ovens/temperature/{ovenId}
     */
    @Operation(summary = "Get all Temperatures for one Oven", description = "Get Temperatures for given Oven")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all the available Temperatures for given Oven",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = TemperaturesResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = Constants.ErrorMessages.CANNOT_FOUND_OVEN_FOR_ID)
    })
    @RequestMapping(
            value = Constants.FORWARD_SLASH + Constants.RestUrls.OVENS + Constants.FORWARD_SLASH + Constants.RestUrls.TEMPERATURE,
            method = RequestMethod.POST,
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity getTemperatureForOven(@RequestBody OvenTemperaturesRequest ovenTemperaturesRequest);


    /**
     * Get all available Ovens with their Temperatures belonging into given period
     * @return List of Ovens with their Temperatures
     *
     * Ex: POST https://dev-harmony-tools.southeastasia.cloudapp.azure.com:44391/oven-temperatures
     */
    @Operation(summary = "Get all Ovens with Temperatures", description = "Get all Ovens with Temperatures for given period")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return Ovens with Temperatures for given for given date/time period",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OvensWithTemperatureResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = Constants.ErrorMessages.INVALID_PARAMETER),
            @ApiResponse(
                    responseCode = "500",
                    description = "Can not found any Oven")
    })
    @RequestMapping(
            value = Constants.FORWARD_SLASH + Constants.RestUrls.OVEN_TEMPERATURES,
            method = RequestMethod.POST,
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    ResponseEntity getAllOvensWithTemperatures(@RequestBody OvensTemperaturesRequest ovensTemperaturesRequest);
}
