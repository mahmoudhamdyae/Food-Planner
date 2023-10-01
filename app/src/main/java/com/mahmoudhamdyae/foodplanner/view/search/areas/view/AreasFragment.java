package com.mahmoudhamdyae.foodplanner.view.search.areas.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.db.LocalDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.model.Area;
import com.mahmoudhamdyae.foodplanner.model.AreaResponse;
import com.mahmoudhamdyae.foodplanner.model.RepositoryImpl;
import com.mahmoudhamdyae.foodplanner.model.SearchType;
import com.mahmoudhamdyae.foodplanner.network.RemoteDataSourceImpl;
import com.mahmoudhamdyae.foodplanner.view.search.areas.presenter.AreaPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.areas.presenter.IAreaPresenter;
import com.mahmoudhamdyae.foodplanner.view.search.categories.view.CategoriesAdapter;

import java.util.ArrayList;

public class AreasFragment extends Fragment implements IAreaView, OnAreaClickListener {

    private AreasAdapter mAdapter;

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

        // Recycler View
        mAdapter = new AreasAdapter(getContext(), new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.areas_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Presenter
        IAreaPresenter presenter = new AreaPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getAreas();
    }

    @Override
    public void onGetAreasSuccess(AreaResponse areaResponse) {
        mAdapter.setList(areaResponse.getAreas());
    }

    @Override
    public void onGetAreasFail(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
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