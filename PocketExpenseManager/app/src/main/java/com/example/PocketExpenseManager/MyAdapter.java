package com.example.PocketExpenseManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/4/2016.
 */
public class MyAdapter extends ArrayAdapter<Expense> {
    Context mycontext;
    int myresource;
    List<Expense> expenselist;
    String useremail;
    SharedPreferences mPrefs;

    public MyAdapter(Context context, int resource, List<Expense> objects,String useremail) {
        super(context, resource, objects);
        this.mycontext=context;
        this.expenselist=objects;
        this.myresource=resource;
        this.useremail=useremail;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(myresource,parent,false);
        }

        Expense expense=expenselist.get(position);
        TextView name=(TextView) convertView.findViewById(R.id.name);
        TextView amount=(TextView) convertView.findViewById(R.id.amount);
        ImageView recimg=(ImageView) convertView.findViewById(R.id.imageView7);
        name.setText(" "+expense.expensename.toString());
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mycontext);
        String pcurrencysymbol  = mPrefs.getString(useremail,"");
        amount.setText(expense.amount.toString() +" "+ expense.expensecurrency.toString());
        recimg.setImageURI(Uri.parse(expense.recptimg));
        convertView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        return convertView;
    }

}
