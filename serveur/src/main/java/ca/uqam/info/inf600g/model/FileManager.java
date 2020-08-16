package ca.uqam.info.inf600g.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {

    public FileManager(){

    }


    public String writeToFile(File directory, String fileLocation , InputStream uploadedInputStream){
        if (! directory.exists()){
            //here i create the dirs if they dont exists to store my image
            directory.mkdirs();
        }
        //saving file
        try {
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(fileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {e.printStackTrace();}

        return"File successfully uploaded to : " + fileLocation;
    }
}
