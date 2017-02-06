package com.flynorc.snowboardgrabsquiz;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Flynorc on 04-Feb-17.
 */

/*
 * TODO THIS should be implemented in a nicer way,
 * i'm guessing the ArrayList should be a static variable of the class that is created only once
 * and used from all (both) activities that need it, but was unable to implement it properly
 *
 */
public class SnowboardGrabs {

    private Context mContext;

    private ArrayList<SnowboardGrab> mSnowboardGrabs = new ArrayList<>();


    public SnowboardGrabs(Context context) {
        mContext = context;

        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.nose_grab_name), mContext.getString(R.string.nose_grab_description), false, R.drawable.nose_goofy));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.tail_grab_name), mContext.getString(R.string.tail_grab_description), true, R.drawable.tail_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.indy_grab_name), mContext.getString(R.string.indy_grab_description), false, R.drawable.indy_goofy));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.mute_grab_name), mContext.getString(R.string.mute_grab_description), true, R.drawable.mute_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.melon_grab_name), mContext.getString(R.string.melon_grab_description), true, R.drawable.melon_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.method_grab_name), mContext.getString(R.string.method_grab_description), true, R.drawable.method_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.chicken_salad_grab_name), mContext.getString(R.string.chicken_salad_grab_description), true, R.drawable.chicken_salad_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.canadian_bacon_grab_name), mContext.getString(R.string.canadian_bacon_grab_description), true, R.drawable.canadian_bacon_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.stalefish_grab_name), mContext.getString(R.string.stalefish_grab_description), false, R.drawable.stalefish_goofy));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.seatbelt_grab_name), mContext.getString(R.string.seatbelt_grab_description), false, R.drawable.seatbelt_goofy));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.rocket_air_grab_name), mContext.getString(R.string.rocket_air_grab_description), true, R.drawable.rocket_air_regular));
        mSnowboardGrabs.add(new SnowboardGrab(mContext.getString(R.string.double_tail_grab_name), mContext.getString(R.string.double_tail_grab_description), true, R.drawable.double_tail_regular));

    }

    public ArrayList<SnowboardGrab> getSnowboardGrabs() {
        return mSnowboardGrabs;
    }


}
