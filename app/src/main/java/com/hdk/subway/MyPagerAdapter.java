package com.hdk.subway;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter{

    List<Fragment> fragments = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> list) {
        super(fragmentActivity);

        for(int i = 0; i < list.size(); i++){
            fragments.add(new TabFragment());
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}