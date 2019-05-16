package groep10.chef.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import groep10.chef.R;
import groep10.chef.fragments.RecipeDetailsFragment;

/**
 * Created by thoma on 15/04/2018.
 */

public class CustomAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener{

    private List<Ingredient> dataSet;
    RecipeDetailsFragment ok;
    Context mContext;


    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        Button test;
    }

    public CustomAdapter(List<Ingredient> data, Context context, RecipeDetailsFragment ok) {
        super(context, R.layout.row_layout, data);
        this.dataSet = data;
        this.mContext=context;
        this.ok = ok;

    }

    @Override
    public void onClick(View v) {
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredient dataModel = dataSet.get(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_lan);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.txt_lan1);
            viewHolder.test = (Button) convertView.findViewById(R.id.mybut);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());

        if (dataModel.getHoeveelheid() == 0) {
            viewHolder.txtType.setText("n/a");
        } else {
            String string = String.format("%.2f", (dataModel.getHoeveelheid()* ok.za)) + " " + dataModel.getSmallSoort();
            viewHolder.txtType.setText(string);
        }

        return result;
    }
}