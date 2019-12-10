package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.GardenPlanting;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlantAndGardenPlantingsViewModel extends ViewModel {
    private static DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
    private PlantAndGardenPlantings plantings;
    public PlantAndGardenPlantingsViewModel(PlantAndGardenPlantings plantings) {
        this.plantings = plantings;
    }

    private Plant plant() {
        return this.plantings.plant;
    }

    private GardenPlanting gardenPlanting() {
        return this.plantings.gardenPlantings.get(0);
    }

    public String waterDateString() {
        return dateFormat.format(gardenPlanting().lastWateringDate.getTime());
    }

    public int wateringInterval() {
        return plant().wateringInterval;
    }

    public String imageUrl() {
        return plant().imageUrl;
    }

    public String plantName() {
        return plant().name;
    }

    public String plantDateString() {
        return dateFormat.format(gardenPlanting().plantDate.getTime());
    }

    public String plantId() {
        return plant().plantId;
    }
}
