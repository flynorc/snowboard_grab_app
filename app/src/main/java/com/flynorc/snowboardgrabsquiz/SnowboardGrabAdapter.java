package com.flynorc.snowboardgrabsquiz;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Flynorc on 04-Feb-17.
 */
public class SnowboardGrabAdapter extends ArrayAdapter<SnowboardGrab> {

    public SnowboardGrabAdapter(Activity context, ArrayList<SnowboardGrab> grabs) {
        super(context, 0, grabs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        //if list item view does not exist, inflate a new one from the layout file
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.snowboard_grab_item, parent, false);
        }

        //get the current grab
        SnowboardGrab currentGrab = getItem(position);

        /*
            set the texts/images in the item to match current grab
         */
        TextView grabNameView = (TextView) listItemView.findViewById(R.id.grab_name);
        grabNameView.setText(currentGrab.getName());

        TextView grabDescriptionView = (TextView) listItemView.findViewById(R.id.grab_description);
        grabDescriptionView.setText(currentGrab.getDescription());

        TextView grabOrientationView = (TextView) listItemView.findViewById(R.id.grab_rider_orientation);
        if(currentGrab.riderIsRegular()) {
            grabOrientationView.setText(R.string.regular);
        }
        else {
            grabOrientationView.setText(R.string.goofy);
        }

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.grab_image);
        imageView.setImageResource(currentGrab.getImageResourceId());

        return listItemView;
    }
}
