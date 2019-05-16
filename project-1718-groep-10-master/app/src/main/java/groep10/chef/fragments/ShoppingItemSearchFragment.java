package groep10.chef.fragments;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.ShoppingIngredientAdapter;
import groep10.chef.Main;
import groep10.chef.R;

/**
 * Created by Jules on 17/04/2018.
 */

public class ShoppingItemSearchFragment extends Fragment {
    public List<Ingredient> ingredients;
    public List<Ingredient> ingredienten;
    public Main main;
    public SearchView zzz;


    public RecyclerView list;
    ShoppingIngredientAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        Main ln = (Main) this.getActivity();
        main = ln;
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if(args!=null && args.containsKey("ingredients")){
            ingredients = (List<Ingredient>) args.getSerializable("ingredients");
        }
        else {
            ingredients = ln.ingredients;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ingredient_search_2, parentVG, false);
        list = (RecyclerView) v.findViewById(R.id.recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager x = new LinearLayoutManager(this.getContext());
        list.setLayoutManager(x);
        ingredienten = new ArrayList<Ingredient>();
        for(Ingredient ing : ingredients){
            if (!main.toboodschappenlijst.contains(ing)){
                ingredienten.add(ing);
            }
        }
        ingredienten = sorter(ingredienten);
        adapter = new ShoppingIngredientAdapter(ingredienten, main, this.getContext());
        list.setAdapter(adapter);
        return v;
    }

    public List<Ingredient> sorter(List<Ingredient> lijst){
        Collections.sort(lijst);
        return lijst;
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
                List<Ingredient> z = filter(ingredienten,newText);
                adapter.setFilter(z);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private List<Ingredient> filter(List<Ingredient>p1,String s){
        s=s.toLowerCase();
        final List<Ingredient> filterm = new ArrayList<>();
        for (Ingredient z : p1){
            final String abc = z.getName().toLowerCase();
            if(abc.contains(s)){
                filterm.add(z);
            }
        }
        return filterm;
    }
}
