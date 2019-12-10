package com.google.samples.apps.sunflower.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Single;

public final class PlantRepository {
    private final PlantDao plantDao;
    private static volatile PlantRepository instance;
    private PlantRepository(PlantDao plantDao) {
        this.plantDao = plantDao;
    }

    public static PlantRepository getInstance(PlantDao plantDao) {
        if (instance == null) {
            synchronized (PlantRepository.class) {
                if (instance == null) {
                    instance = new PlantRepository(plantDao);
                }
            }
        }
        return instance;
    }

    public Single<List<Plant>> getPlants() {
        return plantDao.getPlants();
    }

    public Single<Plant> getPlant(String plantId) {
        return plantDao.getPlant(plantId);
    }

    public Single<List<Plant>> getPlantsWithGrowZoneNumber(int growZoneNumber) {
        return plantDao.getPlantsWithGrowZoneNumber(growZoneNumber);
    }
}
