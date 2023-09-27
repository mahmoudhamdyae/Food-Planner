package com.mahmoudhamdyae.foodplanner.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.mahmoudhamdyae.foodplanner.R;
import com.mahmoudhamdyae.foodplanner.model.MealsResponse;
import com.mahmoudhamdyae.network.ApiClient;
import com.mahmoudhamdyae.network.NetworkCallback;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements NetworkCallback {

    private final String TAG = "HomeFragment";
    private FirebaseAuth mAuth;

    private HomeAdapter mAdapter;
    private RecyclerView recyclerView;

    private ApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAdapter = new HomeAdapter(getContext(), new ArrayList<>());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        client = ApiClient.getInstance();
        client.makeNetworkCall(this);
    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onSuccessResult(MealsResponse mealsResponse) {
        mAdapter.setList(mealsResponse.getCategories());
    }

    @Override
    public void onFailureResult(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}