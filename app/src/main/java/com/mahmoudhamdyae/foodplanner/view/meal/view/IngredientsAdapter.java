package com.mahmoudhamdyae.foodplanner.view.meal.view;

import android.content.Context;
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

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private final List<String> items;
    private final Context context;

    public IngredientsAdapter(@NonNull Context context, List<String> items) {
        super();
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredientNameTextView.setText(items.get(position));
        String url = "https://www.themealdb.com/images/ingredients/" + items.get(position).replace(" ", "%20") + "-Small.png";
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_broken_image))
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientNameTextView;
        ImageView ingredientImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
        }
    }
}
