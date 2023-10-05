package com.mahmoudhamdyae.foodplanner.view.welcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.mahmoudhamdyae.foodplanner.R;

public class WelcomeViewPageAdapter extends RecyclerView.Adapter<WelcomeViewPageAdapter.PagerVH2> {

    private final Context context;

    public WelcomeViewPageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PagerVH2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_item, parent, false);
        return new PagerVH2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerVH2 holder, int position) {
        switch (position) {
            case 0:
                holder.introTitle.setText(context.getString(R.string.on_boarding_title_1));
                holder.introDescription.setText(context.getString(R.string.on_boarding_text_1));
                holder.introImage.setAnimation("w1.json");
                break;
            case 1:
                holder.introTitle.setText(context.getString(R.string.on_boarding_title_2));
                holder.introDescription.setText(context.getString(R.string.on_boarding_text_2));
                holder.introImage.setAnimation("w2.json");
                break;
            case 2:
                holder.introTitle.setText(context.getString(R.string.on_boarding_title_3));
                holder.introDescription.setText(context.getString(R.string.on_boarding_text_3));
                holder.introImage.setAnimation("w3.json");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class PagerVH2 extends RecyclerView.ViewHolder {

        private final TextView introTitle;
        private final TextView introDescription;
        private final LottieAnimationView introImage;

        public PagerVH2(@NonNull View view) {
            super(view);
            introTitle = view.findViewById(R.id.intro_title);
            introDescription = view.findViewById(R.id.intro_description);
            introImage = view.findViewById(R.id.intro_image);
        }
    }
}