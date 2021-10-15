package com.astar.ots.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OvenTemperaturesRequest implements Serializable {

    @Schema(description = "Oven Id", defaultValue = "1")
    @NotBlank(message = "Oven Id can not be blank")
    private String ovenId;
}
