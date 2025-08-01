package com.loaizasoftware.domain.models;

public class Wind {
    public double speed;
    public int deg;

    public String getFormattedWindSpeed(){
        return "Wind: "+speed+" m/s";
    }

}
