package com.google.samples.apps.sunflower.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding;

import java.util.Objects;

public class PlantAdapter extends ListAdapter<Plant, RecyclerView.ViewHolder> {

    public PlantAdapter() {
        super(new PlantDiffCallback());
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {
        private final ListItemPlantBinding binding;

        PlantViewHolder(ListItemPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(view -> {
                Plant plant = this.binding.getPlant();
                if (plant != null) {
                    navigateToPlant(plant, view);
                }
            });
        }

        private void navigateToPlant(Plant plant, View it) {
            final NavDirections direction =
                    HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                            plant.plantId
                    );
            Navigation.findNavController(it).navigate(direction);
        }

        void bind(Plant item) {
            binding.setPlant(item);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantViewHolder(ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Plant plant = getItem(position);
        ((PlantViewHolder) holder).bind(plant);
    }

    private static class PlantDiffCallback extends DiffUtil.ItemCallback<Plant> {

        @Override
        public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return TextUtils.equals(oldItem.plantId, newItem.plantId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return Objects.equals(oldItem, newItem);
        }
    }
}
