package org.lab03;

import com.google.common.escape.UnicodeEscaper;
import com.google.common.net.PercentEscaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import org.lab03.Artist.Artist;
import org.lab03.Artist.ArtistNotFoundException;
import org.lab03.Artist.Artists;
import org.lab03.Release.Releases;

public class RestApiClient {
    public RestApiClient(String artistName) throws ArtistNotFoundException {
        if (getArtistId(artistName).equals("")) {
            throw new ArtistNotFoundException("No artist named: '" + artistName + "' has been found");
        }
    }

    private URL encodeArtistURL(String artistName) {
        try {
            UnicodeEscaper basicEscaper = new PercentEscaper("-", false);
            String encoded = basicEscaper.escape(artistName);
            String fullURL = "https://musicbrainz.org/ws/2/artist/?query=artist:" + encoded + "&limit=1&fmt=json";
            return new URL(fullURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getArtistRecordingsURL(String artistName) {
        return "https://musicbrainz.org/ws/2/release-group?artist=" + getArtistId(artistName) + "&limit=100&type=album|ep&fmt=json";
    }

    private String getArtistId(String artistName) {
        URL url = encodeArtistURL(artistName);
        try {
            assert url != null;
            String content = getJson(url);

            Gson gson = new Gson();
            Artists artistSearchResult = gson.fromJson(content, Artists.class);
            if (artistSearchResult.getArtists().size() != 0) {
                return artistSearchResult.getArtists().get(0).getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Artist getArtist(String artistName) {
        URL url = encodeArtistURL(artistName);
        try {
            assert url != null;
            String content = getJson(url);

            Gson gson = new Gson();
            Artists artistSearchResult = gson.fromJson(content, Artists.class);
            return artistSearchResult.getArtists().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Releases getArtistReleases(String artistName) throws IOException {
        String recordings = getArtistRecordingsURL(artistName);
        URL url = new URL(recordings);

        String content = getJson(url);

        Gson gson = new Gson();
        return gson.fromJson(content, Releases.class);
    }

    private String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }
}
