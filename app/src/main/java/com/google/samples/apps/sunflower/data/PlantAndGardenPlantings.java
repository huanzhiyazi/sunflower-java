package com.google.samples.apps.sunflower.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlantAndGardenPlantings {
    @Embedded
    public final Plant plant;

    @Relation(parentColumn = "id", entityColumn = "plant_id")
    public final List<GardenPlanting> gardenPlantings;

    public PlantAndGardenPlantings(Plant plant, List<GardenPlanting> gardenPlantings) {
        this.plant = plant;
        this.gardenPlantings = gardenPlantings;
    }
}
