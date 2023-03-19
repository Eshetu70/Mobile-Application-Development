package com.example.midterm.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    String artistName, id, name, releaseDate, artworkUrl100;
    ArrayList<String> genres = new ArrayList<>();
    String genreId, url;
    public Album() {
    }

    public Album(JSONObject json) throws JSONException {

        this.artistName = json.getString("artistName");
        this.id=json.getString("id");
        this.name = json.getString("name");
        this.releaseDate = json.getString("releaseDate");
        this.artworkUrl100 =json.getString("artworkUrl100");

        JSONArray jsonArray = json.getJSONArray("genres");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonGeners = jsonArray.getJSONObject(i);
            this.genreId =jsonGeners.getString("genreId");
            this.name =jsonGeners.getString("name");
            this.url =jsonGeners.getString(" url");


        }
        /*
    {
                "artistName": "Morgan Wallen",
                "id": "1667990565",
                "name": "One Thing At A Time",
                "releaseDate": "2023-03-03",
                "kind": "albums",
                "artistId": "829142092",
                "artistUrl": "https://music.apple.com/us/artist/morgan-wallen/829142092",
                "contentAdvisoryRating": "Explict",
                "artworkUrl100": "https://is3-ssl.mzstatic.com/image/thumb/Music123/v4/86/cc/00/86cc001c-2efc-9ebb-8290-17f4f3ba3e4a/23UMGIM08087.rgb.jpg/100x100bb.jpg",
                "genres": [
                    {
                        "genreId": "6",
                        "name": "Country",
                        "url": "https://itunes.apple.com/us/genre/id6"
                    },
                    {
                        "genreId": "34",
                        "name": "Music",
                        "url": "https://itunes.apple.com/us/genre/id34"
                    }
                ],
                "url": "https://music.apple.com/us/album/one-thing-at-a-time/1667990565"
            },
     */

    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
