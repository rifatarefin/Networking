package Scontroller;

import java.io.Serializable;

/**
 * Created by rifat on 3/17/17.
 */
public class Constraints implements Serializable {
    private String destinationDir;
    private String sourceDir;
    private String fileType;
    private int fileNum;
    private long maxFileSize;
    private String IDRange;
    private String syncDelay;
    private String fileName;
    private byte[] fileData;
    private String status;
    private int remainder;





    public void setDestinationDir(String destinationDir)
    {
        this.destinationDir=destinationDir;
    }


    public void setFileType(String fileType)
    {
        this.fileType=fileType;
    }

    public void setFileNum(int fileNum)
    {
        this.fileNum=fileNum;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setIDRange(String IDRange) {
        this.IDRange = IDRange;
    }

    public void setSyncDelay(String syncDelay) {
        this.syncDelay = syncDelay;
    }

    public int getFileNum() {
        return fileNum;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public String getDestinationDir() {
        return destinationDir;
    }


    public String getFileType() {
        return fileType;
    }

    public String getIDRange() {
        return IDRange;
    }

    public String getSyncDelay() {
        return syncDelay;
    }

}
