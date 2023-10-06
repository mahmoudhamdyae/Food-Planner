package com.mahmoudhamdyae.foodplanner.view.search.areas.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AreasFragment extends Fragment implements OnAreaClickListener {

    private EditText searchEditText;

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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.areas_screen_title);

        List<Area> savedAreas = AllAreas.getInstance().getAllAreas();

        // Recycler View
        AreasAdapter mAdapter = new AreasAdapter(getContext(), savedAreas, this);
        RecyclerView recyclerView = view.findViewById(R.id.areas_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Search EditText
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    mAdapter.setList(savedAreas);
                } else {
                    mAdapter.setList(savedAreas.stream().filter(area -> area.getName().toLowerCase().contains(s.toString().toLowerCase())).collect(Collectors.toList()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void onAreaClicked(Area area) {
        navigateToMealsScreen(area);
    }

    private void navigateToMealsScreen(Area area) {
        NavDirections action = AreasFragmentDirections.actionAreasFragmentToMealsFragment(SearchType.AREA, area.getName(), null);
        Navigation.findNavController(requireView()).navigate(action);
        searchEditText.setText("");
    }
}