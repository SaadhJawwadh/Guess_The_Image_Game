package com.example.caaimageguess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.view.View.INVISIBLE;

public class AdvanceLevel extends AppCompatActivity {

    private final int MAX_TIME_4_TIMER =20;
    private final int MAX_ATTEMPTS = 3;
    private TextView timerText;
    private boolean timerOn;
    private int timeLeft = MAX_TIME_4_TIMER;
    private Timer timer;
    private Button btn;
    private ImageView car1, car2, car3;
    private TextView answ1, answ2, answ3;
    private TextView correctWrong, scoreTv;
    private EditText editText1, editText2, editText3;
    private final ArrayList<Integer> carList = new ArrayList<>();
    private int scoreInt = 0;
    private int attempts = 0;
    private String answerString1, answerString2, answerString3;
    private final Database db = new Database();
    private boolean firstRun = true;
    //private boolean lastRun = false;
    private boolean scoreOpen1 = true, scoreOpen2 = true, scoreOpen3 = true;


    // ex: if returns true false false--> first edittext is correct
    private final ArrayList<Boolean> correctnessList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_level);
        start();
    }

    public void start() {
        btn = findViewById(R.id.lvl4Btn);

        car1 = findViewById(R.id.CarImageAdvance1);
        car2 = findViewById(R.id.CarImageAdvance2);
        car3 = findViewById(R.id.CarImageAdvance3);

        editText1 = findViewById(R.id.AdvacePlaintText1);
        editText2 = findViewById(R.id.AdvacePlaintText2);
        editText3 = findViewById(R.id.AdvacePlaintText3);
        scoreTv = findViewById(R.id.lvl4ScoreChanging);

        answ1 = findViewById(R.id.AdvanceAnswer01);
        answ2 = findViewById(R.id.AdvanceAnswer02);
        answ3 = findViewById(R.id.AdvanceAnswer03);

        timerText = findViewById(R.id.lvl4Timer);

        correctnessList.add(false);
        correctnessList.add(false);
        correctnessList.add(false);

        correctWrong = findViewById(R.id.AdvanceCorrectWrong);
        //lastRun = false;
        setRandomCars();
        setAnswers();

        // checks if the timer is on and starts it
        timerOn = getIntent().getBooleanExtra("timer", false);
        if (timerOn) startTimer();

        // when text was red or green, once start typing it makes it go black
        //*TextChangeListener - https://developer.android.com/reference/android/text/TextWatcher
        editText1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                editText1.setTextColor(BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                editText2.setTextColor(BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                editText3.setTextColor(BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }


    public void startTimer() {
        // if there are attempts left
        // if there is time left
        // if btn is not "next"
        //System.out.println("TimerTask counter is: " + timeLeft);
        // if btn is "next"
        // if no time left
        // if there are no attempts left
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                if (attempts < MAX_ATTEMPTS) { // if there are attempts left
                    if (timeLeft >= 0) {       // if there is time left
                        if (!(btn.getText().equals(getString(R.string.next)))) { // if btn is not "next"
                            //System.out.println("TimerTask counter is: " + timeLeft);
                            setCounterText(Integer.toString(timeLeft));
                            timeLeft--;

                        } else { // if btn is "next"
                            stopTimer();
                        }
                    } else { // if no time left

                        timeLeft = MAX_TIME_4_TIMER;
                        doClickBtn();
                        System.out.println("Attempts used = " + attempts);
                    }
                } else { // if there are no attempts left
                    doClickBtn();
                    setButtonText("Next");
                }
            }
        };

        timer = new Timer("TheTimer");
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void doClickBtn() {
        runOnUiThread(() -> btn.performClick());
    }

    public void stopTimer() {
        if (timerOn) {
            timeLeft = MAX_TIME_4_TIMER;
            timer.cancel();
            timer.purge();
            setCounterText("");
        }
    }

    public void setButtonText(final String text) {
        runOnUiThread(() -> btn.setText(text));
    }

    public void setCounterText(final String counterValue) {
        runOnUiThread(() -> {
            timerText.setVisibility(View.VISIBLE);
            timerText.setText(counterValue);
        });
    }


    public void setRandomCars() {


        car1.setImageResource(db.getRandomCars());
        carList.add(Database.getLastRandomIndex());
        car2.setImageResource(db.getRandomCars());
        carList.add(Database.getLastRandomIndex());
        car3.setImageResource(db.getRandomCars());
        carList.add(Database.getLastRandomIndex());
    }

    public void setAnswers() {


        answerString1 = db.getMakeName(carList.get(0));
        answerString2 = db.getMakeName(carList.get(1));
        answerString3 = db.getMakeName(carList.get(2));
        // empty the array so 0 1 2 values stays always correct when repeating
        carList.clear();

        //store answers in textviews
        answ1.setText(answerString1);
        answ2.setText(answerString2);
        answ3.setText(answerString3);
        System.out.println(answerString1);
        System.out.println(answerString2);
        System.out.println(answerString3);
        System.out.println("\n\n\n\n");
        System.out.println(answ1.getText().toString());
        System.out.println(answ2.getText().toString());
        System.out.println(answ3.getText().toString());
        //log

    }

    public void checkUserAnswers(EditText editText, String answerString) {

        // ex: if returns true false false--> first edittext is correct

        // checking every seperate edittext if its correct
        correctnessList.add(editText.getText().toString().equalsIgnoreCase(answerString));

        // test
        if (editText.getText().toString().equalsIgnoreCase(answerString)) {
            System.out.println("CHECKING ANSWERS " + answerString + " - TRUE, user said: " + editText.getText().toString());
        } else
            System.out.println("CHECKING ANSWERS " + answerString + " - FALSE, user said: " + editText.getText().toString());
    }


    public void onSubmit(View view) {

        if (btn.getText().toString().equals("Next")) {
            restart();
        } else {
            attempts++;
            if (!firstRun) {
                if (checkIfDone()) {
                    giveFeedback();
                }
            }
            firstRun = false;

            // checking user answers // check if guesses are correct
            checkUserAnswers(editText1, answerString1);
            checkUserAnswers(editText2, answerString2);
            checkUserAnswers(editText3, answerString3);

            // prevents a bug when after submit start typing in empty space and getting red
            //fixEmptyEditTexts(editText1);
            //fixEmptyEditTexts(editText2);
            //fixEmptyEditTexts(editText3);

            //reset timer back to ten;
            timeLeft = MAX_TIME_4_TIMER;
            //update timer immediately, dont wait up to 1sec delay
            //timerText.setText(Integer.toString(timeLeft)); <- still gives up to 1sec delay

            // change edit text colors according to answers
            changeEditTextColors();
            countScore();
            setEditTextsVisible();


            // if attempts are used, show feedback!
            if (attempts >= MAX_ATTEMPTS) {
                giveFeedback();
                if(timerOn) {
                    stopTimer();
                }
            }
        }
    }

    // if edittext is green, return true.
    public boolean checkIfDone() {
        boolean done = false;
        if ((editText1.getCurrentTextColor() == Color.rgb(0, 255, 0))
                && (editText2.getCurrentTextColor() == Color.rgb(0, 255, 0))
                && (editText3.getCurrentTextColor() == Color.rgb(0, 255, 0))) {
            done = true;
        }
        return done;
    }

    @SuppressLint("SetTextI18n")
    public void giveFeedback() {


        //if all green, give CORRECT, if no - not WRONG
        boolean correctOrWrongFeedback = true;
        int i = correctnessList.size();
        if (!correctnessList.get(i - 3)) correctOrWrongFeedback = false;
        else if (!correctnessList.get(i - 2)) correctOrWrongFeedback = false;
        else if (!correctnessList.get(i - 1)) correctOrWrongFeedback = false;

        // set it and make it visible
        if (correctOrWrongFeedback) {
            correctWrong.setText("CORRECT!");
            correctWrong.setTextColor(GREEN);
        } else {
            correctWrong.setText("WRONG!");
            correctWrong.setTextColor(RED);
        }
        correctWrong.setVisibility(View.VISIBLE);

        setAnswersVisible();

        // change button
        btn.setText("Next");
    }

    @SuppressLint("SetTextI18n")
    public void restart() { // resets all the values so, then calls start()
        btn.setText("Submit");
        attempts = 0;
        carList.clear();
        correctnessList.clear();
        correctWrong.setVisibility(INVISIBLE);

        setAnswersInvisible();
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        firstRun = true;
        //lastRun = true;
        scoreOpen1 = true;
        scoreOpen2 = true;
        scoreOpen3 = true;

        stopTimer();
        start();
        editText1.setEnabled(true); //* For Release the text
        editText2.setEnabled(true);
        editText3.setEnabled(true);
    }


    @SuppressLint("SetTextI18n")
    public void countScore() {
        //counting score
        if (scoreOpen1 && editText1.getCurrentTextColor() == GREEN) {
            scoreInt++;
            scoreOpen1 = false;
        }
        if (scoreOpen2 && editText2.getCurrentTextColor() == GREEN) {
            scoreInt++;
            scoreOpen2 = false;
        }
        if (scoreOpen3 && editText3.getCurrentTextColor() == GREEN) {
            scoreInt++;
            scoreOpen3 = false;
        }
        scoreTv.setText("" + scoreInt);


    }

    public void changeEditTextColors() {
        boolean allCorrect = true;

        int size = correctnessList.size();
        if (correctnessList.get(size - 3)) {
            editText1.setTextColor(Color.rgb(0, 255, 0));


        } else {
            allCorrect = false;

            editText1.setTextColor(Color.rgb(255, 0, 0));
        }
        if (correctnessList.get(size - 2)) {
            editText2.setTextColor(Color.rgb(0, 255, 0));

        } else {

            allCorrect = false;
            editText2.setTextColor(Color.rgb(255, 0, 0));
        }
        if (correctnessList.get(size - 1)) {
            editText3.setTextColor(Color.rgb(0, 255, 0));

        } else {

            allCorrect = false;
            editText3.setTextColor(Color.rgb(255, 0, 0));
        }
        if (allCorrect) {
            giveFeedback();
            editText1.setEnabled(false);//*Fix Edit
            editText2.setEnabled(false);
            editText3.setEnabled(false);
        }
    }

    public void setEditTextsVisible() {
        editText1.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        editText3.setVisibility(View.VISIBLE);

    }

    public void setAnswersVisible() {
        System.out.println("Setting answers visible");
        int size = correctnessList.size();
        if (!correctnessList.get(size - 3)) {
            answ1.setVisibility(View.VISIBLE);
        }
        if (!correctnessList.get(size - 2)) {
            answ2.setVisibility(View.VISIBLE);
        }
        if (!correctnessList.get(size - 1)) {
            answ3.setVisibility(View.VISIBLE);
        }
    }


    public void setAnswersInvisible() {
        answ1.setVisibility(INVISIBLE);
        answ2.setVisibility(INVISIBLE);
        answ3.setVisibility(INVISIBLE);

    }

    public void onEditTextClick1(View view) {
        //editText2.requestFocus();
    }

    public void onEditTextClick2(View view) {
        // editText3.requestFocus();
    }

    public void onEditTextClick3(View view) {
        //btn.requestFocus();
    }


}