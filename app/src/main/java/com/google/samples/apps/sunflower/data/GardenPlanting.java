package com.google.samples.apps.sunflower.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(
        tableName = "garden_plantings",
        foreignKeys = @ForeignKey(entity = Plant.class, parentColumns = "id", childColumns = "plant_id"),
        indices = @Index("plant_id")
)
public class GardenPlanting {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") public long gardenPlantingId = 0;
    @ColumnInfo(name = "plant_id") public final String plantId;
    @ColumnInfo(name = "plant_date") public final Calendar plantDate;
    @ColumnInfo(name = "last_watering_date") public final Calendar lastWateringDate;

    public GardenPlanting(long gardenPlantingId,
                          String plantId,
                          Calendar plantDate,
                          Calendar lastWateringDate) {
        this.gardenPlantingId = gardenPlantingId;
        this.plantId = plantId;
        this.plantDate = plantDate;
        this.lastWateringDate = lastWateringDate;
    }

    public static GardenPlanting of(String plantId) {
        return new GardenPlanting(0, plantId, Calendar.getInstance(), Calendar.getInstance());
    }
}
