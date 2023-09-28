package com.mahmoudhamdyae.foodplanner.view.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Meal> items;
    private final Context context;
    private final OnMealClickListener listener;

    private static final String TAG = "RecyclerView";

    public SearchAdapter(Context context, List<Meal> items, OnMealClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Meal> products) {
        this.items = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "========= onCreateViewHolder ===========");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).getStrMealThumb())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(holder.imageView);

        holder.titleView.setText(items.get(position).getStrMeal());
        holder.descriptionView.setText(items.get(position).getStrInstructions());

        holder.row.setOnClickListener(v -> listener.onMealClicked(items.get(position)));
        Log.i(TAG, "********* onBindViewHolder ***********");
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView, descriptionView;
        CardView row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.desc);
            row = itemView.findViewById(R.id.row);
        }
    }
}
