package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GstMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;  
    private float per;
    private int ledgerId;
    private int type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPer() {
        return per;
    }

    public void setPer(float per) {
        this.per = per;
    }

    public int getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(int ledgerId) {
        this.ledgerId = ledgerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
