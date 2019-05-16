package groep10.chef.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import groep10.chef.R;
import groep10.chef.fragments.StoreCompareFragment;

/**
 * Created by Jules on 17/04/2018.
 */

public class ExpandableStoreAdapter extends BaseExpandableListAdapter {


    StoreCompareFragment frag;
    private Context context;
    private List<DTO> dataSet;

    public ExpandableStoreAdapter(Context context, List<DTO> dataSet, StoreCompareFragment frag) {
        this.context = context;
        this.dataSet = dataSet;
        this.frag = frag;
    }

    @Override
    public Ingredient getChild(int groupPosition, int childPosition) {
        return frag.main.toboodschappenlijst.get(childPosition);
    }

    @Override
    public DTO getGroup(int groupPosition) {
        return dataSet.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Ingredient ingredient = (Ingredient) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ingredient_price, null);
        }

        TextView txtName = (TextView) view.findViewById(R.id.store_ingredient_name);
        TextView txtPriceKg = (TextView) view.findViewById(R.id.store_ingredient_price_kg);
        TextView txtPrice = (TextView) view.findViewById(R.id.store_ingredient_price);
        txtName.setText(ingredient.getName());

        double priceKg = ingredient.getStoreprice(getGroup(groupPosition).getName());
        double price;
        String priceKgString;
        String priceString;

        if (priceKg == 9999.00){
            txtPriceKg.setText(null);
            txtPrice.setText("n/a");
            txtPrice.setTextColor(Color.RED);
        }else{
            priceKgString = String.format( "%.2f", priceKg ) + "/" + ingredient.getBigSoort();
            txtPriceKg.setText(priceKgString);

            if (ingredient.getSoort().equals("")) {
                price = ingredient.getHoeveelheid()*priceKg;
            } else {
                price= (ingredient.getHoeveelheid()/1000)*priceKg;
            }
            priceString = "€" + String.format( "%.2f", price );
            txtPrice.setText(priceString);

            txtPriceKg.setTextColor(Color.parseColor("#c6c5c5"));
            txtPrice.setTextColor(Color.parseColor("#c6c5c5"));
        }

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return frag.main.toboodschappenlijst.size();
    }

    @Override
    public int getGroupCount() {
        return dataSet.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        DTO dto = (DTO) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.store_layout, null);
        }
        final TextView txtTitle = (TextView) view.findViewById(R.id.store_title);
        final TextView txtPrice = (TextView) view.findViewById(R.id.store_price);
        TextView txtMissing = (TextView) view.findViewById(R.id.store_missing);
        Button viewRoute = (Button) view.findViewById(R.id.store_button);
        viewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder mapsUrlBuilder = Uri.parse("https://www.google.com/maps/search/").buildUpon();
                mapsUrlBuilder.appendQueryParameter("api", "1")
                        .appendQueryParameter("query", txtTitle.getText().toString());
                Uri mapsUrl = mapsUrlBuilder.build();

                Intent webIntent = new Intent(Intent.ACTION_VIEW, mapsUrl);
                if (webIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    view.getContext().startActivity(webIntent);
                }
            }
        });
        txtTitle.setText(dto.getName());
        txtPrice.setText("€" + String.format( "%.2f", dto.price ));
        int missing = 0;
        for (Ingredient ing : frag.main.toboodschappenlijst){
            double price = ing.getStoreprice(getGroup(groupPosition).getName());
            if (price == 9999.00){
                missing++;
            }
        }
        if (missing == 0){
            txtMissing.setText("artikels aanwezig");
            txtMissing.setTextColor(Color.GREEN);
        }else if (missing == 1){
            txtMissing.setText("1 artikel ontbreekt");
            txtMissing.setTextColor(Color.RED);
        }else{
            txtMissing.setText(String.valueOf(missing) + " artikels ontbreken");
            txtMissing.setTextColor(Color.RED);
        }
        return view;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
