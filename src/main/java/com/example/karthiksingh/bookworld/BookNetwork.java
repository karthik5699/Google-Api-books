package com.example.karthiksingh.bookworld;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by karthiksingh on 2017-10-04.
 */

public class BookNetwork {
    public static final String LOG_TAG = "BookNetwork";
    private static ArrayList<Book> books ;
    public static Bitmap[] bookimages;


    public BookNetwork() {

    }

    public static ArrayList<Book> extractBooks(String weburl) {
        URL url = createUrl(weburl);//createurl is function defined below

        books = new ArrayList<>();

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        books = extractFeatureFromJson(jsonResponse);

        bookimages = new Bitmap[books.size()];

        for (int i =0;i<books.size();i++){
            bookimages[i]=getBitmapFromURL(books.get(i).getimage());

        }


//        Bitmap[] bitmaps = new Bitmap[10];
//        for(int i=0;i<5;i++){
//            bitmaps[i]=getBitmapFromURL(books.get(i).getimage());
//        }
        return books;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            URI uri = new URI(stringUrl);
            url = uri.toURL();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(100000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);//readfromstream is a function defined below
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());


            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();//stringbuilers are used when strings need to be manipulated or concatinated in steps
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));//inputstream is in the form of byte code so it needs to be converted to readable data
            BufferedReader reader = new BufferedReader(inputStreamReader);//bufferedreader helps to convert byte data to readable data
            String line = reader.readLine();//reading the first line of text
            while (line != null) {
                output.append(line);
                line = reader.readLine();//going to nextline in a loop until the lines are completed
            }
        }
        return output.toString();//converting stringbuilder to a string
    }

    public static ArrayList<Book> extractFeatureFromJson(String json) {

        try {
            JSONObject booksJson = new JSONObject(json);
            JSONArray booksArray = booksJson.getJSONArray("items");


            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject booksProperties = booksArray.getJSONObject(i);
                JSONObject volumeinfo = booksProperties.getJSONObject("volumeInfo");
                System.out.println(booksArray.length());
                String title = volumeinfo.getString("title");

                String subtitle;

                if(volumeinfo.has("subtitle")) {

                    subtitle = volumeinfo.getString("subtitle");
                }
                else {
                    subtitle = " A brief description about the book " + title;
                }



                String authors;

                if(volumeinfo.has("authors")) {
                    JSONArray AuthorArray = volumeinfo.getJSONArray("authors");


                    StringBuilder authorBuilder = new StringBuilder();

                    if (AuthorArray.length() <= 1) {


                        authorBuilder.append(AuthorArray.getString(0));


                        authors = authorBuilder.toString();
                    } else {
                        authors = AuthorArray.getString(0) + " , " + AuthorArray.getString(1);
                    }
                }
                else {
                    authors="No information";

                }


                String publishedDate;

                if(volumeinfo.has("publishedDate")) {

                   publishedDate = volumeinfo.getString("publishedDate");
                }
                else {
                    publishedDate="No info";
                }

                String description;
                if(volumeinfo.has("description")) {

                     description = volumeinfo.getString("description");
                }
                else{
                    description="No description Available";
                }

                int pageCount;
                if(volumeinfo.has("pageCount")) {
                   pageCount = volumeinfo.getInt("pageCount");
                }
                else {
                    pageCount = 404;
                }
                Log.d(TAG, "extractFeatureFromJson: 5");
                String smallimage;
                String image;
                if(volumeinfo.has("imageLinks")) {

                    JSONObject imageLinks = volumeinfo.getJSONObject("imageLinks");
                     smallimage = imageLinks.getString("smallThumbnail");
                     image = imageLinks.getString("thumbnail");
                }
                else {
                    smallimage=" ";
                    image=" ";
                }

                String previewLink = volumeinfo.getString("previewLink");


                JSONObject accessInfo = booksProperties.getJSONObject("accessInfo");
                JSONObject pdf = accessInfo.getJSONObject("pdf");

                Boolean pdfisAvailable = pdf.getBoolean("isAvailable");
                String downloadlink;
                if (pdfisAvailable&&pdf.has("acsTokenLink")) {
                    downloadlink = pdf.getString("acsTokenLink");
                    // enable webview
                } else {
                    downloadlink = "";
                }
                String webReaderlink = accessInfo.getString("webReaderLink");

                books.add(new Book(title, subtitle, authors, publishedDate, description, pageCount, smallimage, image, previewLink, pdfisAvailable, downloadlink, webReaderlink));
            }



        } catch (JSONException e) {
            Log.e(TAG, "extractFeatureFromJson: ");
        }
        return books;


    }
    public static Bitmap getBitmapFromURL(String mimage) {
        try {

            URL url = new URL(mimage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(150000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public static Bitmap[] imageconverter(){

        return bookimages;
    }
}

