package com.example.Trivia_Quest_beta;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ShopEquipmentFragment extends Fragment {

    private String Username;
    private int[] EquipData;
    private String[] UserData;
    private int PCurrency, PLevel, PExp;

    DatabaseHelper MyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_equipment, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE);
        Username = sp.getString("username", "");


        MyDB = new DatabaseHelper(this.getActivity());
        EquipData = MyDB.getEquipmentData(Username);
        UserData = MyDB.GetUserData(Username);
        PLevel = Integer.parseInt(UserData[1]);
        PCurrency = Integer.parseInt(UserData[2]);
        PExp = Integer.parseInt(UserData[3]);




        ImageButton equipment_button = (ImageButton)view.findViewById(R.id.btn_back_equipment_shop);
        Button BuyEquipment = (Button)view.findViewById(R.id.btnBuy_equipments);
        ImageView sold = (ImageView)view.findViewById(R.id.sold);


        if(EquipData[4]==1){
            sold.setVisibility(View.VISIBLE);
            BuyEquipment.setEnabled(false);
        }

        equipment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.FragmentContainer,new ShopFragment());
                fr.commit();
            }
        });

        BuyEquipment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(PCurrency>=6000) {
                    sold.setVisibility(View.VISIBLE);
                    BuyEquipment.setEnabled(false);
                    PCurrency = PCurrency - 6000;
                    MyDB.BoughtEquipment(Username);
                    MyDB.UpdateUser(Username, PLevel, PExp, PCurrency);
                    Toast.makeText(view.getContext(), "Successfully Purchased", Toast.LENGTH_LONG).show();
               }
                else if(PCurrency < 6000){
                    Toast.makeText(view.getContext(), "You don't have enough money", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
