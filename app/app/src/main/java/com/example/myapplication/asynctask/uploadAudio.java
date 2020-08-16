package com.example.myapplication.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class uploadAudio extends AsyncTask {

    private String id;
    private URL url;
    public Bitmap bitmap;
    public String path;
    public String fileName;
    public File file;
    public FileInputStream fileInputStream;

    @Override
    public Object doInBackground(Object[] objects) {
        //FileInputStream fileInputStream = new FileInputStream( sourceFile );
        String lineEnd = "rn";
        HttpURLConnection urlConnection;
        DataOutputStream dos;
        int maxBufferSize;
        int bytesAvailable;
        int bufferSize;
        URL url = null;
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            url = new URL("http://10.0.2.2:8080/api/quizz/uploadAudio/"+fileName);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            urlConnection.setRequestProperty("Content-Disposition", "attachment;Nom=\" " + fileName+ "\n");



            dos = new DataOutputStream(urlConnection.getOutputStream());
            maxBufferSize = 1*1024*1024;
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name="+fileName+";filename="+ fileName + "" + lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);


            while ( bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int responseCode = urlConnection.getResponseCode();
            System.out.println("REPONSE DE UPLOAD AUDIO: "+responseCode);
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







/*
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = "http://10.0.2.2:8080/api/residents/uploadAudio";
        try {

            //------------------ CLIENT REQUEST
            System.out.println("Le path est : " + this.path);
            System.out.println(new File(".").getAbsolutePath());

           // File file = new File(path);
            byte[] b = new byte[(int) file.length()];
            System.out.println("Le path est : " + file.getAbsolutePath());
            //FileInputStream fileInputStream = new FileInputStream(this.file);

            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            byte[] byteFileArray = new byte[0];
           // byteFileArray = FileUtils.readFileToByteArray(file);
            String base64String = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + path + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            //String encodedString = Base64.encodeToString(dos, Base64.DEFAULT);

            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }*/
    return null;
    }

    /*public void setUrl(String url){
        this.url = url;
    }*/

    public void setId(String id){
        this.id = id;
    }


/*
    @Override
    protected Object doInBackground(Object[] objects) {
        HttpURLConnection httpCon = null;
        try {
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable = 0, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            FileInputStream fileInputStream = new FileInputStream(String.valueOf(uri));

            httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoInput(true); // Allow Inputs
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            JSONObject j = JSONObject();
            /* j.
            dos = new DataOutputStream(httpCon.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\""+ "\n");
            dos.writeBytes(lineEnd);


            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

// read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

// send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }*/



    /*public void getFileNameByUri(Context context, Uri uri){
        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0){
            Cursor cursor = context.getContentResolver().query(uri,null,null,
                    null, null);
            if (cursor.moveToFirst()){
                int column_index =
                    cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
                if(filePathUri == null){
                    fileName = "xxx.png";//load a default Image from server
                }else{
                    fileName = filePathUri.getLastPathSegment().toString();
                }
            }
        }else
            if (uri.getScheme().compareTo("file")==0){
                fileName = filePathUri.getLastPathSegment().toString();
            }else{
                fileName = fileName+"_"+filePathUri.getLastPathSegment();
            }
        this.filename = fileName;
    }*/

   /* private void getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = content(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

        //some extra potentially useful data to help with filtering if necessary
        this.path= filePath;
    }*/

}
