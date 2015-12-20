package com.yuliatallus.moneytracker.ui.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.ui.fragments.CategoriesFragment_;
import com.yuliatallus.moneytracker.ui.fragments.ExpensesFragment_;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.ui.fragments.SettingsFragment_;
import com.yuliatallus.moneytracker.ui.fragments.StatisticsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    protected Fragment fragment;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.navigtion_view)
    NavigationView navigationView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void ready() {
        setupToolbar();
        setupDrawer();
        initCategories();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment_()).commit();
    }

    private void initCategories(){
        Categories catEntertainment = new Categories("Развлечения");
        catEntertainment.save();
        Categories catBooks = new Categories("Книги");
        catBooks.save();
        Categories catEducation = new Categories("Образование");
        catEducation.save();
        Categories catPhone = new Categories("Телефон");
        catPhone.save();
        Categories catFood = new Categories("Еда");
        catFood .save();
        Categories catClothes = new Categories("Одежда");
        catClothes.save();
    }

    @Override
    public void onBackPressed() {

        Fragment findingFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (findingFragment != null && findingFragment instanceof ExpensesFragment_) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            navigationView.setCheckedItem(R.id.drawer_expenses);
        } else if (findingFragment instanceof CategoriesFragment_) {
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (findingFragment instanceof StatisticsFragment_) {
            navigationView.setCheckedItem(R.id.drawer_statistics);
        } else if (findingFragment instanceof SettingsFragment_) {
            navigationView.setCheckedItem(R.id.drawer_settings);
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.drawer_expenses:
                        fragment = new ExpensesFragment_();
                        break;

                    case R.id.drawer_categories:
                        fragment = new CategoriesFragment_();
                        break;

                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment_();
                        break;

                    case R.id.drawer_settings:
                        fragment = new SettingsFragment_();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}