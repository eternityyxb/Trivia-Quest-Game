package com.example.Trivia_Quest_beta;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {
    Dialog confirmation_logout;
    private Toast toastMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_more, container, false);
        Button yes;
        Button no;
        confirmation_logout = new Dialog(view.getContext());
        confirmation_logout.setContentView(R.layout.confirmation_logout);
        yes = (Button) confirmation_logout.findViewById(R.id.btnYes_logout);
        no = (Button) confirmation_logout.findViewById(R.id.btnNo_logout);




        ImageButton logoutBtn = (ImageButton)view.findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim1 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim1.setInterpolator(interpolator);
                logoutBtn.startAnimation(myAnim1);

                confirmation_logout.show();
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmation_logout.dismiss();
                        onResume();
                   }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent intent = new Intent(getActivity(), LoginActivity.class);
                         startActivity(intent);

                   }
               });

            }
        });

        ImageButton friendsBtn = (ImageButton)view.findViewById(R.id.btnFriends);
        friendsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim2 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim2.setInterpolator(interpolator);
                friendsBtn.startAnimation(myAnim2);
                ToastManager();
                toastMessage = Toast.makeText(view.getContext(), "This feature currently not available", Toast.LENGTH_SHORT);
                toastMessage.show();
            }
        });

        ImageButton trophyBtn = (ImageButton)view.findViewById(R.id.btnTrophy);
        trophyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim3 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim3.setInterpolator(interpolator);
                trophyBtn.startAnimation(myAnim3);
                ToastManager();
                toastMessage = Toast.makeText(view.getContext(), "This feature currently not available", Toast.LENGTH_SHORT);
                toastMessage.show();
            }
        });

        ImageButton libraryBtn = (ImageButton)view.findViewById(R.id.btnLibrary);
        libraryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim4 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim4.setInterpolator(interpolator);
                libraryBtn.startAnimation(myAnim4);
                Intent intent = new Intent(getActivity(), LibraryActivity.class);
                startActivity(intent);
            }
        });

        ImageButton settingsBtn = (ImageButton)view.findViewById(R.id.btnSettings);
        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim5 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim5.setInterpolator(interpolator);
                ToastManager();
                toastMessage = Toast.makeText(view.getContext(), "This feature currently not available", Toast.LENGTH_SHORT);
                toastMessage.show();
                settingsBtn.startAnimation(myAnim5);
            }
        });

        ImageButton profileBtn = (ImageButton)view.findViewById(R.id.btnProfile);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim6 = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim6.setInterpolator(interpolator);
                //profileBtn.startAnimation(myAnim);
                ToastManager();
                toastMessage = Toast.makeText(view.getContext(), "This feature currently not available", Toast.LENGTH_SHORT);
                toastMessage.show();
                profileBtn.startAnimation(myAnim6);
            }
        });


        return view;
    }

    public void ToastManager(){
        if (toastMessage != null) {
            toastMessage.cancel();
        }
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



}