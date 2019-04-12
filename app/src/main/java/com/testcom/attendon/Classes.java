package com.testcom.attendon;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Classes extends Fragment {
private ClassesAdapter mSectionsPageAdapter;
 private   ViewPager pager;
    public Classes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.classes, container, false);
        pager = (ViewPager)rootView.findViewById(R.id.containner);

        TabLayout layout = (TabLayout) rootView.findViewById(R.id.tablayout);
        layout.setupWithViewPager(pager);
        setupViewPager(pager);
        return rootView;

    }


    private void setupViewPager (ViewPager viewPager){
        ClassesAdapter gg = new ClassesAdapter(getChildFragmentManager());
        gg.addFragment(new Class1(), "Your Classes");
        gg.addFragment(new Class2(), "Owned Classes");
        viewPager.setAdapter(gg);
    }




}
