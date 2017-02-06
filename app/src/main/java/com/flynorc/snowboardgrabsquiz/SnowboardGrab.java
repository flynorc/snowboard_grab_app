package com.flynorc.snowboardgrabsquiz;

import java.util.ArrayList;

/**
 * Created by Flynorc on 04-Feb-17.
 */

public class SnowboardGrab {

    private String mName;
    private String mDescription;
    private boolean mRiderRegular;
    private int mImageResourceId;

    public SnowboardGrab (String name, String description, boolean riderRegular, int imageResourceId) {
        mName = name;
        mDescription = description;
        mRiderRegular = riderRegular;
        mImageResourceId = imageResourceId;
    }

    public String getName () {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean riderIsRegular() {
        return mRiderRegular;
    }

    /*
     * debug
     */
    @Override
    public String toString() {
        return "SnowboardGrab{" +
                "mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mRiderRegular=" + mRiderRegular +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
