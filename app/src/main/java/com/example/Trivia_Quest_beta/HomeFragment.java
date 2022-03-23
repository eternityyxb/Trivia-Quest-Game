package com.example.Trivia_Quest_beta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;

import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.banner1, R.drawable.banner2};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    int currentPage = 0;
    int MAXPAGE=2;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 7000; // time in milliseconds between successive task executions.

    Button boardclick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rooView=inflater.inflate(R.layout.fragment_home, container, false);
        boardclick = rooView.findViewById(R.id.buttontocombat);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager) rooView.findViewById(R.id.viewPagerMain);
        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(rooView.getContext(), images);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == MAXPAGE) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        boardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WorldScreen.class);
                startActivity(intent);
            }
        });

        return rooView;
    }
}