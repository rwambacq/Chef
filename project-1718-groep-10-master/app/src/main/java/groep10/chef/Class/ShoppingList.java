package groep10.chef.Class;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by thoma on 13/04/2018.
 */

public class ShoppingList {

    private List<Ingredient> list = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(List<Ingredient> list) {
        this.list = list;
    }

    public List<Ingredient> getList() {
        return list;
    }

    public void setList(List<Ingredient> list) {
        this.list = list;
    }

    public void addToList(Ingredient ingredient){
        list.add(ingredient);
    }

    public void addToList(List<Ingredient> ingredienten){
        list.addAll(ingredienten);
    }

    public void removeFromList(Ingredient ingredient){
        //list.removeIf(s->s.getName().equals(ingredient.getName()));
    }

    public List<DTO> getCheapestWithoutAll(){
        List<DTO> lijst = comparaPrices();
        Collections.sort(lijst);
        return lijst;
    }

    public List<DTO> getCheapestAndAll(){
        List<DTO> lijst = comparaPrices();
        Collections.sort(lijst);
        List<DTO> newlijst = new ArrayList<>();
        for (DTO x : lijst){
            if (x.lijst.size() == 0){
                newlijst.add(x);
            }
        }
        if (newlijst.size() == 0){
            System.out.println("Geen winkel gevonden die alles bevat, zal nu zoeken op het meest \"hebbende\"");
            lijst = getCheapestAndMost();
        }
        return lijst;
    }

    public List<DTO> getCheapestAndMost(){
        List<DTO> lijst = comparaPrices();
        Collections.sort(lijst);
        return lijst;
    }

    public List<DTO> comparaPrices(){
        double prijsdelhaize = 0;
        double prijscol = 0;
        double prijsah = 0;
        List<String> alld = new ArrayList<>();
        List<String> allc = new ArrayList<>();
        List<String> alla = new ArrayList<>();
        for (Ingredient i : this.list){
            double pd = i.getDelhaize();
            double pc = i.getColruyt();
            double pa = i.getAlbertheijn();
            if(pd != 9999.00) {
                if (i.getSoort().equals("")) {
                    prijsdelhaize += (pd*i.getHoeveelheid());
                }else {
                    double z = i.getHoeveelheid() / 1000;
                    z = z * pd;
                    prijsdelhaize += z;
                }
            }else{
                alld.add(i.getName());
            }
            if(pc != 9999.00) {
                if(i.getSoort().equals("")) {
                    prijscol += (pc*i.getHoeveelheid());
                }else {
                    double z = i.getHoeveelheid()/1000;
                    z = z * pc;
                    prijscol += z;
                }
            }else{
                allc.add(i.getName());
            }
            if(pa != 9999.00){
                if(i.getSoort().equals("")) {
                    prijsah += (pa*i.getHoeveelheid());
                }else {
                    double z = i.getHoeveelheid()/1000;
                    z = z * pa;
                    prijsah += z;
                }
            }else{
                alla.add(i.getName());
            }
        }
        List<DTO> x = new ArrayList<>();
        x.add(new DTO(prijscol,allc,"colruyt"));
        x.add(new DTO(prijsah , alla,"albert heijn"));
        x.add(new DTO(prijsdelhaize,alld,"delhaize"));
        return x;
    }
}