package com.example.drizzle16.moviecard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drizzle16 on 2016-08-31.
 */
public class NowPlayingItem{

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResult(ArrayList<Results> results) {
        this.results = results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    int page;
    List<Results> results = new ArrayList<>();
    Dates dates;
    int total_pages;
    int total_results;

    @Override
    public String toString() {
        return "NowPlayingItem{" +
                "page=" + page +
                ", results=" + results +
                ", dates=" + dates +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}



