package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.config.AppConfig;
import org.example.models.FileInfo;

import java.io.File;

public class DownloadManager {

    @FXML
    private TableView<FileInfo> tableView;  //tableView contains the data of fileInfo

    @FXML
    private TextField urlTextField;

    public int index =0;
    @FXML
    void downloadButtonClicked(ActionEvent event) {

        String url=urlTextField.getText().trim();
        //https://ManishSyal.com/python.exe
        String filename = url.substring(url.lastIndexOf("/") + 1);
        String status="STARTING";
        String action="OPEN";
        String path= AppConfig.DOWNLOAD_PATH+ File.separator+filename;
        FileInfo file= new FileInfo((index + 1) +"", filename,url,status,action,path);  //creating object of FileInfo class and passing all the collected data from user in constructor
        this.index=this.index+1;
        DownloadThread thread=new DownloadThread(file,this);
        this.tableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);
        thread.start();
        this.urlTextField.setText("");

    }

    public void updateUI(FileInfo metafile) {
        System.out.println(metafile);
        FileInfo fileInfo= this.tableView.getItems().get(Integer.parseInt(metafile.getIndex())-1);
        fileInfo.setStatus(metafile.getStatus());
        this.tableView.refresh();
        System.out.println("_________________________________");
    }

    @FXML
    public void initialize(){       //this method is automatically called when we run the project
        System.out.println("View initialized");

        //Sr.No
        TableColumn<FileInfo, String> sn = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(0);
        sn.setCellValueFactory(p->{
           return p.getValue().indexProperty();
        });

        //File Name
        TableColumn<FileInfo, String> filename = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(1);
        filename.setCellValueFactory(p->{
           return p.getValue().nameProperty();
        });

        //URL
        TableColumn<FileInfo, String> url = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(2);
        url.setCellValueFactory(p->{
           return p.getValue().urlProperty();
        });

        //Status
        TableColumn<FileInfo, String> status = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(3);
        status.setCellValueFactory(p->{
           return p.getValue().statusProperty();
        });

        //Action
        TableColumn<FileInfo, String> action = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(4);
        action.setCellValueFactory(p->{
           return p.getValue().actionProperty();
        });


    }
}
