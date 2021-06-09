package com.example.caaimageguess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class CarImage extends AppCompatActivity {

    private String imageName1;
    private String imageName2;
    private String imageName3;
    private TextView result;
    private String guessingMake;
    private int imageCount;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private boolean isTimerSwitchOn = false;

    public ArrayList<String> carMakeArray = new ArrayList<>();
    private int numberOfCarImagesOfOneMake;
    CarMakes carMake = new CarMakes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        this.carMakeArray = carMake.getCarMakeArray();
        this.numberOfCarImagesOfOneMake = carMake.getNumberOfCarImagesOfOneMake();

        timer = findViewById(R.id.Timer_Image);
        Intent intent = getIntent();
        isTimerSwitchOn = intent.getExtras().getBoolean("switchValue");  //To get the boolean value from main activity

        if (isTimerSwitchOn){   //Check weather switch is on or not
            switchTimer();
            start();
        }else {
            start();
        }
    }

    //To start the countdown in the text view
    public void switchTimer(){
        countDownTimer = new CountDownTimer(21000,1000) {   //Create the countDownTimer
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText(seconds);
            }
            @Override
            public void onFinish() {   //method for when the timer finished
                imageCount++;
                result = findViewById(R.id.feedback_Image);
                String notAnswered = "NOT ANSWERED";
                result.setTextColor(Color.RED);
                result.setText(notAnswered);

                highlightedImage();
            }
        };
        countDownTimer.start();    //To Start the countdown
    }

    //Method for the when the activity start
    @SuppressLint("UseCompatLoadingForDrawables")
    public void start(){

        imageCount = 0;  //To get the count of the clicked images

        ImageView image_Make1 = findViewById(R.id.car_image1);
        ImageView image_Make2 = findViewById(R.id.car_image2);
        ImageView image_Make3 = findViewById(R.id.car_image3);

        ArrayList<String> randomImagesChosen;
        randomImagesChosen = getRandomMakeArray();  //To add the chosen random 3 images array

        guessingMake = randomImagesChosen.get(getRandomInteger(0,2));   //To get the image name of guessing Make

        //To assign chosen images to the image views
        imageName1 = randomImagesChosen.get(0) + "_" + getRandomInteger(1, numberOfCarImagesOfOneMake);
        image_Make1.setImageDrawable( getResources().getDrawable(getImageID(imageName1, getApplicationContext())));

        imageName2 = randomImagesChosen.get(1) + "_" +getRandomInteger(1, numberOfCarImagesOfOneMake);
        image_Make2.setImageDrawable( getResources().getDrawable(getImageID(imageName2, getApplicationContext())));

        imageName3 = randomImagesChosen.get(2) + "_" + getRandomInteger(1, numberOfCarImagesOfOneMake);
        image_Make3.setImageDrawable( getResources().getDrawable(getImageID(imageName3, getApplicationContext())));

        result = findViewById(R.id.Answer_Image);
        result.setText(guessingMake.toUpperCase());  //To show the guessing Make
    }

    //To check that the image name is match with the images in the drawable folder
    protected static int getImageID(final String imageName, final Context context) {
        final int ImageID = context.getResources().getIdentifier(imageName, "drawable", context.getApplicationInfo().packageName);
        if (ImageID == 0) {
            throw new IllegalArgumentException("No image string found with name " + imageName);
        }else{
            return ImageID;
        }
    }

    //To get a random Make name from the Make array
    public String getRandomCarMake(){
        return carMakeArray.get(getRandomInteger(0,(carMakeArray.size()-1)));
    }

    //To get a random integer between the number of images from one Make
    public static int getRandomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    //To get 3 unique random images
    public ArrayList<String> getRandomMakeArray(){

        ArrayList<String> checkingMakeArray = new ArrayList<>();

        do{
            String chekingCarMake = getRandomCarMake();
            if(!checkingMakeArray.contains(chekingCarMake)){
                checkingMakeArray.add(chekingCarMake);
            }
        }while(checkingMakeArray.size()!=3);
        return checkingMakeArray;
    }

    //Method for the Next button
    public void OnClick(View view){
        if (imageCount != 0){                           //To refresh the activity
            start();
            if(isTimerSwitchOn){
                countDownTimer.cancel();                 //To reset the timer
                switchTimer();
            }
            result = findViewById(R.id.feedback_Image);
            result.setText("");

            ImageView image_Make1 = findViewById(R.id.car_image1);
            ImageView image_Make2 = findViewById(R.id.car_image2);
            ImageView image_Make3 = findViewById(R.id.car_image3);

            image_Make1.setBackgroundColor(Color.WHITE);
            image_Make2.setBackgroundColor(Color.WHITE);
            image_Make3.setBackgroundColor(Color.WHITE);


        }else {               //if the user not select an image
            Toast.makeText(getApplicationContext(), "Touch an Image !",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //To highlight the correct Car image
    public void highlightedImage(){
        String make1 = imageName1.split("_")[0];     //to get the Make name 1 form the image name
        String make2 = imageName2.split("_")[0];     //to get the Make name 2 form the image name
        String make3 = imageName3.split("_")[0];     //to get the Make name 3 form the image name

        ImageView image_Make1 = findViewById(R.id.car_image1);
        ImageView image_Make2 = findViewById(R.id.car_image2);
        ImageView image_Make3 = findViewById(R.id.car_image3);

        if (guessingMake.equals(make1)){
            image_Make1.setBackgroundColor(Color.YELLOW);
        }else if (guessingMake.equals(make2)){
            image_Make2.setBackgroundColor(Color.YELLOW);
        } else if(guessingMake.equals(make3)){
            image_Make3.setBackgroundColor(Color.YELLOW);
        }
    }

    //Method for the clicked image1
    public void clickableImage1(View view){
        imageCount++;

        String Make1 = imageName1.split("_")[0];     //to get the Make name 1 form the image name

        if (imageCount == 1){
            if (guessingMake.equals(Make1)){
                String answer = "CORRECT !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.GREEN);
                result.setText(answer.toUpperCase());

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }else {
                String answer = "WRONG !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.RED);
                result.setText(answer.toUpperCase());
                highlightedImage();                       //To show the correct image

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }
        }else {                                         //With this user can't do multiple attempts
            Toast.makeText(getApplicationContext(), "Press the Next button to Continue",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Method for the clicked image2
    public void clickableImage2(View view){
        imageCount++;
        String Make2 = imageName2.split("_")[0];     //to get the Make name 2 form the image name

        if (imageCount == 1){
            if (guessingMake.equals(Make2)){
                String answer = "CORRECT !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.GREEN);
                result.setText(answer.toUpperCase());

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }else {
                String answer = "WRONG !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.RED);
                result.setText(answer.toUpperCase());
                highlightedImage();     // *Highlight Correct Image                     //To show the correct image

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }
        }else {                                             //With this user can't do multiple attempts
            Toast.makeText(getApplicationContext(), "Press the Next button to Continue",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Method for the clicked image2
    public void clickableImage3(View view){
        imageCount++;
        String Make3 = imageName3.split("_")[0];     //to get the Make name 3 form the image name


        if (imageCount == 1){
            if (guessingMake.equals(Make3)){
                String answer = "CORRECT !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.GREEN);
                result.setText(answer.toUpperCase());

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }else {
                String answer = "WRONG !";
                result = findViewById(R.id.feedback_Image);
                result.setTextColor(Color.RED);
                result.setText(answer.toUpperCase());
                highlightedImage();                          //To show the correct image

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }
        }else {                                               //With this user can't do multiple attempts
            Toast.makeText(getApplicationContext(), "Press the Next button to Continue",
                    Toast.LENGTH_SHORT).show();
        }
    }
}