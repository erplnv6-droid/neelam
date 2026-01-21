package com.SCM.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String zoneName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "zone_id")
    private List<Staff> staff;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<State_Zone>  state_zone;


  
}
