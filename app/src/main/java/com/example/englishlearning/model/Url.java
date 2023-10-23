package com.example.englishlearning.model;

public class Url{
    public String raw;
    public String full;
    public String regular;
    public String small;
    public String thumb;
    public String small_s3;

    public Url() {
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSmall_s3() {
        return small_s3;
    }

    public void setSmall_s3(String small_s3) {
        this.small_s3 = small_s3;
    }
}
