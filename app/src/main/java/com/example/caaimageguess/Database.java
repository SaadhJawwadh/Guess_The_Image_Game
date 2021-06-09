package com.example.caaimageguess;

import java.util.Random;

public class Database {


    private final int noOfMakes = 29;
    private static int lastRandomIndex;


    private final Integer[] CarImages = {
            R.drawable.benz_0,R.drawable.benz_1,R.drawable.benz_2,R.drawable.benz_3,R.drawable.benz_4,R.drawable.benz_5,R.drawable.benz_6,R.drawable.benz_7,R.drawable.benz_8,R.drawable.benz_9,
            R.drawable.bmw_0,R.drawable.bmw_1,R.drawable.bmw_2,R.drawable.bmw_3,R.drawable.bmw_4,R.drawable.bmw_5,R.drawable.bmw_6,R.drawable.bmw_7,R.drawable.bmw_8,R.drawable.bmw_9,
            R.drawable.ford_0, R.drawable.ford_1,R.drawable.ford_2,R.drawable.ford_3,R.drawable.ford_4,R.drawable.ford_5,R.drawable.ford_6,R.drawable.ford_7,R.drawable.ford_8,R.drawable.ford_9};


    private final String[] Makes = {
            "Benz","Benz","Benz","Benz","Benz","Benz","Benz","Benz","Benz","Benz",
            "Bmw","Bmw","Bmw","Bmw","Bmw","Bmw","Bmw","Bmw","Bmw","Bmw",
            "Ford","Ford","Ford","Ford","Ford","Ford","Ford","Ford","Ford","Ford"};


    public Database(){

    }


    // takes index to return associated Make name
    public String getMakeName(int indexInArray){
        //Preventing array index out of bounds error
        if ((indexInArray <= noOfMakes) && (indexInArray >= 0)){
            return Makes[indexInArray];
        }
        else {
            System.out.println("Index out of bounds <-- Database.class");
            return "Index out of bounds <-- Database.class";
        }
    }

    // takes Make name to return index
    public int getIndex(String Make){
        for(int i = 0; i < noOfMakes; i++){
            if(Make.equals(Makes[i])) return i;
        }
        System.out.println("Make not found, return -1 <-- Database.class");
        return -1;
    }

    // returns random car
    public Integer getRandomCars(){

        Random rand = new Random();
        int randomNumber = rand.nextInt((noOfMakes) + 1);

        lastRandomIndex = randomNumber;
        return CarImages[randomNumber];
    }

    public String[] getAnswersArray(){
        return Makes;
    }

    public static int getLastRandomIndex(){
        return lastRandomIndex;
    }



}
