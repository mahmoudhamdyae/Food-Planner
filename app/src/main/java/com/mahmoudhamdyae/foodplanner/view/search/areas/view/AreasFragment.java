package com.mahmoudhamdyae.foodplanner.view.search.areas.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.AllAreas;
import com.mahmoudhamdyae.foodplanner.model.Area;
import com.mahmoudhamdyae.foodplanner.model.SearchType;

public class AreasFragment extends Fragment implements OnAreaClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_areas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set Action Bar Title
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.areas_screen_title);

        // Recycler View
        AreasAdapter mAdapter = new AreasAdapter(getContext(), AllAreas.getInstance().getAllAreas(), this);
        RecyclerView recyclerView = view.findViewById(R.id.areas_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAreaClicked(Area area) {
        navigateToMealsScreen(area);
    }

    private void navigateToMealsScreen(Area area) {
        NavDirections action = AreasFragmentDirections.actionAreasFragmentToMealsFragment(SearchType.AREA, area.getName());
        Navigation.findNavController(requireView()).navigate(action);
    }
}