package com.astar.ots.web.rest.impl;

import com.astar.ots.entity.Oven;
import com.astar.ots.entity.Temperature;
import com.astar.ots.service.OvenService;
import com.astar.ots.service.TemperatureService;
import com.astar.ots.util.Util;
import com.astar.ots.web.request.OvenTemperaturesRequest;
import com.astar.ots.web.request.OvensTemperaturesRequest;
import com.astar.ots.web.response.OvensWithTemperatureResponse;
import com.astar.ots.web.response.OvensWithoutTemperatureResponse;
import com.astar.ots.web.response.TemperaturesResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OvenControllerImplTest {

    private Oven oven;
    private Temperature temperature;
    private String reportedOn = "2021-10-16 10:22:53";
    private String startDate = "2021-10-16 10:22:52";
    private String endDate = "2021-10-16 10:22:54";

    @Autowired
    private OvenService ovenService;

    @Autowired
    private OvenControllerImpl ovenControllerImpl;

    @Autowired
    private TemperatureService temperatureService;

    @Before
    public void setUp() throws Exception {

        // Insert on Oven
        oven = new Oven();
        oven.setCode("OVN-100-100");
        oven.setDescription("This is test Oven");
        ovenService.save(oven);

        // Insert on Temperature
        temperature = new Temperature();
        temperature.setOven(oven);
        temperature.setValue(45.23F);
        temperature.setDescription("This is test temperature");
        temperature.setReportedOn(Util.getDate(reportedOn));
        temperatureService.save(temperature);
    }

    @Test
    public void getAllOvensWithoutTemperaturesTest() {

        ResponseEntity<OvensWithoutTemperatureResponse> response = ovenControllerImpl.getAllOvensWithoutTemperatures();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        OvensWithoutTemperatureResponse responseBody = response.getBody();
        Assert.assertEquals(responseBody.getOvens().get(0).getCode(), oven.getCode());
    }

    @Test
    public void getTemperatureForOvenTest() {

        OvenTemperaturesRequest request = new OvenTemperaturesRequest(String.valueOf(oven.getId()));

        ResponseEntity<TemperaturesResponse> response = ovenControllerImpl.getTemperatureForOven(request);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        TemperaturesResponse responseBody = response.getBody();
        assert responseBody != null;
        Assert.assertEquals(responseBody.getTemperatures().get(0).getValue(), temperature.getValue());
    }

    @Test
    public void getAllOvensWithTemperatures() {

        // Without period
        OvensTemperaturesRequest request = new OvensTemperaturesRequest();

        ResponseEntity<OvensWithTemperatureResponse> response = ovenControllerImpl.getAllOvensWithTemperatures(request);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        OvensWithTemperatureResponse responseBody = response.getBody();
        assert responseBody != null;
        Assert.assertEquals(responseBody.getOvens().get(0).getCode(), oven.getCode());

        // With period
        request = new OvensTemperaturesRequest(startDate, endDate);

        response = ovenControllerImpl.getAllOvensWithTemperatures(request);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        responseBody = response.getBody();
        assert responseBody != null;
        Assert.assertEquals(responseBody.getOvens().get(0).getCode(), oven.getCode());
    }
}
