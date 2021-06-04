package model.entity;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Base64;

public class ConversionRecord {
    private int id;
    private String fileName;
    private String conversionSourceType;
    private String conversionDestinationType;
    private Date createdDate;
    private Boolean isConverted;
    private Boolean isError;
    private int createdBy;

    private InputStream sourceFileStream;
    private InputStream destinationFileStream;
    private Blob destinationFileBlob;
    private String destinationFileBase64;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConversionSourceType() {
        return conversionSourceType;
    }

    public void setConversionSourceType(String conversionSourceType) {
        this.conversionSourceType = conversionSourceType;
    }

    public String getConversionDestinationType() {
        return conversionDestinationType;
    }

    public void setConversionDestinationType(String conversionDestinationType) {
        this.conversionDestinationType = conversionDestinationType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getConverted() {
        return isConverted;
    }

    public void setConverted(Boolean converted) {
        isConverted = converted;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public InputStream getSourceFileStream() {
        return sourceFileStream;
    }

    public void setSourceFileStream(InputStream sourceFileStream) {
        this.sourceFileStream = sourceFileStream;
    }

    public InputStream getDestinationFileStream() {
        return destinationFileStream;
    }

    public void setDestinationFileStream(InputStream destinationFileStream) {
        this.destinationFileStream = destinationFileStream;
    }

    public Blob getDestinationFileBlob() {
        return destinationFileBlob;
    }

    public void setDestinationFileBlob(Blob destinationFileBlob) throws SQLException {
        this.destinationFileBlob = destinationFileBlob;
        if(destinationFileBlob != null) {
            this.destinationFileBase64 = Base64.getEncoder().encodeToString(destinationFileBlob.getBytes(1, (int) destinationFileBlob.length()));
        }
    }

    public String getDestinationFileBase64() {
        return destinationFileBase64;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", conversionSourceType='" + conversionSourceType + '\'' +
                ", conversionDestinationType='" + conversionDestinationType + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
