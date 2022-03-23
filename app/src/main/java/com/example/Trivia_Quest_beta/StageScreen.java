package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class StageScreen extends AppCompatActivity {
    ImageButton BtnBack, Stage0btn, Stage1btn, Stage2btn, Stage3btn;
    Dialog confirmation_enter;
    Dialog tut;
    DatabaseHelper MyDB;

    private String S0, S1, S2, S3;
    private int Stage;
    private String AreaID = "C";
    private String[] Data;
    private String[][] chkStage;
    private String StageID;



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
        setContentView(R.layout.activity_stage_screen);



        BtnBack = (ImageButton) findViewById(R.id.Back_Button_Stage);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AreaScreen.class);
                startActivity(intent);
            }
        });

        confirmation_enter = new Dialog(this);
        tut = new Dialog(this);
        MyDB = new DatabaseHelper(this);


        Data = MyDB.GetTutorial(AreaID);

        Stage0btn = findViewById(R.id.Stage0btn);
        Stage1btn = findViewById(R.id.Stage1btn);
        Stage2btn = findViewById(R.id.Stage2btn);
        Stage3btn = findViewById(R.id.Stage3btn);

        getStageData();
        CheckStage();
    }

    public void Confirm_Enter(View v) {
        switch (v.getId()) {
            case R.id.Stage0btn:
                Stage = 0;
                OpenTutorial(Data, Stage);
                break;

            case R.id.Stage1btn:
                Stage = 1;
                OpenBattle(Stage);
                break;

            case R.id.Stage2btn:
                Stage = 2;
                OpenBattle(Stage);
                break;

            case R.id.Stage3btn:
                Stage = 3;
                OpenBattle(Stage);
                break;

        }
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

    public void OpenBattle(int Stage) {
        ImageButton Close;
        Button Enter;
        TextView Confirm;
        confirmation_enter.setContentView(R.layout.confirmation_enter_stage);
        Close = (ImageButton) confirmation_enter.findViewById(R.id.btnexit);
        Enter = (Button) confirmation_enter.findViewById(R.id.btnEnter);
        Confirm = (TextView) confirmation_enter.findViewById(R.id.stage_text);


        if(Stage == 1) {Confirm.setText("World 1 - Cave 1"); StageID = "SC1";}
        if(Stage == 2) {Confirm.setText("World 1 - Cave 2"); StageID = "SC2";}
        if(Stage == 3) {Confirm.setText("World 1 - Cave 3"); StageID = "SC3";}


        //confirmation_enter.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmation_enter.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmation_enter.dismiss();
                onResume();

            }
        });

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BattleScreen.class);
                intent.putExtra("StageID", StageID);
                startActivity(intent);

            }
        });
    }

    public void OpenTutorial(String[] Data, int Stage) {
        Button ctn_tut, page1, page2, page3, page4;
        TextView Title, Description;

        tut.setContentView(R.layout.tutorial);
        page1 = (Button) tut.findViewById(R.id.btn1);
        page2 = (Button) tut.findViewById(R.id.btn2);
        page3 = (Button) tut.findViewById(R.id.btn3);
        page4 = (Button) tut.findViewById(R.id.btn4);
        ctn_tut = (Button)tut.findViewById(R.id.btnContinue2);
        Title = (TextView) tut.findViewById(R.id.Titletv);
        Description = (TextView) tut.findViewById(R.id.Descriptiontv);

        Title.setText(Data[0]);
        Description.setText(Data[1]);

        Description.setMovementMethod(new ScrollingMovementMethod());

        tut.show();
        ctn_tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tut.dismiss();
                onResume();
                String ID = "SC0";
                MyDB.UpdateStage(ID);
                getStageData();
                CheckStage();
            }
        });

        page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title.setText(Data[0]);
                Description.setText(Data[1]);
            }
        });

        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title.setText(Data[2]);
                Description.setText(Data[3]);
            }
        });

        page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title.setText(Data[4]);
                Description.setText(Data[5]);
            }
        });

        page4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title.setText(Data[6]);
                Description.setText(Data[7]);
            }
        });
    }

    public void getStageData(){
        chkStage = MyDB.GetStages(AreaID);
        S0 = chkStage[0][1];
        S1 = chkStage[1][1];
        S2 = chkStage[2][1];
        S3 = chkStage[3][1];

    }

    public void CheckStage(){
        if(S0.equals("YES")) {Stage1btn.setVisibility(View.VISIBLE);}
        if(S1.equals("YES")) {Stage2btn.setVisibility(View.VISIBLE);}
        if(S2.equals("YES")) {Stage3btn.setVisibility(View.VISIBLE);}
    }
}
