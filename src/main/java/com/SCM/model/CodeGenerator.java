package com.SCM.model;

import java.util.concurrent.atomic.AtomicInteger;

public class CodeGenerator  {


    private static final AtomicInteger count = new AtomicInteger(100000000);

    public Long autoIncrement() {
        long y=00000001;
        long x=y*count.incrementAndGet();
        return x;
    }


    public String WORKODERNUMBER(){
        String x="W_ODER";
        return x+autoIncrement();
    }





}
