package org.lab03.Release;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Releases {
    @SerializedName("release-groups")
    private List<Release> releases;

    public List<Release> getReleases() {
        return releases;
    }
}
