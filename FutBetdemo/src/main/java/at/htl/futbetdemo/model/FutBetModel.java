package at.htl.futbetdemo.model;

import at.htl.futbetdemo.database.Database;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.net.httpserver.Headers;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FutBetModel {
    Database database;


    public FutBetModel() throws SQLException {
         database = Database.getInstance();
    }

    public HttpResponse<String> makeApiConnection() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/teams?country=England")
                .header("x-rapidapi-key", "a2ae3e6da00f126e1e76332c2c1f25f5")
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();

        return response;
    }


    public void getDataFromApi() throws IOException, ParseException, JSONException, UnirestException {

        HttpResponse<String> response = makeApiConnection();




            JSONObject data_obj = new JSONObject(response.getBody());
            JSONArray jsonArray = data_obj.getJSONArray("response");
            JSONObject jsonObject = jsonArray.getJSONObject(1);
            JSONObject team = jsonObject.getJSONObject("team");

            System.out.println(team.getString("name"));

    }
}
