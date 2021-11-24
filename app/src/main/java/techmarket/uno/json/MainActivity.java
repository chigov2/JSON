package techmarket.uno.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=London&appid=907ba720e9626eeeb8194ca99b8439bb"; //Не забудьте ввести свой APPID после '='

    private EditText editTextCity;
    private TextView textViewWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadJSON task = new DownloadJSON();
        task.execute("https://api.openweathermap.org/data/2.5/weather?q=London&appid=907ba720e9626eeeb8194ca99b8439bb");
        //task.onPostExecute("http://api.openweathermap.org/data/2.5/weather?id=2172797&appid=907ba720e9626eeeb8194ca99b8439bb");
    }

    private static class DownloadJSON extends AsyncTask<String, Void, String>{
        //String line;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                //open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //получаем поток ввода
                InputStream inputStream = urlConnection.getInputStream();
                //create readers
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                //begin read info
                String line = reader.readLine();
                //пока строка не станет пустой
                while (line != null){
                    //in StringBuilder add line
                    result.append(line);
                    //and read next line
                    line = reader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override// переопределяем метод для получения данных в этом классе
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("test",s);
        }
    }
}
