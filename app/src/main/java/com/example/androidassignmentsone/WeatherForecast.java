package com.example.androidassignmentsone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    protected String selectedCityFromDropDown;
    protected TextView currentTemperature, minTemperature,maxTemperature;
    protected ImageView weatherPictureView;
    protected ProgressBar weatherProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        Bundle dataFromIntent = getIntent().getExtras();
        if (dataFromIntent != null) {
            selectedCityFromDropDown = dataFromIntent.getString("dropDownCityValue");
            Log.i("Selected City Name From DropDown :", selectedCityFromDropDown);
        }

        weatherPictureView = findViewById(R.id.weatherImageView);

        currentTemperature = findViewById(R.id.currentTemperature);
        minTemperature = findViewById(R.id.minTemperature);
        maxTemperature = findViewById(R.id.maxTemperature);

        weatherProgressBar = findViewById(R.id.weatherProgressBar);
        weatherProgressBar.setVisibility(View.VISIBLE);
        new ForeCastQuery().execute();
    }

    class ForeCastQuery extends AsyncTask<Void, Integer, Void> {
        public String minimumTemperatureValue , maximumTemperatureValue, currentTemperatureValue, icon;
        public Bitmap weatherPictureValue;

        @Override
        protected Void doInBackground(Void... Void) {

            try {
                String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + selectedCityFromDropDown + "&appid=3d0b0ddbc8aa2582a0bbbff00361d0ab&mode=xml&units=metric";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();

                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(conn.getInputStream(), null);
                xmlPullParser.nextTag();


                while (xmlPullParser.next() != XmlPullParser.END_TAG){
                    if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String name = xmlPullParser.getName();

                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {
                        xmlPullParser.require(XmlPullParser.START_TAG, null, "temperature");
                        currentTemperatureValue = xmlPullParser.getAttributeValue(null, "value");
                        publishProgress(25);
                        Thread.sleep(1200);

                        minimumTemperatureValue = xmlPullParser.getAttributeValue(null, "min");
                        publishProgress(50);
                        Thread.sleep(1200);

                        maximumTemperatureValue = xmlPullParser.getAttributeValue(null, "max");
                        publishProgress(75);
                        Thread.sleep(1200);

                        xmlPullParser.nextTag();
                        xmlPullParser.require(XmlPullParser.END_TAG, null, "temperature");
                    } else if (name.equals("weather")){
                        xmlPullParser.require(XmlPullParser.START_TAG, null, "weather");
                        icon = xmlPullParser.getAttributeValue(null, "icon");
                        String imageName = icon + ".png";
                        if (fileExistance(imageName)){
                            Log.i("Weather Forecast Image Information ", "Image is already available and reuse it again "+ imageName);

                            FileInputStream fileInputStream = null;
                            try {
                                fileInputStream = openFileInput(imageName);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                             weatherPictureValue = bitmap;
                            }

                        else{
                            Log.i("Weather Forecast Image Information ", "Image is downloaded "+ imageName);
                            weatherPictureValue = HttpUtils.getImage("https://openweathermap.org/img/w/" + imageName);

                            FileOutputStream outputStream = null;
                            try {
                                outputStream = openFileOutput( imageName, Context.MODE_PRIVATE);
                                weatherPictureValue.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        publishProgress(100);
                        Thread.sleep(1200);
                        xmlPullParser.nextTag();
                        xmlPullParser.require(XmlPullParser.END_TAG, null, "weather");
                    }
                else {
                        int depth = 1;
                        while (depth != 0) {
                            switch (xmlPullParser.next()) {
                                case XmlPullParser.END_TAG:
                                    depth--;
                                    break;
                                case XmlPullParser.START_TAG:
                                    depth++;
                                    break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            weatherProgressBar.setVisibility(View.VISIBLE);
            weatherProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            currentTemperature.setText(currentTemperatureValue);
            minTemperature.setText(minimumTemperatureValue);
            maxTemperature.setText(maximumTemperatureValue);
            weatherPictureView.setImageBitmap(weatherPictureValue);
            weatherProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}

class HttpUtils {
    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
