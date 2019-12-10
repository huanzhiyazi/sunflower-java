package com.google.samples.apps.sunflower.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections;
import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding;
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel;

import java.util.Objects;

public class GardenPlantingAdapter extends ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> {
    public GardenPlantingAdapter() {
        super(new GardenPlantDiffCallback());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemGardenPlantingBinding binding;

        ViewHolder(ListItemGardenPlantingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(view -> {
                PlantAndGardenPlantingsViewModel viewModel = this.binding.getViewModel();
                if (viewModel != null) {
                    String plantId = viewModel.plantId();
                    if (plantId != null) navigateToPlant(plantId, view);
                }
            });
        }

        private void navigateToPlant(String plantId, View view) {
            final NavDirections direction = HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(plantId);
            Navigation.findNavController(view).navigate(direction);
        }

        void bind(PlantAndGardenPlantings plantings) {
            PlantAndGardenPlantingsViewModel viewModel = new PlantAndGardenPlantingsViewModel(plantings);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.list_item_garden_planting, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static class GardenPlantDiffCallback extends DiffUtil.ItemCallback<PlantAndGardenPlantings> {

        @Override
        public boolean areItemsTheSame(@NonNull PlantAndGardenPlantings oldItem, @NonNull PlantAndGardenPlantings newItem) {
            return TextUtils.equals(oldItem.plant.plantId, newItem.plant.plantId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlantAndGardenPlantings oldItem, @NonNull PlantAndGardenPlantings newItem) {
            return Objects.equals(oldItem, newItem);
        }
    }
}
