package com.mahmoudhamdyae.foodplanner.model;

import com.mahmoudhamdyae.network.NetworkCallback;
import com.mahmoudhamdyae.network.RemoteSource;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl repo = null;

    private final RemoteSource remoteSource;

    private RepositoryImpl(RemoteSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    public static RepositoryImpl getInstance(RemoteSource remoteSource) {
        if (repo == null) {
            repo = new RepositoryImpl(remoteSource);
        }
        return repo;
    }

    @Override
    public void getMeals(NetworkCallback networkCallback) {
        remoteSource.makeNetworkCall(networkCallback);
    }
}
