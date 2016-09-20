package com.example.drizzle16.moviecard.adapter;

/**
 * Created by drizzle16 on 2016-09-13.
 */
public class ListViewItem {
    private String posterPath;
    private String title;
    private String genre;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
