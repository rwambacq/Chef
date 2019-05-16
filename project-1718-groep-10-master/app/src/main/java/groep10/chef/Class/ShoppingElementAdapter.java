package groep10.chef.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import groep10.chef.R;
import groep10.chef.fragments.RecipeDetailsFragment;
import groep10.chef.fragments.ShoppingListFragment;

/**
 * Created by Jules on 15/04/2018.
 */

public class ShoppingElementAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener {

    private List<Ingredient> dataSet;
    private ShoppingListFragment frag;
    Context context;


    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        Button delete;
        int position;
    }

    public ShoppingElementAdapter(Context context, List<Ingredient> data, ShoppingListFragment frag) {
        super(context, R.layout.shopping_row, data);
        this.dataSet = data;
        this.frag = frag;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Ingredient dataModel = dataSet.get(position);

        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.shopping_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_slname);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.txt_sltype);
            viewHolder.delete = (Button) convertView.findViewById(R.id.button_sldelete);
            viewHolder.position = position;

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(dataModel.getName());

        if (dataModel.getHoeveelheid() == 0) {
            viewHolder.txtType.setText("n/a");
        } else {
            String string = String.format("%.2f", dataModel.getHoeveelheid()) + " " + dataModel.getSmallSoort();
            viewHolder.txtType.setText(string);
        }

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.removeElement(dataSet.indexOf(dataModel));
            }
        });

        return result;
    }
}

