package com.hdk.subway;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter{


    private List<String> list;
    private String stationName;

    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> list, String stationName) {
        super(fragmentActivity);

        this.list = list;
        this.stationName = stationName;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String line = list.get(position);
        return new TabFragment(stationName, line);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
//
//    List<Fragment> fragments = new ArrayList<>();
//
//    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> list, String stationName) {
//        super(fragmentActivity);
//
//        for(int i = 0; i < list.size(); i++){
//            fragments.add(new TabFragment(stationName, list));
//        }
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        return fragments.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return fragments.size();
//    }