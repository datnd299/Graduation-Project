package com.example.signboard2.model.other;

import android.location.Location;

public class LocationCollected{
    private Location location;
    private boolean trusted;
    public LocationCollected(){
        this.location = null;
        this.trusted = true;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }
}
