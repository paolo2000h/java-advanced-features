package org.lab03.Artist;

import com.google.gson.annotations.SerializedName;

public class LifeSpan {
    @SerializedName("begin")
    private String begin;

    public String getYear() {
        return begin.substring(0, 4);
    }
}
