package groep10.chef.database;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import groep10.chef.Class.Ingredient;
import groep10.chef.Class.Recipe;

/**
 * Created by thoma on 13/04/2018.
 */

public class CustomAdapter2 extends BaseAdapter{
    private Context mContext;
    private List<Recipe> lijst;

    public CustomAdapter2(Context mContext, List<Recipe> lijst) {
        this.mContext = mContext;
        this.lijst = lijst;
    }

    @Override
    public int getCount() {
        return lijst.size();
    }

    @Override
    public Object getItem(int i) {
        return lijst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
