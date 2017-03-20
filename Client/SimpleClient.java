package Client;

import Scontroller.Constraints;
import javafx.scene.control.TextField;

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
	private fileEvent fileEvent;
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

           /* strRecv = br.readLine();
            if (strRecv != null) {
                System.out.println("Server says: " + strRecv);
            } else {
                System.err.println("Error in reading from the socket. Exiting main1.");
                cleanUp();
                System.exit(0);
            }*/
        } catch (Exception e) {
            System.err.println("Error in reading from the socket. Exiting main2.");
            cleanUp();
            System.exit(0);
        }
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

        for (int i = 0; i < fileCount; i++)
        { System.out.println("Sending " + files[i].getAbsolutePath());
            sendFile(files[i].getAbsolutePath(), fileCount - i - 1);

        }
    }

    public void sendFile(String path, int index)
    {

        fileEvent = new fileEvent();
        fileEvent.setDestinationDirectory(constraints.getDestinationDir()+"/"+sid);
        fileEvent.setSourceDirectory(getSrcDir());
        File file = new File(path);
        fileEvent.setFilename(file.getName());
        fileEvent.setRemainder(index);
        DataInputStream dataInputStream = null;

        try {
            dataInputStream = new DataInputStream(new FileInputStream(file));
            long len = (int) file.length();
            byte[] fileBytes = new byte[(int) len];
            int read = 0; int numRead = 0;
            while (read < fileBytes.length && (numRead=dataInputStream.read(fileBytes,read,fileBytes.length-read)) >= 0)
            {
                read = read + numRead;
            }
            fileEvent.setFileData(fileBytes);
            fileEvent.setStatus("Success");

        }  catch (IOException e) {
            e.printStackTrace();
            fileEvent.setStatus("Error");
        }

        try {
            objectOutputStream.writeObject(fileEvent);
            //objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*pr.println("Uploaded.");
        pr.flush();*/
    }

    void chat()
    {
			
		while(true)
		{
			System.out.print("Enter a string: ");
			try
			{
				strSend = input.nextLine();
			}
			catch(Exception e)
			{
				continue;
			}
			
			pr.println(strSend);
			pr.flush();
			if(strSend.equals("BYE"))
			{
				System.out.println("Client.Client wishes to terminate the connection. Exiting main.");
				break;
			}
			if(strSend.equals("DL"))
			{
				
				/*try
				{
					strRecv = br.readLine();					//These two lines are used to determine
					int filesize=Integer.parseInt(strRecv);		//the size of the receiving file
					byte[] contents = new byte[10000];

					FileOutputStream fos = new FileOutputStream("capture1.jpg");
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					InputStream is = s.getInputStream();
				
					int bytesRead = 0; 
					int total=0;			//how many bytes read
					
					while(total!=filesize)	//loop is continued until received byte=totalfilesize
					{
						bytesRead=is.read(contents);
						total+=bytesRead;
						bos.write(contents, 0, bytesRead);
                        System.out.println("Q");
                    }
					bos.flush(); 
				}
				catch(Exception e)
				{
					System.err.println("Could not transfer file.");
				}*/
								
			}
			try
			{
				strRecv = br.readLine();
				if(strRecv != null)
				{
					System.out.println("Server says: " + strRecv);
				}
				else
				{
					System.err.println("Error in reading from the socket. Exiting main.");
					break;
				}
			}
			catch(Exception e)
			{
				System.err.println("Error in reading from the socket. Exiting main.");
				break;
			}
			
		}
		
		cleanUp();
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
