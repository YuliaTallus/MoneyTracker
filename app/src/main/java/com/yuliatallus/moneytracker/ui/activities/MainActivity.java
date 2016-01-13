package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.activeandroid.query.Select;
import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.CreateCategory;
import com.yuliatallus.moneytracker.ui.fragments.CategoriesFragment_;
import com.yuliatallus.moneytracker.ui.fragments.ExpensesFragment_;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.ui.fragments.SettingsFragment_;
import com.yuliatallus.moneytracker.ui.fragments.StatisticsFragment_;
import com.yuliatallus.moneytracker.util.ConstantBox;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
        addCategory(getDataList());
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

        super.onBackPressed();
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

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }

    @Background
    void addCategory(List<Categories> list){
        RestService restService = new RestService();
        for (Categories cat: list){
            CreateCategory createCategory = restService.createCategory(cat.name);
            switch (createCategory.getStatus()){

                case ConstantBox.SUCCESS:
                    Log.d(TAG, "Status: " + createCategory.getStatus() +
                            ", Title: " + createCategory.getData().getTitle() +
                            ", Id: " + createCategory.getData().getId() +
                            MoneyTrackerApplication.getAuthKey());
                    break;

                case ConstantBox.UNAUTHORIZED:
                    startActivity(new Intent(this, LoginActivity_.class));
                    break;
            }

        }

    }


}