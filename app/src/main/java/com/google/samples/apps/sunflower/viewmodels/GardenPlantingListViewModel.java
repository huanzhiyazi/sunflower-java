package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GardenPlantingListViewModel extends ViewModel {
    private GardenPlantingRepository gardenPlantingRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<PlantAndGardenPlantings>> plantAndGardenPlantings;

    GardenPlantingListViewModel(GardenPlantingRepository gardenPlantingRepository) {
        this.gardenPlantingRepository = gardenPlantingRepository;
    }

    public LiveData<List<PlantAndGardenPlantings>> plantAndGardenPlantings() {
        if (this.plantAndGardenPlantings == null) {
            this.plantAndGardenPlantings = new MutableLiveData<>();
        }

        disposables.add(
                this.gardenPlantingRepository.getPlantedGardens()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(plants -> this.plantAndGardenPlantings.setValue(plants))
        );

        return this.plantAndGardenPlantings;
    }
}
