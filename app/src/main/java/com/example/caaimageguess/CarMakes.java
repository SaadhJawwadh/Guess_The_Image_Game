package com.example.caaimageguess;

import java.util.ArrayList;

public class CarMakes {

    //ArrayList for the car makes
    public ArrayList<String> carMakeArray = new ArrayList<>();

    //To add the Makes names to the ArrayList
    public CarMakes(){
        this.carMakeArray.add("bmw");
        this.carMakeArray.add("ford");
        this.carMakeArray.add("benz");
    }

    public ArrayList<String> getCarMakeArray() {
        return carMakeArray;
    }

    public int getNumberOfCarImagesOfOneMake() {
        //Number of images for a one brand
        return 10;
    }
}
