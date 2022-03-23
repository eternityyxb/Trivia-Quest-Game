package com.example.Trivia_Quest_beta;

import android.app.Activity;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BagFragmentItem extends Fragment {
    DatabaseHelper MyDB;
    private String Username;
    private int[] EquipData;
    TextView PotionAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag_item, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE);
        Username = sp.getString("username", "");

        MyDB = new DatabaseHelper(this.getActivity());
        EquipData = MyDB.getEquipmentData(Username);
        PotionAmount=view.findViewById(R.id.txtPotionAmount);
        PotionAmount.setText("x " + EquipData[3]);

        Button equipment_button = (Button)view.findViewById(R.id.btnEquipment_Bag);
        equipment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.FragmentContainer,new BagFragmentEquipment());
                fr.commit();
            }
        });
        return view;
    }
}
