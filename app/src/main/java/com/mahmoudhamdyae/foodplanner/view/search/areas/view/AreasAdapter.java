package com.mahmoudhamdyae.foodplanner.view.search.areas.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.Area;

import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.ViewHolder> {

    private List<Area> items;
    private final Context context;
    private final OnAreaClickListener listener;

    public AreasAdapter(Context context, List<Area> items, OnAreaClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Area> areas) {
        this.items = areas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.name.setText(items.get(position).getName());
        holder.row.setOnClickListener(v -> listener.onAreaClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        CardView row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
        }
    }
}
