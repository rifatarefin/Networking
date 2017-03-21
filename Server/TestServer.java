package Server;

import Client.fileStatus;
import Scontroller.Constraints;

import java.io.*;
import java.net.*;


public class TestServer
{
	public static int workerThreadCount = 0;
	
	public TestServer(Constraints constraints)
	{
		int id = 1;
		
		try
		{
			ServerSocket ss = new ServerSocket(5555);
			System.out.println("Server has been started successfully.");
			
			while(true)
			{
				Socket s = ss.accept();		//TCP Connection
				WorkerThread wt = new WorkerThread(s, id, constraints);
				Thread t = new Thread(wt);
				t.start();
				workerThreadCount++;
				System.out.println("Client.Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);
				id++;
			}
		}
		catch(Exception e)
		{
			System.err.println("Problem in ServerSocket operation. Exiting main.");
		}
	}
}

class WorkerThread implements Runnable
{
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private FileOutputStream fileOutputStream;
	
	private int id = 0;
	private Constraints constraints;
	private fileStatus fileStatus;
	private File dstFile;
	
	public WorkerThread(Socket s, int id, Constraints constraints)
	{
		this.socket = s;
		this.constraints=constraints;
		try
		{
			this.is = this.socket.getInputStream();
			this.os = this.socket.getOutputStream();
			this.objectOutputStream=new ObjectOutputStream(this.socket.getOutputStream());
			this.objectInputStream=new ObjectInputStream(this.socket.getInputStream());
		}
		catch(Exception e)
		{
			System.err.println("Sorry. Cannot manage client [" + id + "] properly.");
		}
		
		this.id = id;
	}
	
	public void run()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
		PrintWriter pr = new PrintWriter(this.os);

//        try {
//            System.out.println(br.readLine());            //read studentID
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            objectOutputStream.writeObject(constraints);
            objectOutputStream.flush();
            //write constraints
        } catch (IOException e) {
            e.printStackTrace();
        }

//        pr.println("Your id is: " + this.id);
//		pr.flush();
		
		String strRecv;
		String[] a=new String[2];
		
		while(true)
		{
			/*try
			{
				*//*if( (str = br.readLine()) != null )
				{
					if(str.equals("BYE"))
					{
						System.out.println("[" + id + "] says: BYE. Worker thread will terminate now.");
						break; // terminate the loop; it will terminate the thread also
					}
					else if(str.equals("DL"))
					{
						try
						{
							File file = new File("capture.jpg");
							FileInputStream fis = new FileInputStream(file);
							BufferedInputStream bis = new BufferedInputStream(fis);
							OutputStream os = socket.getOutputStream();
							byte[] contents;
							long fileLength = file.length();
							pr.println(String.valueOf(fileLength));		//These two lines are used
							pr.flush();									//to send the file size in bytes.
							
							long current = 0;
							 
							long start = System.nanoTime();
							while(current!=fileLength){ 
								int size = 10000;
								if(fileLength - current >= size)
									current += size;    
								else{ 
									size = (int)(fileLength - current); 
									current = fileLength;
								} 
								contents = new byte[size]; 
								bis.read(contents, 0, size); 
								os.write(contents);
								System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
							}   
							os.flush();
							pr.println("ok");
							System.out.println("File sent successfully!");
						}
						catch(Exception e)
						{
							System.err.println("Could not transfer file.");
						}
						pr.println("Downloaded.");
						pr.flush();

					}
					else
					{
						System.out.println("[" + id + "] says: " + str);
						pr.println("Got it. You sent \"" + str + "\"");
						pr.flush();
					}
				}
				else
				{
					System.out.println("[" + id + "] terminated connection. Worker thread will terminate now.");
					break;
				}*//*
			}*/


            try {
                if(socket.isConnected()) fileStatus = (fileStatus) objectInputStream.readObject();
                if (fileStatus.getStatus().equalsIgnoreCase("Error"))
                {
                    System.out.println("Error occurred ..with file" + fileStatus.getFilename() + "at sending end ..");

                }
                String outputFile = fileStatus.getDestinationDirectory() + "/"+ fileStatus.getFilename();
                if (!new File(fileStatus.getDestinationDirectory()).exists()) {
                    new File(fileStatus.getDestinationDirectory()).mkdirs();
                }
                dstFile = new File(outputFile);
                fileOutputStream = new FileOutputStream(dstFile);
				int total=0;			//how many bytes read
				int filesize=(int)fileStatus.getFileSize();
				byte[] contents;
				while(total!=filesize)	//loop is continued until received byte=totalfilesize
				{
					contents=(byte[])objectInputStream.readObject();
                    objectOutputStream.writeObject("packet received by server");
                    objectOutputStream.flush();
					total+=contents.length;
					fileOutputStream.write(contents);

				}
				fileOutputStream.flush();
				fileOutputStream.close();
                System.out.println("Output file : " + outputFile + " is successfully saved ");
                if (fileStatus.getRemainder() == 0) {
                    System.out.println("Whole directory is copied...");
                    //System.exit(0);

                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
		}
		
		try
		{
			this.is.close();
			this.os.close();
			this.socket.close();
		}
		catch(Exception e)
		{
		
		}
		
		TestServer.workerThreadCount--;
		System.out.println("Client.Client [" + id + "] is now terminating. No. of worker threads = "
				+ TestServer.workerThreadCount);
	}
}
