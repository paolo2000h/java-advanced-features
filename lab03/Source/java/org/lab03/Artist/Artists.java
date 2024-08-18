package org.lab03.Artist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artists {
    @SerializedName("artists")
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }
}
