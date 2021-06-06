package model.entity.audioWord;

import java.io.InputStream;
import java.sql.Blob;

public class WordEnd {
    private int id;
    private String name;
    private String language;
    private InputStream endStream;
    private Blob endBlob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public InputStream getEndStream() {
        return endStream;
    }

    public void setEndStream(InputStream endStream) {
        this.endStream = endStream;
    }

    public Blob getEndBlob() {
        return endBlob;
    }

    public void setEndBlob(Blob endBlob) {
        this.endBlob = endBlob;
    }
}
