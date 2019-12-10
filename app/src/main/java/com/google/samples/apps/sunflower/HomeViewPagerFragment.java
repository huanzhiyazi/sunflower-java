package com.google.samples.apps.sunflower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter;
import com.google.samples.apps.sunflower.databinding.FragmentViewPagerBinding;

import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.MY_GARDEN_PAGE_INDEX;
import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.PLANT_LIST_PAGE_INDEX;

public class HomeViewPagerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentViewPagerBinding binding = FragmentViewPagerBinding.inflate(inflater, container, false);
        final TabLayout tabLayout = binding.tabs;
        final ViewPager2 viewPager = binding.viewPager;

        viewPager.setAdapter(new SunflowerPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setIcon(getTabIcon(position));
            tab.setText(getTabTitle(position));
        }).attach();

        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);

        return binding.getRoot();
    }

    private int getTabIcon(int position) {
        if (position == MY_GARDEN_PAGE_INDEX) {
            return R.drawable.garden_tab_selector;
        } else if (position == PLANT_LIST_PAGE_INDEX) {
            return R.drawable.plant_list_tab_selector;
        }
        throw new IndexOutOfBoundsException();
    }

    private String getTabTitle(int position) {
        if (position == MY_GARDEN_PAGE_INDEX) {
            return getString(R.string.my_garden_title);
        } else if (position == PLANT_LIST_PAGE_INDEX) {
            return  getString(R.string.plant_list_title);
        }
        return null;
    }
}
