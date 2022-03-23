package com.example.Trivia_Quest_beta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ShopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_shop, container, false);
       Button equipment_button = (Button)view.findViewById(R.id.btnEquipment);
        equipment_button.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.FragmentContainer,new ShopEquipmentFragment());
                fr.commit();
            }
        });

        Button item_button = (Button)view.findViewById(R.id.btnItems);
        item_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.FragmentContainer,new ShopItemsFragment());
                fr.commit();
            }
        });

        return view;
    }


}