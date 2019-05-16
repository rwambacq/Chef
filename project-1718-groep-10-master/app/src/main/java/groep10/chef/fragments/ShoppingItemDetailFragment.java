package groep10.chef.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import groep10.chef.Class.Ingredient;
import groep10.chef.Main;
import groep10.chef.R;

/**
 * Created by Jules on 17/04/2018.
 */

public class ShoppingItemDetailFragment extends Fragment {
    private int position;
    public Main main;
    EditText edit;
    TextView warning;
    Bundle bundle;
    Ingredient ing;
    View v;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        Main ln = (Main) this.getActivity();
        main = ln;
        position = ln.shoppingPosition;
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.shopping_itemdetail, parentVG, false);
        TextView title = (TextView) v.findViewById(R.id.shopping_detail_title);
        edit = (EditText) v.findViewById(R.id.shopping_detail_edit);
        warning = (TextView) v.findViewById(R.id.shopping_detail_warning);
        TextView format = (TextView) v.findViewById(R.id.shopping_detail_format);
        Button confirm = (Button) v.findViewById(R.id.shopping_item_confirm);
        Button details = (Button) v.findViewById(R.id.shopping_item_ah);
        Button details1 = (Button) v.findViewById(R.id.shopping_item_col);
        Button details2 = (Button) v.findViewById(R.id.shopping_item_del);


        if (bundle!=null){
            ing = (Ingredient) bundle.get("ing");
        }
        else{
            ing = main.toboodschappenlijst.get(position);
        }

        title.setText(ing.getName());
        format.setText(ing.getSmallSoort());

        if (bundle==null) {
            edit.setText(String.valueOf(ing.getHoeveelheid()));
        }
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ing.getAlbertheijnurl()));
                startActivity(browserIntent);
            }
        });
        details1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ing.getColruyt()!=9999) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(("https://colruyt.collectandgo.be/cogo/nl/artikeldetail/" + (ing.getColruyturl().substring(67)))));
                    startActivity(browserIntent);
                }else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(("https://colruyt.collectandgo.be")));
                    startActivity(browserIntent);
                }
            }
        });
        details2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ing.getDelhaizeurl()));
                startActivity(browserIntent);
            }
        });

        TextView tekst1 = (TextView)v.findViewById(R.id.tekst1a);
        if (ing.getAlbertheijn()==9999){
            tekst1.setText("n/a");
        }
        else {
            tekst1.setText(String.valueOf("€" + ing.getAlbertheijn() + "/" + ing.getSoort()));
        }
        TextView tekst2 = (TextView)v.findViewById(R.id.tekst2a);
        if (ing.getColruyt()==9999){
            tekst2.setText("n/a");
        }
        else {
            tekst2.setText(String.valueOf("€" + ing.getColruyt() + "/" + ing.getSoort()));
        }
        TextView tekst3 = (TextView)v.findViewById(R.id.tekst3a);
        if (ing.getDelhaize()==9999){
            tekst3.setText("n/a");
        }
        else {
            tekst3.setText(String.valueOf("€" + ing.getDelhaize() + "/" + ing.getSoort()));
        }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString().equals("")){
                    warning.setText(getString(R.string.shopping_item_warning));
                    warning.setTextColor(Color.RED);
                }else{
                    if (bundle==null) {
                        main.toboodschappenlijst.get(position).setHoeveelheid(Double.parseDouble(edit.getText().toString()));
                        FragmentManager fm = main.getSupportFragmentManager();
                        fm.popBackStack();
                    }
                    else{
                        ing.setHoeveelheid(Double.parseDouble(edit.getText().toString()));
                        main.toboodschappenlijst.add(ing);
                        main.openShoppingList(v);
                        FragmentManager fm = main.getSupportFragmentManager();
                        fm.popBackStack();
                        fm.popBackStack();
                        fm.popBackStack();
                    }
                }
            }
        });
        return v;
    }


}
