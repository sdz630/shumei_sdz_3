package com.example.photoshow;

import java.util.List;

public class Httphelper {
    Boolean error;
    List<Result> results;

    public Boolean getError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }
}

class Result {
    private String _id;
    private String createAt;
    private String dese;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private Boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getDese() {
        return dese;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}