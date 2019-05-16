package groep10.chef;

/**
 * Created by ruben on 02-Apr-18.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import groep10.chef.Class.Recipe;
import groep10.chef.database.Common;
import groep10.chef.database.HTTPDataHandler;
import groep10.chef.fragments.FavoriteFragment;
import groep10.chef.fragments.HomeFragment;
import groep10.chef.fragments.IngredientSearchFragment;
import groep10.chef.fragments.IngredientSearchFragment2;
import groep10.chef.fragments.NoResultFragment;
import groep10.chef.fragments.RecipeDetailsFragment;
import groep10.chef.fragments.RecipeSearchFragment;
import groep10.chef.fragments.ShoppingItemDetailFragment;
import groep10.chef.fragments.ShoppingItemSearchFragment;
import groep10.chef.fragments.ShoppingListFragment;
import groep10.chef.Class.Ingredient;
import groep10.chef.fragments.StoreCompareFragment;
import groep10.chef.test.UnitTest;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.TaskCallbacks{

    private DrawerLayout drawerLayout; // Navigation drawer
    private FragmentManager fm;
    private Fragment mainFragment; // Hierin worden alle fragments ingeladen
    public Recipe mainRecipe; // ???
    public ActionBar bar; // Navigation bar bovenaan het scherm met "Chef" en het drawer icon
    private NavigationView nav;

    public List<Ingredient> toboodschappenlijst = new ArrayList<>(); // List die wordt gebruikt om vanuit een recept ingredienten door te geven aan de booschappenlijst
    public  ArrayList<String> favorites = new ArrayList<>(); // Lijst van favoriete recepten, komen bovenaan te staan

    public  List<Ingredient> ingredients = new ArrayList<>(); // Volledige lijst van ingredienten, ingeladen uit de database
    public  List<Recipe> recipes = new ArrayList<>(); // Volledige lijst van recepten, ingeladen uit de database

    private List<Ingredient> func1List = new ArrayList<>(); // List die wordt gebruikt om geselecteerde ingredienten door te geven aan de zoekfunctie in "Zoek adhv ingredienten"
    public int shoppingPosition; // ???

    public static String RECIPE_FILE = "recipeFile"; // Bestandsnaam voor database waarden
    public static String INGREDIENT_FILE = "ingredientFile"; // Bestandsnaam voor database waarden
    public static String LAST_DOWNLOAD = "lastDownload"; // Shared preferences naam voor laatste database download

    private UnitTest test;

    private ProgressDialog ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        drawerLayout = findViewById(R.id.drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Action bar initializeren met hamburger icoontje
        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeAsUpIndicator(R.drawable.ic_menu);

        fm = getSupportFragmentManager();
        mainFragment = fm.findFragmentById(R.id.fragment_container);

        // Home screen inladen als het eerste fragment
        if(savedInstanceState == null) {
            if (mainFragment == null) {
                mainFragment = new HomeFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, mainFragment,"HOMEPAGE")
                        .commit();
            }
        }
        else{
            // RESTORE STATE HERE!
            mainRecipe = (Recipe) savedInstanceState.getSerializable("main_recipe");
        }

        nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().getItem(0).setChecked(true);
        ps = new ProgressDialog(Main.this,ProgressDialog.THEME_HOLO_DARK);
        inladenData();
    }

    protected void onStart() {
        super.onStart();
        if(ingredients == null || ingredients.size() == 0){
            fillListsFromFiles();
        }
    }

    protected void onStop(){
        super.onStop();
        recipes = null;
        ingredients = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("main_recipe", mainRecipe);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_nav:
                openHome(null);
                break;
            case R.id.func1_nav:
                openIngredientSearchButton(null);
                break;
            case R.id.func2_nav:
                openRecipeSearch(null);
                break;
            case R.id.shopping_nav:
                openShoppingList(null);
                break;
            case R.id.shopping_favorite:
                openFavorites(null);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences test = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();
        Gson gson= new Gson();
        String json = gson.toJson(toboodschappenlijst);
        editor.putString("task list",json);
        editor.apply();

        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", favorites.size());

        for ( int i = 0; i< favorites.size(); i++ )
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, favorites.get(i));
        }
        mEdit1.apply();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if ( drawerLayout.isDrawerOpen( GravityCompat.START )) {
            drawerLayout.closeDrawer( GravityCompat.START );
        } else if ( fm.getBackStackEntryCount() > 0 ) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void openHome(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        nav.getMenu().getItem(0).setChecked(true);
    }

    public void openIngredientSearch(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("sel", new ArrayList<Ingredient>());
        bundle.putSerializable("listIng", (Serializable)ingredients);
        bundle.putSerializable("listRec", (Serializable)recipes);
        IngredientSearchFragment is = new IngredientSearchFragment();
        is.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, is).addToBackStack(null).commit();
        nav.getMenu().getItem(1).setChecked(true);
    }

    public void openIngredientSearchButton(View v){
        clearFunc1List();
        openIngredientSearch(v);
    }


    public void openRecipeSearch(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipes", (Serializable) recipes);
        RecipeSearchFragment x = new RecipeSearchFragment();
        x.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x).addToBackStack(null).commit();
        nav.getMenu().getItem(2).setChecked(true);
    }

    public void openRecipeSearch(View v, List<Recipe> recipes){
        if(recipes != null && recipes.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("filtered_recipes", (Serializable) recipes);
            RecipeSearchFragment x = new RecipeSearchFragment();
            x.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x).addToBackStack(null).commit();
        }
        else{
            NoResultFragment x = new NoResultFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x).addToBackStack(null).commit();
        }
    }

    public void openRecipeDetail(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeDetailsFragment(),"NOT").addToBackStack(null).commit();

    }

    public void openShoppingList(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingListFragment(),"NOT").addToBackStack(null).commit();
        nav.getMenu().getItem(3).setChecked(true);
    }


    public void openShoppingItemSearch(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredients", (Serializable) ingredients);
        ShoppingItemSearchFragment x = new ShoppingItemSearchFragment();
        x.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x,"NOT").addToBackStack(null).commit();
    }

    public void openShoppingItemDetail(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingItemDetailFragment(),"NOT").addToBackStack(null).commit();
    }

    public void addShoppingItemDetail(Ingredient ing){
        ShoppingItemDetailFragment sidf = new ShoppingItemDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable("ing", ing);
        sidf.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, sidf).addToBackStack(null).commit();
    }


    public void openStoreCompare(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreCompareFragment(),"NOT").addToBackStack(null).commit();
    }

    public void openFindIngredient(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredients", (Serializable) ingredients);
        IngredientSearchFragment2 x = new IngredientSearchFragment2();
        x.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x).addToBackStack(null).commit();
    }

    public void openFavorites(View v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipes", (Serializable) recipes);
        FavoriteFragment x = new FavoriteFragment();
        x.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, x,"NOT").addToBackStack(null).commit();
        nav.getMenu().getItem(4).setChecked(true);
    }

    public void ingredientAdded(String naam){
        boolean found = false;
        int i = 0;
        while(!found && i < ingredients.size()){
            Ingredient in = ingredients.get(i);
            if(in.getName().equals(naam)){
                if(!func1List.contains(in)) {
                    func1List.add(in);
                }
                found = true;
            } else {
                i++;
            }
        }
        IngredientSearchFragment isf = new IngredientSearchFragment();
        Bundle b = new Bundle();
        b.putSerializable("sel", (Serializable) func1List);
        isf.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, isf).addToBackStack(null).commit();
    }

    //Maak de lijst van ingredienten leeg die bij func 1 wordt gebruikt
    //VOOR FUNCTIE 1
    public void clearFunc1List(){
        func1List.clear();
    }

    //Volgende 2 methodes zijn voor het aanpassen van de ingredienten waar op wordt gezocht in functie 1
    public void addSearchIngredient(Ingredient i){
        func1List.add(i);
    }

    public void removeSearchIngredient(Ingredient i){
        func1List.remove(i);
    }

    public void inladenData(){
        SharedPreferences preferences = getSharedPreferences("shared",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Ingredient>>(){}.getType();
        toboodschappenlijst = gson.fromJson(json,type);
        if ( toboodschappenlijst == null ){
            toboodschappenlijst = new ArrayList<>();
        }

        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        favorites.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            favorites.add(mSharedPreference1.getString("Status_" + i, null));
        }

    }
    // Leest de twee bestanden in met de JSON data van de ingredienten en recepten, en zet deze om naar List<Recipe/Ingredient>
    private void fillListsFromFiles (){
        Gson gson = new Gson();
        try {
            //Reading in contents from recipe file and making them into a List<Recipe>
            Type listtypeRecipe = new TypeToken<List<Recipe>>(){}.getType();
            FileInputStream fis1 = openFileInput(RECIPE_FILE);
            InputStreamReader isr1 = new InputStreamReader(fis1);
            BufferedReader br1 = new BufferedReader(isr1);
            String recipeString = br1.readLine();
            recipes = gson.fromJson(recipeString,listtypeRecipe);
            for (Recipe in : recipes){
                if(favorites.contains(in.getName())){
                    in.favo = true;
                }
            }

            //Reading in contents from ingredient file and making them into a List<Ingredient>
            Type listtypeIngredient = new TypeToken<List<Ingredient>>(){}.getType();
            FileInputStream fis2 = openFileInput(INGREDIENT_FILE);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader br2 = new BufferedReader(isr2);
            String ingredientString = br2.readLine();
            ingredients = gson.fromJson(ingredientString,listtypeIngredient);

            test.assertListsFilled(ingredients, recipes);
            test.assertFilesMade(Arrays.asList(fileList()), RECIPE_FILE, INGREDIENT_FILE);

        } catch (FileNotFoundException e) {
            System.err.println("Oepsie geen filetje hier");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreExecute1() {
        ps.setTitle("Applicatie starten");
        ps.setMessage("Ingredienten downloaden");
        ps.setCanceledOnTouchOutside(false);
        ps.show();
    }

    @Override
    public void onPostExecute1(String s) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(INGREDIENT_FILE, MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.dismiss();
    }

    @Override
    public void onPreExecute2() {
        ps.setTitle("Applicatie starten");
        ps.setMessage("Recepten downloaden");
        ps.setCanceledOnTouchOutside(false);
        ps.show();
    }

    @Override
    public void onPostExecute2(String s) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(RECIPE_FILE, MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
            if(recipes == null || ingredients.size() == 0){
                fillListsFromFiles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.dismiss();
    }
}