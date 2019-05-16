package groep10.chef.fragments;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import groep10.chef.Class.DTO;
import groep10.chef.Class.ExpandableStoreAdapter;
import groep10.chef.Class.ShoppingList;
import groep10.chef.Main;
import groep10.chef.R;

/**
 * Created by Jules on 16/04/2018.
 */

public class StoreCompareFragment extends Fragment {
    public Main main;
    private ShoppingList list;
    private ExpandableStoreAdapter adapter;
    ExpandableListView stores;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        Main ln = (Main) this.getActivity();
        main = ln;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.store_compare, parentVG, false);
        list = new ShoppingList(main.toboodschappenlijst);
        List<DTO> DTOs = list.getCheapestAndAll();
        stores = (ExpandableListView) v.findViewById(R.id.store_comparing);
        adapter = new ExpandableStoreAdapter(getContext(), DTOs, this);
        stores.setAdapter(adapter);
        return v;
    }

}
