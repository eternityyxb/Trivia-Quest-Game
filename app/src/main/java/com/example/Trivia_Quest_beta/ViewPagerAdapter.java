package com.example.Trivia_Quest_beta;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

class ViewPagerAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    int[] images;

    // Layout Inflater
    LayoutInflater mLayoutInflater;

    private Toast toastMessage;
    // Viewpager Constructor
    public ViewPagerAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the bannerslide.xml
        View itemView = mLayoutInflater.inflate(R.layout.bannerslide, container, false);

        // referencing the image view from the bannerslide.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);

        // setting the image in the imageView
        imageView.setImageResource(images[position]);

        // Adding the View
        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position==0){
                    ToastManager();
                    Intent intent = new Intent(context, WorldScreen.class);
                    context.startActivity(intent);
                }else
                if (position==1){
                    ToastManager();
                    toastMessage = Toast.makeText(context, "New Mode Coming Soon", Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
            }
        });


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    public void ToastManager(){
        if (toastMessage != null) {
            toastMessage.cancel();
        }
    }
}