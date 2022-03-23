package com.example.Trivia_Quest_beta;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BagFragmentEquipment extends Fragment {
    Dialog equip_popup;
    Dialog unequip_popup;

    DatabaseHelper MyDB;

    private String Username;
    private int[] EquipData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_bag_equipment, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE);
        Username = sp.getString("username", "");

        MyDB = new DatabaseHelper(this.getActivity());
        EquipData = MyDB.getEquipmentData(Username);

        equip_popup = new Dialog(view.getContext());
        unequip_popup = new Dialog(view.getContext());
        equip_popup.setContentView(R.layout.confirmation_equip);
        unequip_popup.setContentView(R.layout.confirmation_unequip);

        Button equipment_button = (Button)view.findViewById(R.id.btnItems_bag);
        ImageButton demo_sword = (ImageButton)view.findViewById(R.id.sword);
        ImageButton demo_armor = (ImageButton)view.findViewById(R.id.armor);
        ImageButton demo_helmet =(ImageButton)view.findViewById(R.id.helmet);
        ImageButton equiped_sword = (ImageButton)view.findViewById(R.id.btnSword_Equiped);
        ImageButton equiped_armor = (ImageButton)view.findViewById(R.id.btnArmor_Equiped);
        ImageButton equiped_helmet =(ImageButton)view.findViewById(R.id.btnHelmet_Equiped);
        ImageButton necklace = (ImageButton)view.findViewById(R.id.demo_necklace);
        Button equip = (Button)equip_popup.findViewById(R.id.btnEquip);
        Button unequip = (Button)unequip_popup.findViewById(R.id.btnUnequip);
        ImageButton equip_exit = (ImageButton)equip_popup.findViewById(R.id.btnExit_Equip);
        ImageButton unequip_exit = (ImageButton)unequip_popup.findViewById(R.id.btnExit_Unequip);

        EquipmentUpdate(equiped_sword, demo_sword, equiped_armor, demo_armor, equiped_helmet, demo_helmet);
        if(EquipData[4]==1)
        {
            necklace.setVisibility(View.VISIBLE);
        }


        equipment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.FragmentContainer,new BagFragmentItem());
                fr.commit();
            }
        });

        demo_sword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equip_popup.show();
                equip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        equiped_sword.setVisibility(View.VISIBLE);
                        demo_sword.setBackgroundResource(R.drawable.sword_dark);
                        demo_sword.setEnabled(false);
                        //Sword_Equipped_value=1;//
                        MyDB.EquipEquipment("sword_equipped", Username);
                        Toast.makeText(view.getContext(), "Equipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                equip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });

        demo_armor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equip_popup.show();
                equip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        equiped_armor.setVisibility(View.VISIBLE);
                        demo_armor.setBackgroundResource(R.drawable.armor_dark);
                        demo_armor.setEnabled(false);
                        //Armor_Equipped_value=1;//
                        //String Equip = "armor_equipped";
                        MyDB.EquipEquipment("armor_equipped", Username);
                        Toast.makeText(view.getContext(), "Equipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                equip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });

        demo_helmet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equip_popup.show();
                equip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        equiped_helmet.setVisibility(View.VISIBLE);
                        demo_helmet.setBackgroundResource(R.drawable.helmet_dark);
                        demo_helmet.setEnabled(false);
                        //Helmet_Equipped_value=1;//
                        MyDB.EquipEquipment("helmet_equipped", Username);
                        Toast.makeText(view.getContext(), "Equipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                equip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });

        equiped_sword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unequip_popup.show();
                unequip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        equiped_sword.setVisibility(View.INVISIBLE);
                        demo_sword.setBackgroundResource(R.drawable.sword);
                        demo_sword.setEnabled(true);
                        //Sword_Equipped_value=0;//
                        MyDB.UnequipEquipment("sword_equipped", Username);
                        Toast.makeText(view.getContext(), "Unequipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                unequip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });

        equiped_armor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unequip_popup.show();
                unequip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        equiped_armor.setVisibility(View.INVISIBLE);
                        demo_armor.setBackgroundResource(R.drawable.armor);
                        demo_armor.setEnabled(true);
                        //Armor_Equipped_value=0;//
                        MyDB.UnequipEquipment("armor_equipped", Username);
                        Toast.makeText(view.getContext(), "Unequipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                unequip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });

        equiped_helmet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unequip_popup.show();
                unequip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        equiped_helmet.setVisibility(View.INVISIBLE);
                        demo_helmet.setBackgroundResource(R.drawable.helmet);
                        demo_helmet.setEnabled(true);
                        //Helmet_Equipped_value=0;//
                        MyDB.UnequipEquipment("helmet_equipped", Username);
                        Toast.makeText(view.getContext(), "Unequipped", Toast.LENGTH_LONG).show();
                        onResume();
                    }
                });

                unequip_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unequip_popup.dismiss();
                        onResume();
                    }
                });
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void EquipmentUpdate(ImageButton equiped_sword, ImageButton demo_sword, ImageButton equiped_armor, ImageButton demo_armor, ImageButton equiped_helmet, ImageButton demo_helmet) {
        // obtain value from database//
        if(EquipData[0] ==1) //Equipped//
       {
            equiped_sword.setVisibility(View.VISIBLE);
            demo_sword.setBackgroundResource(R.drawable.sword_dark);
            demo_sword.setEnabled(false);
       }

        if(EquipData[1]==1)
       {
           equiped_helmet.setVisibility(View.VISIBLE);
           demo_helmet.setBackgroundResource(R.drawable.helmet_dark);
           demo_helmet.setEnabled(false);
        }

       if(EquipData[2]==1)
        {
            equiped_armor.setVisibility(View.VISIBLE);
            demo_armor.setBackgroundResource(R.drawable.armor_dark);
            demo_armor.setEnabled(false);
        }
    }



}
