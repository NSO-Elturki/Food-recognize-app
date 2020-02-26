package com.example.bigprojectwhitelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WhiteListAdapterClass extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<ListInfo> listStorage;
    private ArrayList<Integer> selectedApps;

    private int a;


    public WhiteListAdapterClass(Context context, List<ListInfo> customizedListView) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        selectedApps = new ArrayList<Integer>();

    }


    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.icon_layout, parent, false);
            CheckBox checkBox = convertView.findViewById(R.id.app_checkbox);
            checkBox.setTag(position);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //  int selectedApp = (int)buttonView.getTag();
                    // countSelectedItem(selectedApp);
                    a = (int)buttonView.getTag();
                    indexNr(a);

                    selectedApps.add(a);


                }
            });


            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.app_name);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());

        return convertView;
    }

    public void indexNr(int count){


        String i = String.valueOf(count);
        Log.e("TAG", i);

    }
    public ArrayList<Integer> countSelectedItem(){


        return  this.selectedApps;
    }




    static class ViewHolder {

        TextView textInListView;
        ImageView imageInListView;
    }

}

