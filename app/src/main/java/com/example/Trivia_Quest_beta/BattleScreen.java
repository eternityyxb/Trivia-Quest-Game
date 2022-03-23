package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class BattleScreen extends AppCompatActivity {
    Dialog pause;
    Dialog confirmation;
    Dialog exp;
    Dialog win;
    Dialog lose;
    BottomSheetDialog bag_screen;
    DatabaseHelper MyDB;
    TextView Question, Explanation, Correct, Answer, Timer, WinCurrency;
    Button Answer1, Answer2, Answer3, Answer4, True, False;
    ImageView P1, P2, P3, P4, P5, E1, E2, E3, E4, E5, E6;

    private int Item_used_value;
    private int PCurrency, PLevel, PExp;
    private int Currency = 1000, Exp = 10, Set_Bonus = 0;


    private static final long time = 60000;
    private long timeleft = time;
    private CountDownTimer timerCD;

    private int EHealth = 6, PHealth = 5, i;
    private Boolean isCorrect = true;
    private Boolean Ispressed = false;
    private String StageID;
    private String[] Questions, Data, UserData, Choices;
    private String Username;
    private int[] EquipData;
    private int PotionAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_battle_screen);


        pause = new Dialog(this);
        confirmation = new Dialog(this);
        exp = new Dialog(this);
        win = new Dialog(this);
        lose = new Dialog(this);
        bag_screen = new BottomSheetDialog(this);
        MyDB = new DatabaseHelper(this);


        Question = (TextView) findViewById(R.id.questionsText);
        Timer = (TextView) findViewById(R.id.timertxt);

        Answer1 = (Button) findViewById(R.id.Answer1btn);
        Answer2 = (Button) findViewById(R.id.Answer2btn);
        Answer3 = (Button) findViewById(R.id.Answer3btn);
        Answer4 = (Button) findViewById(R.id.Answer4btn);
        True = (Button) findViewById(R.id.Truebtn);
        False = (Button) findViewById(R.id.Falsebtn);

        P1 = (ImageView) findViewById(R.id.Phealth1);
        P2 = (ImageView) findViewById(R.id.Phealth2);
        P3 = (ImageView) findViewById(R.id.Phealth3);
        P4 = (ImageView) findViewById(R.id.Phealth4);
        P5 = (ImageView) findViewById(R.id.Phealth5);
        E1 = (ImageView) findViewById(R.id.Ehealth1);
        E2 = (ImageView) findViewById(R.id.Ehealth2);
        E3 = (ImageView) findViewById(R.id.Ehealth3);
        E4 = (ImageView) findViewById(R.id.Ehealth4);
        E5 = (ImageView) findViewById(R.id.Ehealth5);
        E6 = (ImageView) findViewById(R.id.Ehealth6);



        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!= null)
            StageID = (String) b.get("StageID");

        SharedPreferences sp = getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE); //global data
        Username = sp.getString("username", "");

        EquipData = MyDB.getEquipmentData(Username);
        Questions = MyDB.GetQuestions(StageID);
        List<String> QuestionList = Arrays.asList(Questions);
        Collections.shuffle(QuestionList);
        QuestionList.toArray(Questions);

        i = 0;


        getUserData();
        System.out.println(UserData[0]);
        System.out.println(UserData[1]);
        System.out.println(UserData[2]);
        SetQuestion();
    }

    public void ShowExplanation() {

        Button cont;
        exp.setContentView(R.layout.explaination);
        cont = (Button)exp.findViewById(R.id.btnContinue);
        Explanation = (TextView) exp.findViewById(R.id.Explanationtv);
        Answer = (TextView) exp.findViewById(R.id.Answertv);
        Correct = (TextView) exp.findViewById(R.id.Correcttv);

        if(isCorrect == true)
        {
            Correct.setText("CORRECT");
            MyDB.UpdateAnswer(Questions[i]);
        }
        else
            Correct.setText("INCORRECT");

        Answer.setText(Data[1]);
        Explanation.setText(Data[6]);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ispressed = false;
                i = i + 1;
                timeleft = time;
                CheckHealth();
                DefaultColor();
                exp.dismiss();
                SetQuestion();
                onResume();
            }
        });

        exp.show();
    }

    public void ShowBag(View v){
        ImageButton potion;
        TextView potion_amount_text;

        bag_screen.setContentView(R.layout.bag_in_battle);
        potion = (ImageButton)bag_screen.findViewById(R.id.btnPotion);
        potion_amount_text = (TextView)bag_screen.findViewById(R.id.txtPotion_in_battle);
        EquipData = MyDB.getEquipmentData(Username);
        PotionAmount= EquipData[3];
        potion_amount_text.setText("x " + PotionAmount);
        potion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(PHealth < 5 && PotionAmount>0) {
                    PotionAmount--;
                    potion_amount_text.setText("x " + PotionAmount);
                    //potion.setBackgroundResource(R.drawable.potion_used);
                    //potion.setEnabled(false);
                    MyDB.BoughtItems(PotionAmount, Username);
                    increasePhealth();
                    onResume();
                }
                else if(PHealth == 5){
                    Toast.makeText(BattleScreen.this, "Your Health is Full", Toast.LENGTH_LONG).show();
                    onResume();
                }
                else if(PotionAmount == 0){
                    Toast.makeText(BattleScreen.this, "You have no more Potion", Toast.LENGTH_LONG).show();
                    onResume();
                }
            }
        });

        bag_screen.show();
    }



    public void PauseMenu(View v){
        Button resume;
        Button restart;
        Button back_to_map;
        Button back_to_lobby;
        Button yes;
        Button no;

        pause.setContentView(R.layout.pause_menu);
        confirmation.setContentView(R.layout.confirmation_exit_battle);

        resume = (Button) pause.findViewById(R.id.btnResume);
        restart = (Button) pause.findViewById(R.id.btnRestart);
        back_to_map = (Button) pause.findViewById(R.id.btnBackToMap);
        back_to_lobby = (Button) pause.findViewById(R.id.btnBackToLobby);
        yes = (Button) confirmation.findViewById(R.id.btnYes);
        no = (Button) confirmation.findViewById(R.id.btnNo);

        timerCD.cancel();
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause.dismiss();
                onResume();
                starttimer();

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // confirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                confirmation.show();
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation.dismiss();
                        onResume();

                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), BattleScreen.class);
                        intent.putExtra("StageID", StageID);
                        startActivity(intent);
                    }
                });
            }
        });

        back_to_lobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // confirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                confirmation.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username", Username);
                        startActivity(intent);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation.dismiss();
                        onResume();

                    }
                });
            }
        });

        back_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                confirmation.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), WorldScreen.class);
                        startActivity(intent);
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation.dismiss();
                        onResume();

                    }
                });

            }
        });


        // pause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pause.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public void SetQuestion()
    {
        Data = MyDB.GetData(Questions[i]);
        Question.setText(Data[0]);
        String Type = Data[5];
        System.out.println(Type);

        Choices = new String[] {Data[1], Data[2], Data[3], Data[4]}; //randomized Answers using 3 imports Arrays collections and list//
        List<String> AnswerList = Arrays.asList(Choices);
        Collections.shuffle(AnswerList);
        AnswerList.toArray(Choices);

        Answer1.setText(Choices[0]);
        Answer2.setText(Choices[1]);
        Answer3.setText(Choices[2]);
        Answer4.setText(Choices[3]);

        if(Type.equals("TF"))
        {
            True.setVisibility(View.VISIBLE);
            False.setVisibility(View.VISIBLE);
            Answer1.setVisibility(View.INVISIBLE);
            Answer2.setVisibility(View.INVISIBLE);
            Answer3.setVisibility(View.INVISIBLE);
            Answer4.setVisibility(View.INVISIBLE);
        }else if (Type.equals("MCQ"))
        {
            True.setVisibility(View.INVISIBLE);
            False.setVisibility(View.INVISIBLE);
            Answer1.setVisibility(View.VISIBLE);
            Answer2.setVisibility(View.VISIBLE);
            Answer3.setVisibility(View.VISIBLE);
            Answer4.setVisibility(View.VISIBLE);
        }

        starttimer();
    }

    public void OnClickAnswer(View v) throws InterruptedException {
        String Answer;
        if(Ispressed == false)
        {
            switch (v.getId()) {
                case R.id.Answer1btn:
                    Ispressed = true;
                    Answer = Answer1.getText().toString();
                    CheckAnswer(Answer, 1);
                    Handler();

                    break;

                case R.id.Answer2btn:
                    Ispressed = true;
                    Answer = Answer2.getText().toString();
                    CheckAnswer(Answer, 2);
                    Handler();
                    break;

                case R.id.Answer3btn:
                    Ispressed = true;
                    Answer = Answer3.getText().toString();
                    CheckAnswer(Answer, 3);
                    Handler();
                    break;

                case R.id.Answer4btn:
                    Ispressed = true;
                    Answer = Answer4.getText().toString();
                    CheckAnswer(Answer, 4);
                    Handler();
                    break;

                case R.id.Truebtn:
                    Ispressed = true;
                    Answer = True.getText().toString();
                    CheckAnswer(Answer, 5);
                    Handler();
                    break;

                case R.id.Falsebtn:
                    Ispressed = true;
                    Answer = False.getText().toString();
                    CheckAnswer(Answer, 6);
                    Handler();
                    break;
            }
        }
    }


    public void CheckHealth() {
        if(EHealth == 0)
        {
            Button ctn;
            Button ReturnToLobby;

            win.setContentView(R.layout.you_win_popup);
            ctn = (Button) win.findViewById(R.id.btnContinue_Victory);
            ReturnToLobby = (Button) win.findViewById(R.id.btnBackToLobby_Victory);
            WinCurrency=win.findViewById(R.id.win_currency);

            win.show();
            CalculateData();
            WinCurrency.setText("+ $" + (Currency+Set_Bonus));
            MyDB.UpdateUser(Username, PLevel, PExp, PCurrency);

            ctn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    win.dismiss();
                    Intent intent = new Intent(getApplicationContext(), StageScreen.class);
                    startActivity(intent);

                }
            });

            ReturnToLobby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    win.dismiss();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", Username);
                    startActivity(intent);

                }
            });
            MyDB.UpdateStage(StageID);
        }
        else if(PHealth == 0)
        {
            Button retry;
            Button ReturnToLobby;
            lose.setContentView(R.layout.you_lose_pop_up);
            retry = (Button) lose.findViewById(R.id.btnRetry_Defeat);
            ReturnToLobby = (Button) lose.findViewById(R.id.btnBackToLobby_Defeat);
            lose.show();

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lose.dismiss();
                    Intent intent = new Intent(getApplicationContext(), BattleScreen.class);
                    intent.putExtra("StageID", StageID);
                    startActivity(intent);

                }
            });

            ReturnToLobby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lose.dismiss();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", Username);
                    startActivity(intent);

                }
            });

        }
    }

    public void CheckAnswer(String Answer, int btn) {
        if(btn == 1)
        {
            if(Answer1.getText().equals(Data[1])) {
                Answer1.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                Answer1.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }
        if(btn == 2)
        {
            if(Answer2.getText().equals(Data[1])) {
                Answer2.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                Answer2.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }
        if(btn == 3)
        {
            if(Answer3.getText().equals(Data[1])) {
                Answer3.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                Answer3.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }
        if(btn == 4)
        {
            if(Answer4.getText().equals(Data[1])) {
                Answer4.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                Answer4.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }
        if(btn == 5)
        {
            if(True.getText().equals(Data[1])) {
                True.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                True.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }
        if(btn == 6)
        {
            if(False.getText().equals(Data[1])) {
                False.setBackgroundColor(Color.GREEN);
                reduceEhealth();
                isCorrect = true;
            }
            else {
                False.setBackgroundColor(Color.RED);
                reducePhealth();
                isCorrect = false;
            }
        }

        if(isCorrect == false)
        {
            checkCorrectAnswer();
        }
    }

    public void DefaultColor(){
        Answer1.setBackgroundColor(0xFF6200EE);
        Answer2.setBackgroundColor(0xFF6200EE);
        Answer3.setBackgroundColor(0xFF6200EE);
        Answer4.setBackgroundColor(0xFF6200EE);
        True.setBackgroundColor(0xFF6200EE);
        False.setBackgroundColor(0xFF6200EE);
    }

    public void reducePhealth(){
        PHealth = PHealth - 1;
        if(PHealth == 4) P1.setImageResource(R.drawable.hp_black);
        if(PHealth == 3) P2.setImageResource(R.drawable.hp_black);
        if(PHealth == 2) P3.setImageResource(R.drawable.hp_black);
        if(PHealth == 1) P4.setImageResource(R.drawable.hp_black);
        if(PHealth == 0) P5.setImageResource(R.drawable.hp_black);
    }
    public void increasePhealth(){
        PHealth = PHealth + 1;
        if(PHealth == 5) P1.setImageResource(R.drawable.hp);
        if(PHealth == 4) P2.setImageResource(R.drawable.hp);
        if(PHealth == 3) P3.setImageResource(R.drawable.hp);
        if(PHealth == 2) P4.setImageResource(R.drawable.hp);
    }
    public void reduceEhealth(){
        EHealth = EHealth - 1;
        if(EHealth == 5) E1.setImageResource(R.drawable.hp_black);
        if(EHealth == 4) E2.setImageResource(R.drawable.hp_black);
        if(EHealth == 3) E3.setImageResource(R.drawable.hp_black);
        if(EHealth == 2) E4.setImageResource(R.drawable.hp_black);
        if(EHealth == 1) E5.setImageResource(R.drawable.hp_black);
        if(EHealth == 0) E6.setImageResource(R.drawable.hp_black);
    }

    public void checkCorrectAnswer(){
        if(Answer1.getText().equals(Data[1])) Answer1.setBackgroundColor(Color.GREEN);
        if(Answer2.getText().equals(Data[1])) Answer2.setBackgroundColor(Color.GREEN);
        if(Answer3.getText().equals(Data[1])) Answer3.setBackgroundColor(Color.GREEN);
        if(Answer4.getText().equals(Data[1])) Answer4.setBackgroundColor(Color.GREEN);
        if(True.getText().equals(Data[1])) True.setBackgroundColor(Color.GREEN);
        if(False.getText().equals(Data[1])) False.setBackgroundColor(Color.GREEN);
    }

    public void Handler(){
        Handler handler = new Handler();
        timerCD.cancel();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowExplanation();
            }
        }, 1000);
    }

    public void starttimer() {
        timerCD = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isCorrect = false;
                checkCorrectAnswer();
                reducePhealth();
                Handler();
            }
        }.start();
    }

    public void updateTimerText(){
        int minutes = (int) timeleft / 1000 / 60;
        int seconds = (int) timeleft / 1000 % 60;

        String timelefttext = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        Timer.setText(timelefttext);
    }

    public void getUserData(){
        UserData = MyDB.GetUserBattleData(Username);
        PLevel = Integer.parseInt(UserData[0]);
        PCurrency = Integer.parseInt(UserData[1]);
        PExp = Integer.parseInt(UserData[2]);
    }


    public void CalculateData(){
        if(EquipData[0]==1 && EquipData[1]==1 && EquipData[2]==1)
        {
            Set_Bonus=100;
        }
        PExp = PExp + Exp;
        PCurrency = PCurrency + Currency + Set_Bonus;
        if(PExp == 100)
        {
            PExp = 0;
            PLevel = PLevel + 1;
        }
    }


}