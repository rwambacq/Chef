package groep10.chef.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.Recipe;
import groep10.chef.Class.RecipeAdapter;
import groep10.chef.Main;
import groep10.chef.R;

/**
 * Created by thoma on 5/05/2018.
 */

public class FavoriteFragment extends Fragment {

    public List<Recipe> recipes;
    public List<Ingredient> ingredients;
    public Main main;
    public Button test;
    public SearchView zzz;

    public RecyclerView list;
    RecipeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        Main ln = (Main) this.getActivity();
        main = ln;
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if(args!=null && args.containsKey("recipes")){
            recipes = (List<Recipe>) args.getSerializable("recipes");
        }
        else {
            recipes = ln.recipes;
        }
        ingredients = ln.ingredients;
    }

    public void addButton(){
        test.setVisibility(View.VISIBLE);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Main)getActivity()).openRecipeSearch(null);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_search, parentVG, false);
        list = (RecyclerView) v.findViewById(R.id.recy);
        list.setHasFixedSize(true);
        LinearLayoutManager x = new LinearLayoutManager(this.getContext());
        ArrayList<Recipe> nieuw = new ArrayList<>();
        nieuw = sorter(recipes);
        test = (Button) v.findViewById(R.id.button);
        if(nieuw.size()==0){
           addButton();
        }
        list.setLayoutManager(x);
        adapter = new RecipeAdapter(nieuw, main, this.getContext(),true,this);
        list.setAdapter(adapter);
        return v;
    }


    public ArrayList<Recipe> sorter(List<Recipe> lijst){
        ArrayList<Recipe> nieuw = new ArrayList<>();
        for (Recipe in : lijst){
            if(in.favo){
                nieuw.add(in);
            }
        }
        return nieuw;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchfile,menu);
        final MenuItem myaction = menu.findItem(R.id.search);
        zzz = (SearchView) myaction.getActionView();
        // ((EditText)zzz.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor("#FFFFFF");

        zzz.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!zzz.isIconified()){
                    zzz.setIconified(true);
                }
                myaction.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<Recipe> z = filter(recipes,newText);
                adapter.setFilter(z);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private List<Recipe> filter(List<Recipe>p1,String s){
        s=s.toLowerCase();
        final List<Recipe> filterm = new ArrayList<>();
        for (Recipe z : p1){
            final String abc = z.getName().toLowerCase();
            if(abc.contains(s)){
                filterm.add(z);
            }
        }
        return filterm;
    }
}
