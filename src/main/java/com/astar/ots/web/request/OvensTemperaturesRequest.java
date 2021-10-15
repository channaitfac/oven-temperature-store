package com.astar.ots.web.request;

import com.astar.ots.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Schema
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OvensTemperaturesRequest {

    @Schema(description = "Start Date (" + Constants.DATE_TIME_FORMAT + ")", defaultValue = "2021-10-18 10:00:00")
    private String startDate;

    @Schema(description = "End Date (" + Constants.DATE_TIME_FORMAT + ")", defaultValue = "2021-10-18 22:00:00")
    private String endDate;
}
