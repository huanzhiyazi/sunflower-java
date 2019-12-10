package com.google.samples.apps.sunflower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter;
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding;
import com.google.samples.apps.sunflower.utilities.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel;

import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.PLANT_LIST_PAGE_INDEX;

public class GardenFragment extends Fragment {
    private GardenPlantingListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders
                .of(this, InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext()))
                .get(GardenPlantingListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGardenBinding binding = FragmentGardenBinding.inflate(inflater, container, false);
        final GardenPlantingAdapter adapter = new GardenPlantingAdapter();
        binding.gardenList.setAdapter(adapter);

        binding.addPlant.setOnClickListener(view -> {
            navigateToPlantListPage();
        });

        subscribeUi(adapter, binding);
        return binding.getRoot();
    }

    private void subscribeUi(GardenPlantingAdapter adapter, FragmentGardenBinding binding) {
        viewModel.plantAndGardenPlantings().observe(getViewLifecycleOwner(), result -> {
            binding.setHasPlantings(result != null && !result.isEmpty());
            adapter.submitList(result);
        });
    }

    // TODO: convert to data binding if applicable
    private void navigateToPlantListPage() {
        ((ViewPager2) requireActivity().findViewById(R.id.view_pager))
                .setCurrentItem(PLANT_LIST_PAGE_INDEX);
    }
}
