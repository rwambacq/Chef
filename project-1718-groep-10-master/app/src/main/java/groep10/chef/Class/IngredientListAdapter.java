package groep10.chef.Class;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import groep10.chef.R;
import groep10.chef.fragments.IngredientSearchFragment;
import groep10.chef.fragments.IngredientSearchFragment2;
import groep10.chef.fragments.RecipeDetailsFragment;

/**
 * Created by thoma on 15/04/2018.
 */

public class IngredientListAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener{

    private List<Ingredient> dataSet;
    Context mContext;
    IngredientSearchFragment frag;

    private static class ViewHolder {
        TextView txtName;
        Button knop;
    }

    public IngredientListAdapter(List<Ingredient> data, Context context, IngredientSearchFragment frag) {
        super(context, R.layout.row_layout, data);
        this.dataSet = data;
        this.frag = frag;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Ingredient dataModel = dataSet.get(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout_ingr, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_ingr);
            viewHolder.knop = convertView.findViewById(R.id.knop);
            viewHolder.knop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frag.removeElement(position);
                }
            });

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());

        return result;
    }
}