package com.astar.ots.web.rest.impl;

import com.astar.ots.entity.Oven;
import com.astar.ots.entity.Temperature;
import com.astar.ots.model.OvenWithoutTemperature;
import com.astar.ots.service.OvenService;
import com.astar.ots.util.Constants;
import com.astar.ots.util.Util;
import com.astar.ots.web.request.OvenTemperaturesRequest;
import com.astar.ots.web.request.OvensTemperaturesRequest;
import com.astar.ots.web.response.OvensWithTemperatureResponse;
import com.astar.ots.web.response.OvensWithoutTemperatureResponse;
import com.astar.ots.web.response.TemperaturesResponse;
import com.astar.ots.web.rest.OvenController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(path = Constants.FORWARD_SLASH)
public class OvenControllerImpl implements OvenController {

    @Autowired
    private Validator validator;

    @Autowired
    private OvenService ovenService;

    @Override
    public ResponseEntity getAllOvensWithoutTemperatures() {

        log.debug("API called: Get all ovens without temperatures");
        Optional<List<Oven>> ovenList = ovenService.findAll();

        if(ovenList.isPresent()) {

            OvensWithoutTemperatureResponse response = new OvensWithoutTemperatureResponse(
                    ovenList.get()
                            .stream()
                            .map(oven -> new OvenWithoutTemperature(oven.getId(), oven.getCode(), oven.getDescription()))
                            .collect(Collectors.toList()));

            log.info("Successful response : {}" , response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            log.error(Constants.ErrorMessages.CANNOT_FOUND_OVEN);
            return new ResponseEntity<>(Constants.ErrorMessages.CANNOT_FOUND_OVEN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity getTemperatureForOven(OvenTemperaturesRequest ovenTemperaturesRequest) {

        log.debug("API called: Get temperature for oven - Request parameters: {}", ovenTemperaturesRequest);

        try {

            // Request parameter validation
            doValidate(ovenTemperaturesRequest);
            final long ovenId = Long.parseLong(ovenTemperaturesRequest.getOvenId());

            // Get Oven with temperatures
            try {

                Optional<Oven> oven = ovenService.getById(ovenId);

                if(oven.isPresent()) {

                    TemperaturesResponse response = new TemperaturesResponse(oven.get().getTemperatures());
                    log.info("Successful response : {}" , response);
                    return new ResponseEntity<>(response, HttpStatus.OK);

                } else {
                    log.error(Constants.ErrorMessages.CANNOT_FOUND_OVEN_FOR_ID);
                    return new ResponseEntity<>(Constants.ErrorMessages.CANNOT_FOUND_OVEN_FOR_ID, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } catch (Exception e) {
                log.error("Exception occurred while finding oven for OvenID - {} : {}", ovenId , e.getMessage());
                return new ResponseEntity<>(Constants.ErrorMessages.CANNOT_FOUND_OVEN_FOR_ID, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            log.error("Request parameter validation error: {}", e.getMessage());
            return new ResponseEntity<>(Constants.ErrorMessages.INVALID_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getAllOvensWithTemperatures(OvensTemperaturesRequest ovensTemperaturesRequest) {

        log.debug("API called: Get all ovens with their temperatures for  - Request parameters: {}", ovensTemperaturesRequest);

        if(StringUtils.isBlank(ovensTemperaturesRequest.getStartDate())
                && StringUtils.isBlank(ovensTemperaturesRequest.getEndDate())) {

            // Empty start and end date parameters
            Optional<List<Oven>> ovenListAll = ovenService.findAll();

            if(ovenListAll.isPresent()) {

                // Get all available ovens and sort their temperature reported date by descending oder and get first one
                ovenListAll.get().forEach(oven -> {
                    if (oven.getTemperatures() != null) {
                        oven.setTemperatures(
                                Collections.singletonList(
                                        oven.getTemperatures().stream().max(Comparator.comparing(Temperature::getReportedOn)).orElseGet(null)
                                ));
                    }
                });

                OvensWithTemperatureResponse response = new OvensWithTemperatureResponse(ovenListAll.get());
                log.info("Successful response : {}", response);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                log.error(Constants.ErrorMessages.CANNOT_FOUND_OVEN);
                return new ResponseEntity<>(Constants.ErrorMessages.CANNOT_FOUND_OVEN, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else if (!StringUtils.isBlank(ovensTemperaturesRequest.getStartDate())
                && !StringUtils.isBlank(ovensTemperaturesRequest.getEndDate())) {

            try {

                // None empty start and end date parameters
                final Date start = Util.getDate(ovensTemperaturesRequest.getStartDate());
                final Date end = Util.getDate(ovensTemperaturesRequest.getEndDate());

                Optional<List<Oven>> ovenList = ovenService.findAll();

                if(ovenList.isPresent()) {

                    // Get all available ovens and filter temperatures based on given time period
                    ovenList.get().forEach(oven -> {
                        if(oven.getTemperatures() != null) {
                            oven.setTemperatures(
                                    oven.getTemperatures().stream()
                                            .filter(temperature ->
                                                    ((temperature.getReportedOn().getTime() >= start.getTime())
                                                            && (temperature.getReportedOn().getTime() <= end.getTime())))
                                            .collect(Collectors.toList()));
                        }
                    });

                    OvensWithTemperatureResponse response = new OvensWithTemperatureResponse(ovenList.get());
                    log.info("Successful response : {}" , response);
                    return new ResponseEntity<>(response, HttpStatus.OK);

                } else {
                    log.error(Constants.ErrorMessages.CANNOT_FOUND_OVEN);
                    return new ResponseEntity<>(Constants.ErrorMessages.CANNOT_FOUND_OVEN, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } catch (ParseException e) {
                log.error("Request parameter validation error: {}", e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

        } else {
            log.error(Constants.ErrorMessages.INVALID_PARAMETER);
            return new ResponseEntity<>(Constants.ErrorMessages.INVALID_PARAMETER, HttpStatus.BAD_REQUEST);
        }
    }

    private void doValidate(OvenTemperaturesRequest ovenTemperaturesRequest) {
        Set<ConstraintViolation<OvenTemperaturesRequest>> violations = validator.validate(ovenTemperaturesRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
