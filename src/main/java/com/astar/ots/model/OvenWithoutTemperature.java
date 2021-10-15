package com.astar.ots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "code", "description", "createdOn", "modifiedOn"})
public class OvenWithoutTemperature {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "description")
    private String description;
}
