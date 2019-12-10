package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.samples.apps.sunflower.data.PlantRepository;

public class PlantListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final PlantRepository repository;

    public PlantListViewModelFactory(PlantRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PlantListViewModel(this.repository);
    }
}
