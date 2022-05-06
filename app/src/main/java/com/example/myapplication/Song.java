package com.example.myapplication;

import android.net.Uri;

public class Song {
    long id;
    Uri songUri;
    String trackName;
    int trackDuration;
    long albumId;
    String artist;
    Uri albumartUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackDuration() {
        return trackDuration;
    }

    public Song(long id, Uri songUri, String trackName, int trackDuration, String artist, long albumId, Uri albumartUri) {
        this.id = id;
        this.songUri = songUri;
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.albumId = albumId;
        this.artist = artist;
        this.albumartUri = albumartUri;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getArtist() {
        return artist;
    }

    public Uri getAlbumartUri() {
        return albumartUri;
    }



}
