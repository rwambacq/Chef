package groep10.chef.Class;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import groep10.chef.Main;
import groep10.chef.R;

/**
 * Created by thoma on 14/04/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holderview>{


    private List<Ingredient> lijst;
    private Context context;
    private Main main;

    public IngredientAdapter(List<Ingredient> lijst, Main x, Context context) {
        this.lijst = lijst;
        this.context = context;
        this.main = x;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customingredient,parent,false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(final Holderview holder, final int position) {
            holder.naam.setText(lijst.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = main.getSupportFragmentManager();
                    fm.popBackStack();
                    fm.popBackStack();
                    main.ingredientAdded(holder.naam.getText().toString());
                }
            });
    }

    @Override
    public int getItemCount() {
        return lijst.size();
    }

    public void setfilter(List<Ingredient> listitem){
        lijst = new ArrayList<>();
        lijst.addAll(listitem);
        notifyDataSetChanged();
    }
    class Holderview extends RecyclerView.ViewHolder {
        TextView naam;
        Holderview(View itemview){
            super(itemview);
            naam = (TextView)itemview.findViewById(R.id.ingredientname);
        }
    }
    public void setFilter(List<Ingredient> file){
        lijst = new ArrayList<>();
        lijst.addAll(file);
        notifyDataSetChanged();
    }
}