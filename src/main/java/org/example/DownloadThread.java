package org.example;

import org.example.models.FileInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
            Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(this.file.getPath()));
            this.file.setStatus("Success");
        } catch (IOException e) {
            this.file.setStatus("FAILED...!");
            System.out.println("Downloading --=> Error");
            e.printStackTrace();
        }
        this.manager.updateUI(this.file);

    }
}
