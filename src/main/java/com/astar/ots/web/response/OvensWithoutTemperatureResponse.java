package com.astar.ots.web.response;

import com.astar.ots.entity.Oven;
import com.astar.ots.model.OvenWithoutTemperature;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OvensWithoutTemperatureResponse {

    @JsonProperty(value = "ovens")
    private List<OvenWithoutTemperature> ovens;
}
