package com.google.samples.apps.sunflower.adapters;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.samples.apps.sunflower.GardenFragment;
import com.google.samples.apps.sunflower.PlantListFragment;

import java.util.Map;

public class SunflowerPagerAdapter extends FragmentStateAdapter {
    public static final int MY_GARDEN_PAGE_INDEX = 0;
    public static final int PLANT_LIST_PAGE_INDEX = 1;
    private static final Map<Integer, IFragmentCreator> tabFragmentsCreators = new ArrayMap<>(2);
    static {
        tabFragmentsCreators.put(MY_GARDEN_PAGE_INDEX, GardenFragment::new);
        tabFragmentsCreators.put(PLANT_LIST_PAGE_INDEX, PlantListFragment::new);
    }

    public SunflowerPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        IFragmentCreator creator = tabFragmentsCreators.get(position);
        if (creator == null) throw new IndexOutOfBoundsException();

        return creator.create();
    }

    @Override
    public int getItemCount() {
        return tabFragmentsCreators.size();
    }

    private interface IFragmentCreator {
        Fragment create();
    }
}
