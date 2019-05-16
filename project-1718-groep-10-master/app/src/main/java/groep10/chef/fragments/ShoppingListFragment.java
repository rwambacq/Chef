package groep10.chef.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.ShoppingElementAdapter;
import groep10.chef.Main;
import groep10.chef.R;

public class ShoppingListFragment extends Fragment {
    public Main main;
    private ShoppingElementAdapter adapter;
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        Main ln = (Main) this.getActivity();
        main = ln;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping_list, parentVG, false);
        list = (ListView) v.findViewById(R.id.shopping_items);
        this.adapter = new ShoppingElementAdapter(getContext(), main.toboodschappenlijst, ShoppingListFragment.this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                main.shoppingPosition= i;
                main.openShoppingItemDetail(null);
            }
        });
        Button addItem = (Button) v.findViewById(R.id.add_shoppingitem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.openShoppingItemSearch(null);
            }
        });
        Button comparePrices = (Button) v.findViewById(R.id.compare_prices);
        comparePrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.openStoreCompare(null);
            }
        });
        Button clearAll = (Button) v.findViewById(R.id.clear_all);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.toboodschappenlijst.clear();
                adapter.notifyDataSetChanged();
            }
        });
        return v;
    }


    public void removeElement(int position){
        main.toboodschappenlijst.remove(position);
        adapter.notifyDataSetChanged();
    }

}
