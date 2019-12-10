package com.google.samples.apps.sunflower.data;

import java.util.List;

import io.reactivex.Single;

public final class GardenPlantingRepository {
    private final GardenPlantingDao gardenPlantingDao;
    private static volatile GardenPlantingRepository instance;
    private GardenPlantingRepository(GardenPlantingDao gardenPlantingDao) {
        this.gardenPlantingDao = gardenPlantingDao;
    }

    public static GardenPlantingRepository getInstance(GardenPlantingDao gardenPlantingDao) {
        if (instance == null) {
            synchronized (GardenPlantingRepository.class) {
                if (instance == null) {
                    instance = new GardenPlantingRepository(gardenPlantingDao);
                }
            }
        }
        return instance;
    }

    public Single<Long> createGardenPlanting(String plantId) {
        final GardenPlanting gardenPlanting = GardenPlanting.of(plantId);
        return gardenPlantingDao.insertGardenPlanting(gardenPlanting);
    }

    public Single<Void> removeGardenPlanting(GardenPlanting gardenPlanting) {
        return gardenPlantingDao.deleteGardenPlanting(gardenPlanting);
    }

    public Single<Boolean> isPlanted(String plantId) {
        return gardenPlantingDao.isPlanted(plantId);
    }

    public Single<List<PlantAndGardenPlantings>> getPlantedGardens() {
        return gardenPlantingDao.getPlantedGardens();
    }
}
