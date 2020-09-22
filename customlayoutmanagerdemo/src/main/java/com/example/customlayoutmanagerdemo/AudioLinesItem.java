package com.example.customlayoutmanagerdemo;

public class AudioLinesItem {
    private long id;
    private String lines;

    public AudioLinesItem(long id, String lines) {
        this.id = id;
        this.lines = lines;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }
}
