package com.mahmoudhamdyae.foodplanner.view.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Meal;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Meal> items;
    private final Context context;

    private static final String TAG = "RecyclerView";

    public HomeAdapter(Context context, List<Meal> items) {
        super();
        this.items = items;
        this.context = context;
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
                .load(items.get(position).getStrCategoryThumb())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(holder.imageView);

        holder.titleView.setText(items.get(position).getStrCategory());
        holder.descriptionView.setText(items.get(position).getStrCategoryDescription());
        Log.i(TAG, "********* onBindViewHolder ***********");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView, descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.desc);
        }
    }
}