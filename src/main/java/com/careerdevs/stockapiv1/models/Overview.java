package com.careerdevs.stockapiv1.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    //62:00

}
