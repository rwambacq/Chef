package groep10.chef.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.IngredientListAdapter;
import groep10.chef.Class.Recipe;
import groep10.chef.Main;
import groep10.chef.R;

public class IngredientSearchFragment extends Fragment {

    private Button addIngredient;
    private Button findRecipe;
    private ListView lijst;
    IngredientListAdapter ila;
    private List<Ingredient> selected;

    private Main main;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        main = (Main) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.ingredient_list, parentVG, false);

        addIngredient = v.findViewById(R.id.add_item);
        findRecipe = v.findViewById(R.id.find_recipe);

        lijst = v.findViewById(R.id.ingredient_list);

        Bundle b = getArguments();
        selected = (List<Ingredient>) b.get("sel");

        ila = new IngredientListAdapter(selected, getContext(),IngredientSearchFragment.this);
        lijst.setAdapter(ila);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Main)getActivity()).openFindIngredient(null);

            }
        });

        findRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.openRecipeSearch(view, filterRecipesByIngredient(main.recipes, selected));
            }
        });

        return v;
    }

    private List<Recipe> filterRecipesByIngredient(List<Recipe> recipes, List<Ingredient> selected) {
        List<Recipe> filteredRecipes = new ArrayList<>(recipes);
        Iterator<Recipe> it = filteredRecipes.iterator();
        while(it.hasNext()){
            Recipe recipe = it.next();
            for(Ingredient ingredient : selected){
                if(!recipe.usesIngredient(ingredient)){
                    it.remove();
                    break;
                }
            }
        }
        return filteredRecipes;
    }


    public void removeElement(int position){
        selected.remove(position);
        ila.notifyDataSetChanged();
    }


}