package Client;

import Scontroller.Constraints;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleClient
{
    private Client client;
	private static Socket s = null;
	private static BufferedReader br = null;
	private static PrintWriter pr = null;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Constraints constraints;
	private fileStatus fileStatus;
	private String srcDir="/home/rifat/Downloads/One/";
	private int sid;

    Scanner input = new Scanner(System.in);
    String strSend = null, strRecv = null;
	
	public SimpleClient(String host, String port, String sid, Client client) {
        try {
            this.client=client;
            this.sid= Integer.parseInt(sid);
            //s = new Socket("localhost", 5555);
            s = new Socket(host, Integer.parseInt(port));

            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pr = new PrintWriter(s.getOutputStream());
            objectInputStream=new ObjectInputStream(s.getInputStream());
            objectOutputStream=new ObjectOutputStream(s.getOutputStream());

        } catch (Exception e) {
            System.err.println("Problem in connecting with the server. Exiting main.");
            System.exit(1);
        }


//        pr.println(sid);                                           //send student ID
//        pr.flush();


        try {

            constraints= (Constraints) objectInputStream.readObject();

            client.showConstraints(constraints);
            String idrange=constraints.getIDRange();
            if(idrange!=null)
            {
                boolean a=checkRoll(idrange);
                if(a==false)
                {
                    
                    System.out.println("Student ID out of range....Reconnect");
                    while (true);
                }
            }



        } catch (Exception e) {
            System.err.println("Error in reading from the socket. Exiting main2.");
            cleanUp();
            System.exit(0);
        }
    }

    public boolean checkRoll(String idrange)
    {
        StringTokenizer tokenizer=new StringTokenizer(idrange,";");
        while (tokenizer.hasMoreTokens())
        {
            String t=tokenizer.nextToken();
            if(String.valueOf(sid).equals(t))
            {
                return true;
            }
        }
        System.out.println(idrange+"d++");
        StringTokenizer token=new StringTokenizer(idrange,"-");
        int i=0;String a[]=new String[2];
        while (token.hasMoreTokens())
        {
            if(i==2)break;
            String t=token.nextToken();
            if(i<2)a[i]=t;
            i++;
            System.out.println(t+"++++++"+"");

        }
        if(sid>=Integer.parseInt(a[0]) && sid<Integer.parseInt(a[1]))return true;
        return false;
    }

    public void setSrcDir(String srcDir) {
        this.srcDir = srcDir;
    }

    public String getSrcDir() {
        return srcDir;
    }

    public void syncAll()
    {

        int fileCount;
        File srcDir = new File(getSrcDir());
        if (!srcDir.isDirectory()) {
            System.out.println("Source directory is not valid ..Exiting the client");
            System.exit(0);
        }
        File[] files = srcDir.listFiles();
        fileCount = files.length;
        if (fileCount == 0) {
            System.out.println("Empty directory ..Exiting the client");
            System.exit(0);
        }

        else if(fileCount>constraints.getFileNum())
        {
            System.out.println("More files than constraints provided");
            while(true);
        }

        for (int i = 0; i < fileCount; i++)
        {

            String type=files[i].getName(),ext=null;
            int j=type.lastIndexOf(".");
            if(j>0)
            {
                ext=type.substring(j+1);
            }

            System.out.println(ext+"===="+constraints.getFileType());
            //if(ext.equals(constraints.getFileType()))
                sendFile(files[i].getAbsolutePath(), fileCount - i - 1);

        }
    }

    public void sendFile(String path, int index)
    {

        fileStatus = new fileStatus();
        fileStatus.setDestinationDirectory(constraints.getDestinationDir()+"/"+sid);
        fileStatus.setSourceDirectory(getSrcDir());
        File file = new File(path);
        System.out.println("Sending " + file.getAbsolutePath());
        fileStatus.setFilename(file.getName());
        fileStatus.setRemainder(index);
        fileStatus.setStatus("Success");
        fileStatus.setFileSize(file.length());
        DataInputStream dataInputStream = null;

        try {
            objectOutputStream.writeObject(fileStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            long len = (int) file.length();
            byte[] fileBytes ;
            int read = 0;
            while (read != (int)len)
            {
                int size=512;
                if(read+512<=(int)len)read+=512;
                else
                {
                    size=(int)len-read;
                    read=(int)len;

                }
                fileBytes=new byte[size];
                dataInputStream.read(fileBytes,0,size);
                objectOutputStream.writeObject(fileBytes);
                objectOutputStream.flush();
                try {
                    String t= (String) objectInputStream.readObject();
                    System.out.println(t);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //fileEvent.setFileData(fileBytes);

        }
        catch (IOException e) {
            e.printStackTrace();
            fileStatus.setStatus("Error");
        }




        /*pr.println("Uploaded.");
        pr.flush();*/
    }


	
	private static void cleanUp()
	{
		try
		{
			br.close();
			pr.close();
			s.close();
		}
		catch(Exception e)
		{
		
		}
	}
}
