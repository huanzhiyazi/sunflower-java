package com.google.samples.apps.sunflower.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface GardenPlantingDao {
    @Query("SELECT * FROM garden_plantings")
    LiveData<List<GardenPlanting>> getGardenPlantings();

    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    Single<Boolean> isPlanted(String plantId);

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    Single<List<PlantAndGardenPlantings>> getPlantedGardens();

    @Insert
    Single<Long> insertGardenPlanting(GardenPlanting gardenPlanting);

    @Delete
    Single<Void> deleteGardenPlanting(GardenPlanting gardenPlanting);
}
