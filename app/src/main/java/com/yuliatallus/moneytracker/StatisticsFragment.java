package com.yuliatallus.moneytracker;

import android.support.v4.app.Fragment;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.statistic_fragment)
public class StatisticsFragment extends Fragment{

    @AfterViews
    void ready(){
        getActivity().setTitle(R.string.nav_drawer_statistics);
    }

}
