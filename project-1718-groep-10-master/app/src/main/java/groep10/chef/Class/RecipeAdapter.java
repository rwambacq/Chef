package groep10.chef.Class;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import groep10.chef.Main;
import groep10.chef.R;
import groep10.chef.fragments.FavoriteFragment;
import groep10.chef.fragments.RecipeSearchFragment;

/**
 * Created by thoma on 14/04/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holderview>{


    private List<Recipe> lijst;
    private Context context;
    private Main main;
    private boolean isfavo;
    private FavoriteFragment frag;

    public RecipeAdapter(List<Recipe> lijst, Main x,Context context,boolean isfavo,FavoriteFragment ok) {
        this.lijst = lijst;
        this.context = context;
        this.main = x;
        this.isfavo = isfavo;
        this.frag = ok;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrecipe,parent,false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(final Holderview holder, final int position) {
            holder.v.setText(lijst.get(position).getName());
            holder.z.setText(lijst.get(position).getShortdescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.mainRecipe = lijst.get(position);
                    main.openRecipeDetail(null);
                    System.out.println(main.mainRecipe);
                }
            });

            if(lijst.get(position).favo){
                holder.zz.setBackgroundResource(R.drawable.filled);
            }else{
                holder.zz.setBackgroundResource(R.drawable.notfilled);
            }
            holder.zz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lijst.get(position).favo){
                        holder.zz.setBackgroundResource(R.drawable.notfilled);
                        main.favorites.remove(lijst.get(position).getName());
                        lijst.get(position).favo = false;
                    }else{
                        holder.zz.setBackgroundResource(R.drawable.filled);
                        main.favorites.add(lijst.get(position).getName());
                        lijst.get(position).favo = true;
                    }
                    if(isfavo){
                        FavoriteFragment x = new FavoriteFragment();
                        lijst = x.sorter(lijst);
                        if(lijst.size()==0){
                            frag.addButton();
                        }
                    }else {
                        RecipeSearchFragment x = new RecipeSearchFragment();
                        lijst = x.sorter(lijst);
                    }
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return lijst.size();
    }

    public void setfilter(List<Recipe> listitem){
        lijst = new ArrayList<>();
        lijst.addAll(listitem);
        notifyDataSetChanged();
    }
    class Holderview extends RecyclerView.ViewHolder {
        TextView v;
        TextView z;
        Button zz;
        Button oei;
        Holderview(View itemview){
            super(itemview);
            v = (TextView) itemview.findViewById(R.id.producttitle);
            z = (TextView) itemview.findViewById(R.id.description);
            zz = (Button) itemview.findViewById(R.id.button2);
            oei = (Button) itemview.findViewById(R.id.button);
        }
    }
    public void setFilter(List<Recipe> file){
        lijst = new ArrayList<>();
        lijst.addAll(file);
        notifyDataSetChanged();
    }
}
