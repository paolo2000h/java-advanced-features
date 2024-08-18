package org.lab03.Artist;

import com.google.gson.annotations.SerializedName;

public class Artist {
    private static final int female = 0;
    private static final int male = 1;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("gender")
    private String gender;
    @SerializedName("life-span")
    private LifeSpan lifeSpan;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getGender() {
        return gender.equals("female") ? female : male;
    }

    public LifeSpan getLifeSpan() {
        return lifeSpan;
    }
}
