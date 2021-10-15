package com.astar.ots.entity;

import com.astar.ots.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "temperature")
@JsonPropertyOrder({"id", "value", "description", "reported_on"})
public class Temperature implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonProperty(value = "value")
    @Column(name = "value")
    private Float value;

    @JsonProperty(value = "description")
    @Column(name = "description")
    private String description;

    @JsonProperty(value = "reportedOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT, locale = "en_GB", timezone = Constants.SG_TIME_ZONE)
    @Column(name = "reported_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportedOn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oven_id", nullable = false)
    private Oven oven;

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", reportedOn=" + reportedOn +
                '}';
    }
}
