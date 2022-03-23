package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormatSymbols;

public class CavernLibraryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView LvCavern;
    String[] cavernallcontents;
    String[][] Data;
    String[] Questions, Answer, isAnswered;
    private Toast toastMessage;

    DatabaseHelper MyDB;

    private String area = "Cavern";

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
        setContentView(R.layout.activity_cavern_library);

        MyDB = new DatabaseHelper(this);
        Data = new String[30][3];
        Questions = new String[30];
        Answer = new String[30];
        isAnswered = new String[30];

        Data = MyDB.GetAllQuestionData(area);

        for(int i = 0; i < 30; i++) {
            Questions[i] = Data[i][0];
            System.out.println(Questions[i]);
            Answer[i] = Data[i][1];
            System.out.println(Answer[i]);
            isAnswered[i] = Data[i][2];
            System.out.println(isAnswered[i]);
        }



        ImageButton cavernlibraryback = findViewById(R.id.btn_back_cavern_library);
        LvCavern = findViewById(R.id.List_view_cavern);

        cavernallcontents = Questions;

        ArrayAdapter<String> QuestionAdapter = new ArrayAdapter<>(this, R.layout.listcaverncolor, cavernallcontents);
        LvCavern.setAdapter(QuestionAdapter);
        LvCavern.setOnItemClickListener(this);



        cavernlibraryback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

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

    //onclick on the list and check on the item position (row)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Questions[position].equals("???"))
        {
            ToastManager();
            toastMessage = Toast.makeText(getApplicationContext(), "???",Toast.LENGTH_SHORT);
            toastMessage.show();
        }
        else {
            ToastManager();
            toastMessage = Toast.makeText(getApplicationContext(), "Answer:"+Answer[position],Toast.LENGTH_SHORT);
            toastMessage.show();
        }
    }

    public void ToastManager(){
        if (toastMessage != null) {
            toastMessage.cancel();
        }
    }

}