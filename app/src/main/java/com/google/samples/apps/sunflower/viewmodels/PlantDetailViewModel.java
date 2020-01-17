package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PlantDetailViewModel extends ViewModel {
    private PlantRepository plantRepository;
    private final GardenPlantingRepository gardenPlantingRepository;
    private final String plantId;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<Boolean> isPlanted;
    private MutableLiveData<Plant> plant;

    PlantDetailViewModel(PlantRepository plantRepository, GardenPlantingRepository gardenPlantingRepository, String plantId) {
        this.plantRepository = plantRepository;
        this.gardenPlantingRepository = gardenPlantingRepository;
        this.plantId = plantId;
    }

    public LiveData<Boolean> isPlanted() {
        if (this.isPlanted == null) {
            this.isPlanted = new MutableLiveData<>();
        }

        disposables.add(
                this.gardenPlantingRepository
                        .isPlanted(this.plantId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(planted -> this.isPlanted.setValue(planted))
        );

        return this.isPlanted;
    }

    public Boolean getIsPlanted() {
        return isPlanted != null && isPlanted.getValue() != null ? isPlanted.getValue() : false;
    }

    public LiveData<Plant> plant() {
        if (this.plant == null) {
            this.plant = new MutableLiveData<>();
        }

        disposables.add(
                this.plantRepository.getPlant(this.plantId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(p -> this.plant.setValue(p))
        );

        return this.plant;
    }

    public Plant getPlant() {
        return plant != null ? plant.getValue() : new Plant("", "", "", 0, 0, "");
    }

    public void addPlantToGarden() {
        this.gardenPlantingRepository.createGardenPlanting(this.plantId)
                .subscribeOn(Schedulers.io()).subscribe();
    }
}
