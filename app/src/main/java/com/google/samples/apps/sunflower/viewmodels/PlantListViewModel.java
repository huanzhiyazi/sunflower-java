package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PlantListViewModel extends ViewModel {
    private static final int NO_GROW_ZONE = -1;

    private PlantRepository plantRepository;
    private final MutableLiveData<Integer> growZoneNumber = new MutableLiveData<>(NO_GROW_ZONE);

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Plant>> plants;

    PlantListViewModel(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public LiveData<List<Plant>> plants() {
        return Transformations.switchMap(this.growZoneNumber, this::getPlants);
    }

    private LiveData<List<Plant>> getPlants(int growZone) {
        if (this.plants == null) {
            this.plants = new MutableLiveData<>();
        }

        disposables.add(
                (growZone == NO_GROW_ZONE ? plantRepository.getPlants() : plantRepository.getPlantsWithGrowZoneNumber(growZone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(plants -> this.plants.setValue(plants))
        );

        return this.plants;
    }

    public void setGrowZoneNumber(int num) {
        growZoneNumber.setValue(num);
    }

    public void clearGrowZoneNumber() {
        growZoneNumber.setValue(NO_GROW_ZONE);
    }

    public boolean isFiltered() {
        Integer value = growZoneNumber.getValue();
        return value != null && value != NO_GROW_ZONE;
    }
}
