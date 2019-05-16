package groep10.chef.Class;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by thoma on 13/04/2018.
 */

public class Recipe implements Serializable, Comparable<Recipe> {
    private Id _id;
    private String name;
    private String description;
    private String image;
    //amount of people served
    private String recipeInstructions;
    public boolean favo = false;
    private List<List<String>> recipeIngredient;
    private int number_of_people = 1;
    private List<Ingredient> right;

    public Recipe(String name, String sjort, String preparation, int number_of_people, List<List<String>> recipeIngredient,String image) {
        this.name = name;
        this.recipeInstructions = preparation;
        this.number_of_people = number_of_people;
        this.description = sjort;
        this.recipeIngredient = recipeIngredient;
        this.image = image;
    }

    public void fillup(List<Ingredient> s){
        List<Ingredient> lijst = new ArrayList<>();
        for(List<String> x : this.recipeIngredient){
            for (Ingredient z : s){
                if(z.getName().equals(x.get(0))){
                    Ingredient ok = z;
                    if(x.size()>1){
                        if(x.get(1).contains("g")){
                            ok.gram = true;
                        }
                        String str = x.get(1).replaceAll("\\D+","");
                        if(str.equals("")){
                            ok.setHoeveelheid(1);
                        }else{
                            ok.setHoeveelheid(Double.parseDouble(str));
                        }
                    }
                    lijst.add(z);
                }
            }
        }
        this.right = lijst;
    }

    public String getName() {
        return name;
    }

    public String getPreparation() {
        return recipeInstructions;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public String getShortdescription() {
        return description;
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public List<List<String>> getRecipeIngredients() {
        return recipeIngredient;
    }

    public void setRecipeIngredients(List<List<String>> recipeIngredients) {
        this.recipeIngredient = recipeIngredients;
    }

    public List<Ingredient> getRight() {
        return right;
    }

    public void setRight(List<Ingredient> right) {
        this.right = right;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean usesIngredient(Ingredient ingredient){
        Set<String> ingredientsUsed = new HashSet<String>();
        for(List<String> tuples : recipeIngredient){
            ingredientsUsed.add(tuples.get(0));
        }
        String ingredientString = ingredient.getName();
        return ingredientsUsed.contains(ingredientString);
    }

    @Override
    public int compareTo(@NonNull Recipe recipe) {
        return (this.favo != recipe.favo) ? (this.favo) ? -1 : 1 : 0;

    }
}