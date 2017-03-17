package Scontroller;

/**
 * Created by rifat on 3/17/17.
 */
public class Constraints  {
    private String destinationDir;
    private String fileType;
    private int fileNum;
    private int maxFileSize;
    private String IDRange;
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

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setIDRange(String IDRange) {
        this.IDRange = IDRange;
    }
}
