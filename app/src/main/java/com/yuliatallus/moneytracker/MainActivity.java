package com.yuliatallus.moneytracker;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Fragment fragment;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawer();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment()).commit();
        }

    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {

        Fragment findingFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (findingFragment != null && findingFragment instanceof ExpensesFragment){
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }


    private void setupDrawer(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigtion_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.drawer_expenses:fragment = new ExpensesFragment();
                        navigationView.getMenu().findItem(R.id.drawer_expenses).setChecked(true);
                        break;

                    case R.id.drawer_categories:fragment = new CategoriesFragment();
                        navigationView.getMenu().findItem(R.id.drawer_categories).setChecked(true);
                        break;

                    case R.id.drawer_statistics:fragment = new StatisticsFragment();
                        navigationView.getMenu().findItem(R.id.drawer_statistics).setChecked(true);
                        break;

                    case R.id.drawer_settings:fragment = new SettingsFragment();
                        navigationView.getMenu().findItem(R.id.drawer_settings).setChecked(true);
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).addToBackStack(null).commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id ==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
