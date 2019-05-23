package com.training.myassistantapp;

public class VehicleRegistration {
    public String Make;
    public String Model;
    public String Year;
    public String Color;
    public String Uid;
    public double Latitude;
    public double Longitiude;


public VehicleRegistration()
{

}

    public VehicleRegistration(String make, String model, String year,
                               String color, double latitude, double longitiude, String uid) {
       this.Make = make;
        this.Model = model;
        this.Year = year;
        this.Color = color;
        this.Uid = uid;
        this.Latitude = latitude;
        this.Longitiude = longitiude;

    }

    @Override
    public String toString() {
        return "VehicleRegistration{" +
                "Make='" + Make + '\'' +
                ", Model='" + Model + '\'' +
                ", year='" + Year + '\'' +
                ", color='" + Color + '\'' +
                ", Vid='" + Uid + '\'' +
                ", Latitude=" + Latitude +
                ", Longitiude=" + Longitiude +
                '}';
    }

    public void add(VehicleRegistration vehicleRegistration) {
    }
}
