package com.lukaszse.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\02_PROGRAMOWANIE\\JAVA\\JDBC\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLLUMN_ALBUM_ID = "_id";
    public static final String COLLUMN_ALBUM_NAME = "name";
    public static final String COLLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLLUMN_ARTIST_ID = "_id";
    public static final String COLLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLLUMN_SONG_ID = "_id";
    public static final String COLLUMN_SONG_TRACK = "track";
    public static final String COLLUMN_SONG_TITLE = "title";
    public static final String COLLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;


    private Connection conn;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't open to data base: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Couldn't close to data base: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Artist> querryArtist(int sortOrder) {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if(sortOrder != ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder == ORDER_BY_DESC)
                sb.append("DESC");
            if(sortOrder == ORDER_BY_ASC)
                sb.append("ASC");
        }

        Statement statement = null;
        ResultSet results = null;

        try {

            statement = conn.createStatement();
            results = statement.executeQuery(sb.toString());

            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist();
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }

            return artists;

        } catch (SQLException e) {
            System.out.println("Querry failed " + e.getMessage());
            return null;
        } finally {
            try {
                if(results != null)
                    results.close();
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet: " + e.getMessage());
            }

            try {
                if(statement !=null)
                    statement.close();
            } catch (SQLException e) {
                System.out.println("Error closing Statement: " + e.getMessage());
            }
        }
    }


    public List<String> queryAlbumsForArtists(String artistName, int sortOrder)

}