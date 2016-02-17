package com.yuliatallus.moneytracker.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.yuliatallus.moneytracker.MoneyTrackerApplication;
import com.yuliatallus.moneytracker.database.Categories;
import com.yuliatallus.moneytracker.rest.RestService;
import com.yuliatallus.moneytracker.rest.model.CreateCategoryModel;
import com.yuliatallus.moneytracker.rest.model.GoogleJsonModel;
import com.yuliatallus.moneytracker.sync.TrackerSyncAdapter;
import com.yuliatallus.moneytracker.ui.fragments.CategoriesFragment_;
import com.yuliatallus.moneytracker.ui.fragments.ExpensesFragment_;
import com.yuliatallus.moneytracker.R;
import com.yuliatallus.moneytracker.ui.fragments.SettingsFragment_;
import com.yuliatallus.moneytracker.ui.fragments.StatisticsFragment_;
import com.yuliatallus.moneytracker.util.ConstantBox;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    String pictureUrl, name, email;
    Bitmap bitmap;

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
        getInfoForDrawer();

        initCategories();

        Log.d(TAG, MoneyTrackerApplication.getAuthKey());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment_()).commit();

        //TrackerSyncAdapter.initializeSyncAdapter(this);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            CreateCategoryModel createCategoryModel = restService.createCat(this, cat.name);
            switch (createCategoryModel.getStatus()){

                case ConstantBox.SUCCESS:
                    Log.d(TAG, "Status: " + createCategoryModel.getStatus() +
                            ", Title: " + createCategoryModel.getData().getTitle() +
                            ", Id: " + createCategoryModel.getData().getId());
                    break;

                case ConstantBox.UNAUTHORIZED:
                    startActivity(new Intent(this, LoginActivity_.class));
                    break;

                case ConstantBox.ERROR:
                    Log.d(TAG, "Ошибка при добавлении категории");
                    break;
            }

        }

    }


    @Background
    void getInfoForDrawer() {
        if (!MoneyTrackerApplication.getGoogleKey(this).equalsIgnoreCase("2")) {

            RestService restService = new RestService();
            GoogleJsonModel googleJsonModel = restService.getJsonModel(this);

            name = googleJsonModel.getName();
            email = googleJsonModel.getEmail();
            pictureUrl = googleJsonModel.getPicture();

            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(googleJsonModel.getPicture()).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageChanging();
            textChanging();

        }
    }

    @UiThread
    void imageChanging(){

        CircularImageView i = (CircularImageView)findViewById(R.id.profile_picture);
        i.setImageBitmap(bitmap);

    }

    @UiThread
    void textChanging(){

        TextView nameAndSurnameText = (TextView)findViewById(R.id.name_and_surname);
        TextView eMailText = (TextView)findViewById(R.id.e_mail);

        nameAndSurnameText.setText(name);
        eMailText.setText(email);
    }



}