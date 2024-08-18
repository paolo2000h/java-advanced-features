package org.lab03.Release;

import com.google.gson.annotations.SerializedName;

public class Release {
    @SerializedName("first-release-date")
    private String date;
    @SerializedName("title")
    private String title;

    public String getDate() {
        return !date.equals("") ? date.substring(0, 4) : "";
    }

    public String getTitle() {
        return title;
    }
}

