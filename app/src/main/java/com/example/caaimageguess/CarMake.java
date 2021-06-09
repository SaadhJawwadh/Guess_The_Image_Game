package com.example.caaimageguess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;



public class CarMake extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerLabel;
    private String makeName;
    private Button button;
    private TextView correctMake;
    private TextView result;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private boolean isTimerSwitchOn = false;

    CarMakes carMake = new CarMakes();
    public ArrayList<String> carMakeArray = new ArrayList<>();
    private int numberOfCarImagesOfOneMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        this.carMakeArray = carMake.getCarMakeArray();
        this.numberOfCarImagesOfOneMake = carMake.getNumberOfCarImagesOfOneMake();

        timer = findViewById(R.id.Timer_Image);
        Intent intent = getIntent();
        isTimerSwitchOn = intent.getExtras().getBoolean("switchValue");  //To get the boolean value from main activity

        if (isTimerSwitchOn){    //Check weather switch is on or not
            switchTimer();
        }
        start();

    }

    //To start the countdown in the text view
    public void switchTimer(){
        countDownTimer = new CountDownTimer(21000,1000) {  //Create the countDownTimer
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText(seconds);
            }
            @Override
            public void onFinish() {   //method for when the timer finished
                submitAuto();
            }
        };

        countDownTimer.start();       //To Start the countdown
    }

    //Method for the when the activity start
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void start(){

        ImageView image_make = findViewById(R.id.imageView_car);

        makeName = getRandomCarMake();
        String imageName = makeName + "_" + getRandomInteger(1, numberOfCarImagesOfOneMake);  //To get the random imageName

        //To assign the random image to the image view
        image_make.setImageDrawable( getResources().getDrawable(getImageID(imageName, getApplicationContext())));

        // Create the spinner.
        Spinner spinner = findViewById(R.id.spinner_Make);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.make_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        //Set the button label to Submit
        button = findViewById(R.id.Submit_Make);
        button.setText("Submit");
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

    //To get the selected label from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerLabel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    //Method to auto submit when time over
    @SuppressLint("SetTextI18n")
    public void submitAuto(){

        button.setText("Next");
        if (spinnerLabel.equals(makeName) ){
            String answer = "CORRECT !";
            result = findViewById(R.id.my_answer);
            result.setTextColor(Color.GREEN);
            result.setText(answer);

            correctMake = findViewById(R.id.correct_answer);
            correctMake.setText(makeName.toUpperCase());

        }else {
            String answer = "WRONG !";
            result = findViewById(R.id.my_answer);
            result.setTextColor(Color.RED);
            result.setText(answer);

            correctMake = findViewById(R.id.correct_answer);
            correctMake.setText(makeName.toUpperCase());
        }

    }

    //To check the validation of the users answer by clicking the submit button
    @SuppressLint("SetTextI18n")
    public void onClickButton(View view) {
        if (button.getText().equals("Submit")){
            button.setText("Next");
            if (spinnerLabel.equals(makeName) ){
                String answer = "CORRECT !";
                result = findViewById(R.id.my_answer);
                result.setTextColor(Color.GREEN);
                result.setText(answer);

                correctMake = findViewById(R.id.correct_answer);
                correctMake.setText(makeName.toUpperCase());

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }

            }else {
                String answer = "WRONG !";
                result = findViewById(R.id.my_answer);
                result.setTextColor(Color.RED);
                result.setText(answer);

                correctMake = findViewById(R.id.correct_answer);
                correctMake.setText(makeName.toUpperCase());

                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }
        }else {        //When the button label is set to Next
            start();
            if (isTimerSwitchOn){
                countDownTimer.cancel();   //To reset the timer
                switchTimer();
            }

            result = findViewById(R.id.my_answer);
            result.setText("");

            correctMake = findViewById(R.id.correct_answer);
            correctMake.setText("");
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}