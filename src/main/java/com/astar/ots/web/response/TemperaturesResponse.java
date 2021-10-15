package com.astar.ots.web.response;

import com.astar.ots.entity.Temperature;
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
public class TemperaturesResponse {

    @JsonProperty(value = "temperatures")
    private List<Temperature> temperatures;
}
