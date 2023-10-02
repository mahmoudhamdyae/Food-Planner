package com.mahmoudhamdyae.foodplanner.view.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Category;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Category> items;
    private final Context context;
    private final OnCategoryClickListener listener;

    public HomeAdapter(Context context, List<Category> items, OnCategoryClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Category> products) {
        this.items = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(holder.imageView);
        ViewCompat.setTransitionName(holder.imageView, "meal_image");

        holder.titleView.setText(items.get(position).getName());
        holder.descriptionView.setText(items.get(position).getDescription());

        holder.row.setOnClickListener(v -> listener.onCategoryClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
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
