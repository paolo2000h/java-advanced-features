package org.lab03.Artist;

import org.lab03.Release.Release;
import org.lab03.RestApiClient;

import java.io.IOException;
import java.util.List;

public class ArtistManager {
    private List<Release> releases;
    private Artist artist;

    public ArtistManager(String artistName) throws IOException {
        try {
            RestApiClient restApiClient = new RestApiClient(artistName);
            artist = restApiClient.getArtist(artistName);
            releases = restApiClient.getArtistReleases(artistName).getReleases();
        } catch (ArtistNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getArtistName() {
        return artist.getName();
    }

    public int getNumberOfReleases() {
        return releases.size();
    }

    public String getAlbumTitle(int index) {
        return releases.get(index).getTitle();
    }

    public String getAlbumDate(int index) {
        return releases.get(index).getDate();
    }

    public String getArtistType() {
        return artist.getType();
    }

    public int getArtistGender() {
        if (artist.getType().equals("Person")) {
            return artist.getGender();
        }
        return -1;
    }

    public String getBeginYear() {
        return artist.getLifeSpan().getYear();
    }
}