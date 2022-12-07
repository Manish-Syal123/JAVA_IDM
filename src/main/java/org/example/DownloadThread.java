package org.example;

import org.example.models.FileInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadThread extends Thread {

    private FileInfo file; //it will contain all gathered user data that's why created variable of it
    DownloadManager manager;  //for doing changes in UI that's why created variable of it
    public DownloadThread (FileInfo file, DownloadManager manager){
        this.file=file;
        this.manager=manager;
    }

    @Override
    public void run() {

        this.file.setStatus(("DOWNLOADING"));
        this.manager.updateUI(this.file);


        try {
            //download logic
            //Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(this.file.getPath()));

            URL url=new URL(this.file.getUrl());
            URLConnection urlConnection=url.openConnection();
            int fileSize=urlConnection.getContentLength(); //this will give the total length of file
            System.out.println("File Size: "+fileSize);

            int countByte=0;
            double per=0.0;
            double byteSum=0.0;
            BufferedInputStream bufferedInputStream=new BufferedInputStream(url.openStream());

            FileOutputStream fos=new FileOutputStream(this.file.getPath());  //takes the destination path to download
            byte data[]=new byte[1024];
            while(true){

                //The input data from bufferedInputStream is inserted in data array // and .read() =>returns the number of bytes read, or -1 if the end of the stream has been reached.
                countByte=bufferedInputStream.read(data,0,1024);
                    if(countByte==-1){
                        break;
                    }

                fos.write(data,0,countByte);

                byteSum=byteSum+countByte;  //shows currently how much amount of data we have downloaded

                if(fileSize>0){
                    per= (byteSum/fileSize * 100);
                    System.out.println(per);
                    this.file.setPer(per+"");
                    this.manager.updateUI(file);
                }

            }

            fos.close();                    //after reading and writing the data we will close the file
            bufferedInputStream.close();

            this.file.setPer(100+"");

            this.file.setStatus("Success");
        } catch (IOException e) {
            this.file.setStatus("FAILED...!");
            System.out.println("Downloading --=> Error");
            e.printStackTrace();
        }
        this.manager.updateUI(this.file);

    }
}
