package groep10.chef.Class;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by thoma on 13/04/2018.
 */

public class Ingredient implements Serializable, Comparable<Ingredient> {
    private Id _id;
    private double hoeveelheid = 1;
    private String name;
    private String soort;
    public boolean gram;


    //delhaize
    private double delhaize;
    private String delhaizeurl;

    //colruyt
    private double colruyt;
    private String colruyturl;

    //albert heijn
    private double albertheijn;
    private String albertheijnurl;

    public Ingredient(String name, String soort, double delhaize_price, String delhaize_url, double colruyt_price, String colruyt_url, double albert_heijn_price, String albert_heijn_url) {
        this.name = name;
        this.soort = soort;
        this.delhaize = delhaize_price;
        this.delhaizeurl = delhaize_url;
        this.colruyt = colruyt_price;
        this.colruyturl = colruyt_url;
        this.albertheijn = albert_heijn_price;
        this.albertheijnurl = albert_heijn_url;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getSoort() {
        return soort;
    }

    public double getDelhaize() {
        return delhaize;
    }

    public String getDelhaizeurl() {
        return delhaizeurl;
    }

    public double getColruyt() {
        return colruyt;
    }

    public String getColruyturl() {
        return colruyturl;
    }

    public double getAlbertheijn() {
        return albertheijn;
    }

    public String getAlbertheijnurl() {
        return albertheijnurl;
    }

    public double getHoeveelheid() {
        return hoeveelheid;
    }

    public void setHoeveelheid(double hoeveelheid) {
        this.hoeveelheid = hoeveelheid;
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getBigSoort() {
        String rsoort = soort;
        if (soort.equals("")){
            rsoort = "st";
        }
        return rsoort;
    }

    public String getSmallSoort() {
        String rsoort = soort;
        if (soort.equals("")){
            rsoort = "st";
        }
        if (soort.equals("kg")){
            rsoort = "g";
        }
        if (soort.equals("l")){
            rsoort = "cl";
        }
        return rsoort;
    }

    public double getStoreprice(String shop){
        double price;

        if (shop.equals("delhaize")){
            price = getDelhaize();
        }
        else if (shop.equals("colruyt")){
            price = getColruyt();
        }
        else {
            price = getAlbertheijn();
        }
        return price;
    }

    @Override
    public int compareTo(@NonNull Ingredient ingredient) {
        return this.getName().compareTo(ingredient.getName());

    }
}