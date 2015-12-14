package com.example.gencha.alarmexample;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Entities.Serial;

/**
 * Created by asd on 13/12/15.
 */
public class MainListAdapter extends ArrayAdapter<Serial> {

    private Context mContext;

    public MainListAdapter(Context context, ArrayList<Serial> serials) {
        super(context, 0, serials);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Serial serial = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.main_item_name_text)).setText(serial.getName());
        try {
            Picasso.with(mContext).load(serial.getImagePath()).into(((ImageView) convertView.findViewById(R.id.main_item_image)));
        } catch (Exception e) {
            ((ImageView) convertView.findViewById(R.id.main_item_image)).setImageResource(R.drawable.ic_launcher);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
