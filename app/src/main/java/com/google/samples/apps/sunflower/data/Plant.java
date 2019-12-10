package com.google.samples.apps.sunflower.data;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public final String plantId;
    public final String name;
    public final String description;
    public final int growZoneNumber;
    public int wateringInterval = 7;
    public String imageUrl = "";

    public Plant(@NonNull String plantId,
                 String name,
                 String description,
                 int growZoneNumber,
                 int wateringInterval,
                 String imageUrl) {
        this.plantId = plantId;
        this.name = name;
        this.description = description;
        this.growZoneNumber = growZoneNumber;
        this.wateringInterval = wateringInterval;
        this.imageUrl = imageUrl;
    }

    public boolean shouldBeWatered(Calendar since, Calendar lastWateringDate) {
        lastWateringDate.add(Calendar.DAY_OF_YEAR, wateringInterval);
        return since.compareTo(lastWateringDate) > 0;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Plant)) return false;

        final Plant o = (Plant) other;
        return TextUtils.equals(this.plantId, o.plantId) &&
                TextUtils.equals(this.name, o.name) &&
                TextUtils.equals(this.description, o.description) &&
                this.growZoneNumber == o.growZoneNumber &&
                this.wateringInterval == o.wateringInterval &&
                TextUtils.equals(this.imageUrl, o.imageUrl);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + plantId.hashCode();
        hash = hash * 31 + name.hashCode();
        hash = hash * 31 + description.hashCode();
        hash = hash * 31 + growZoneNumber;
        hash = hash * 31 + wateringInterval;
        hash = hash * 31 + imageUrl.hashCode();
        return hash;
    }
}
