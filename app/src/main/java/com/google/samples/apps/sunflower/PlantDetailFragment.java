package com.google.samples.apps.sunflower;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ShareCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding;
import com.google.samples.apps.sunflower.utilities.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlantDetailFragment extends Fragment {

    private PlantDetailViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        PlantDetailFragmentArgs args = PlantDetailFragmentArgs.fromBundle(getArguments());

        viewModel = ViewModelProviders
                .of(this, InjectorUtils.providePlantDetailViewModelFactory(requireContext(), args.getPlantId()))
                .get(PlantDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentPlantDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant_detail, container, false);
        viewModel.plant().observe(getViewLifecycleOwner(), binding::setPlant);
        viewModel.isPlanted().observe(getViewLifecycleOwner(), binding::setIsPlanted);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setCallback(plant -> {
            if (plant != null) {
                hideAppBarFab(binding.fab);
                viewModel.addPlantToGarden();
                Snackbar.make(binding.getRoot(), R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        AtomicBoolean isToolbarShown = new AtomicBoolean(false);

        // scroll change listener begins at Y = 0 when image is fully collapsed
        binding.plantDetailScrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            // User scrolled past image to height of toolbar and the title text is
            // underneath the toolbar, so the toolbar should be shown.
            final boolean shouldShowToolbar = scrollY > binding.toolbar.getHeight();

            // The new state of the toolbar differs from the previous state; update
            // appbar and toolbar attributes.
            if (isToolbarShown.get() != shouldShowToolbar) {
                isToolbarShown.set(shouldShowToolbar);
            }

            // Use shadow animator to add elevation if toolbar is shown
            binding.appbar.setActivated(shouldShowToolbar);

            // Show the plant name if toolbar is shown
            binding.toolbarLayout.setTitleEnabled(shouldShowToolbar);
        });

        binding.toolbar.setNavigationOnClickListener(view -> Navigation.findNavController(view).navigateUp());

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                createShareIntent();
                return true;
            }
            return false;
        });

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    private void createShareIntent() {
        final Plant plant = viewModel.plant().getValue();
        final String shareText;
        if (plant == null) {
            shareText = "";
        } else {
            shareText = getString(R.string.share_text_plant, plant.name);
        }
        final Intent shareIntent = ShareCompat.IntentBuilder.from(Objects.requireNonNull(getActivity()))
                .setText(shareText)
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(shareIntent);
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private void hideAppBarFab(FloatingActionButton fab) {
        final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        final FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) params.getBehavior();
        assert behavior != null;
        behavior.setAutoHideEnabled(false);
        fab.hide();
    }

    public interface Callback {
        void add(Plant plant);
    }
}
