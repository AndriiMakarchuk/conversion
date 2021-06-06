package model.entity.audioWord;

import java.sql.Blob;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;

public class AudioWord {
    private int id;
    private String wordString;
    private String extension;
    private String language;
    private int createdBy;

    private InputStream audioWordStream;
    private Blob audioWordBlob;
    private String base64;
    private Boolean isStandard;

    public AudioWord getAudioWord() {
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordString() {
        return wordString;
    }

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public InputStream getAudioWordStream() {
        return audioWordStream;
    }

    public void setAudioWordStream(InputStream audioWordStream) {
        this.audioWordStream = audioWordStream;
    }

    public Blob getAudioWordBlob() {
        return audioWordBlob;
    }

    public void setAudioWordBlob(Blob audioWordBlob) throws SQLException {
        this.audioWordBlob = audioWordBlob;
        if(audioWordBlob != null) {
            this.base64 = Base64.getEncoder().encodeToString(audioWordBlob.getBytes(1, (int) audioWordBlob.length()));
        }
    }

    public String getBase64() {
        return base64;
    }

    public Boolean getStandard() {
        return isStandard;
    }

    public void setStandard(Boolean standard) {
        isStandard = standard;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "AudioWord{" +
                "id=" + id +
                ", wordString='" + wordString + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
