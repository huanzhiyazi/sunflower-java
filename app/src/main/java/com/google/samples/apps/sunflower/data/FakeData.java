package com.google.samples.apps.sunflower.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.google.samples.apps.sunflower.BR;

import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel;

public class FakeData extends BaseObservable {
    private PlantDetailViewModel viewModel;

    private String name;

    public void setViewModel(PlantDetailViewModel viewModel) {
        this.viewModel = viewModel;
        notifyPropertyChanged(BR.plant);
        notifyPropertyChanged(BR.isPlanted);
    }

    @Bindable
    public Plant getPlant() {
        return viewModel != null ? viewModel.getPlant() : null;
    }

    @Bindable
    public Boolean getIsPlanted() {
        return viewModel != null ? viewModel.getIsPlanted() : false;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getName() {
        return name;
    }
}
