package groep10.chef.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groep10.chef.Class.CustomAdapter;
import groep10.chef.Class.Ingredient;
import groep10.chef.Main;
import groep10.chef.R;

public class RecipeDetailsFragment extends Fragment {


    private TabHost mTabHost;
    private TextView z;
    boolean[] checkeditems;
    public int za = 1;
    String[] listitems;
    float lastX;
    ArrayList<Integer> musertitems = new ArrayList<>();

    View rootView;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup parentVG,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.test, parentVG, false);
        mTabHost = (TabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup();
        TabHost.TabSpec spec = mTabHost.newTabSpec("tag");
        spec.setIndicator("recept");
        spec.setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                View rootViewz = inflater.inflate(R.layout.recipedetail, parentVG, false);
                Main ln = (Main) getActivity();
                z = (TextView) rootViewz.findViewById(R.id.recipeButtonExpl);
                TextView alo = (TextView) rootViewz.findViewById(R.id.recipeButtonExplz);
                z.setText(ln.mainRecipe.getName());
                TextView test = (TextView) rootViewz.findViewById(R.id.explain2);
                test.setText(ln.mainRecipe.getPreparation());
                alo.setText(ln.mainRecipe.getShortdescription());
                new DownloadImageTask((ImageView) rootViewz.findViewById(R.id.testj))
                        .execute(ln.mainRecipe.getImage());
                mTabHost.getTabWidget().getChildAt(0).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                return (rootViewz);
            }
        });
        mTabHost.addTab(spec);
        spec = mTabHost.newTabSpec("tag1");
        spec.setIndicator("ingredienten");
        spec.setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                View rootViewz = inflater.inflate(R.layout.tab2, parentVG, false);
                ListView z = (ListView) rootViewz.findViewById(R.id.checkable_list);
                final TextView testjee = (TextView) rootViewz.findViewById(R.id.edit);

                z.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                final Main ln = (Main) getActivity();
                ln.mainRecipe.fillup(ln.ingredients);
                final CustomAdapter xz = new CustomAdapter(ln.mainRecipe.getRight(),getContext(),RecipeDetailsFragment.this);
                z.setAdapter(xz);

                mTabHost.getTabWidget().getChildAt(1).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                final TextView testjeee = (TextView) rootViewz.findViewById(R.id.edit);
                Button plus = (Button) rootViewz.findViewById(R.id.plus);
                Button min = (Button) rootViewz.findViewById(R.id.min);
                min.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int z = Integer.parseInt((String)testjeee.getText());
                        if(z>1) {
                            testjeee.setText(String.valueOf(z-1));
                            za = za -1;
                            xz.notifyDataSetChanged();
                        }

                    }
                });
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int z = Integer.parseInt((String)testjeee.getText());
                        testjeee.setText(String.valueOf(z+1));
                        za = za +1;
                        xz.notifyDataSetChanged();

                    }
                });
                final Button x = (Button) rootViewz.findViewById(R.id.mybut);

                x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder mbuild = new AlertDialog.Builder(getActivity());
                        mbuild.setTitle("Voeg toe aan lijst");

                        ArrayList<String> xo = new ArrayList<>();
                        listitems = new String[ln.mainRecipe.getRight().size()];
                        for (Ingredient x : ln.mainRecipe.getRight()){
                            xo.add(x.getName());
                        }
                        listitems = xo.toArray(listitems);
                        checkeditems = new boolean[listitems.length];
                        for (int iz = 0; iz < checkeditems.length;iz++){
                            checkeditems[iz] = true;
                        }

                        for (int i = 0;i<(ln.mainRecipe.getRight().size());i++){
                            musertitems.add(i);
                        }

                        mbuild.setMultiChoiceItems(listitems, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                if(b){
                                    if(!musertitems.contains(i)){
                                        musertitems.add(i);
                                    }else{
                                        musertitems.remove(i);
                                    }
                                }else{
                                    if(musertitems.contains(i)){
                                        musertitems.remove(i);
                                    }
                                }
                            }
                        });
                        mbuild.setCancelable(true);
                        mbuild.setPositiveButton("Voeg toe", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String clicked = "";
                                Boolean in = false;
                                ArrayList<String> xo = new ArrayList<>();
                                for (int iz = 0; iz< musertitems.size();iz++){
                                        xo.add(listitems[musertitems.get(iz)]);
                                }
                                int z = Integer.parseInt((String)testjeee.getText());
                                double current;
                                for (String y : xo){
                                    for (Ingredient zzz : ln.mainRecipe.getRight()){
                                        if (y.equals(zzz.getName())){
                                            for (Ingredient ing : ln.toboodschappenlijst){
                                                if (y.equals(ing.getName())){
                                                    current = ing.getHoeveelheid();
                                                    ing.setHoeveelheid(current+(zzz.getHoeveelheid() * z));
                                                    in = true;
                                                }
                                            }
                                            if (!in) {
                                                zzz.setHoeveelheid(zzz.getHoeveelheid() * z);
                                                ln.toboodschappenlijst.add(zzz);
                                            }
                                            in = false;
                                        }
                                    }
                                }
                                musertitems.clear();
                                x.setText("ga naar lijst");
                                x.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ln.openShoppingList(null);
                                    }
                                });
                                Toast.makeText(getActivity(), "Toegevoegd!",
                                        Toast.LENGTH_LONG).show();

                            }
                        });
                        mbuild.setNeutralButton("Annuleer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        mbuild.show();
                    }
                });
                return (rootViewz);
            }
        });
        mTabHost.addTab(spec);
        return rootView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
