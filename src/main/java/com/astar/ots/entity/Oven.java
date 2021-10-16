package com.astar.ots.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "oven")
@NoArgsConstructor
@JsonPropertyOrder({"id", "code", "description", "temperatures"})
public class Oven implements Serializable {

    @JsonProperty(value = "id")
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonProperty(value = "code")
    @Column(name = "code")
    private String code;

    @JsonProperty(value = "description")
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Column(name = "created_on")
    private Date createdOn;

    @JsonIgnore
    @Column(name = "modified_on")
    private Date modifiedOn;

    @JsonProperty(value = "temperatures")
    @OneToMany(mappedBy = "oven", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Temperature> temperatures;

    @PrePersist
    public void prePersist() {
        createdOn = new Date();
        modifiedOn = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedOn = new Date();
    }
}
