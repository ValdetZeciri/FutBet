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

import static at.htl.futbetdemo.database.Database.getInstance;


public class FutBetModel {
    Database database;


    public FutBetModel() throws SQLException {
         database = getInstance(this);
    }

    public HttpResponse<String> makeApiConnectionWithParamenter(String type, String filter, String value) throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/" +type +"?" + filter +"="+ value)
                .header("x-rapidapi-key", "a2ae3e6da00f126e1e76332c2c1f25f5")
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();

        return response;
    }

    public HttpResponse<String> makeApiConnectionWithType(String type) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/" +type)
                .header("x-rapidapi-key", "a2ae3e6da00f126e1e76332c2c1f25f5")
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();

        return response;
    }

    public void getGroupList(User user){

    }

    public void getLeagueTable(Leagues league) throws UnirestException {
        HttpResponse<String> response = getLeague(league);


        if (league.equals(Leagues.PremierLeague)){
            response = makeApiConnectionWithParamenter("leagues", "id", "39");
        }
        else if (league.equals(Leagues.Ligue1)){
            response = makeApiConnectionWithParamenter("leagues", "id", "61");
        }
        else if (league.equals(Leagues.Bundesliga1)){
            response = makeApiConnectionWithParamenter("leagues", "id", "78");
        }
        else if (league.equals(Leagues.LaLiga)){
            response = makeApiConnectionWithParamenter("leagues", "id", "140");
        }
        else if (league.equals(Leagues.SerieA)){
            response = makeApiConnectionWithParamenter("leagues", "id", "135");
        }

    }

    public String updateUser(User user) throws SQLException {
        return database.updateUser(user);
    }

    public List<Group> getGroupsForUser(int id) throws SQLException {
        return database.getGroupForUser(id);
    }


    public void testMethod() throws IOException, ParseException, JSONException, UnirestException {
        
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/leagues/teams")
                .header("x-rapidapi-key", "87cc7f4e67ba3b53809562372a349d51")
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();


            JSONObject data_obj = new JSONObject(response.getBody());
            JSONArray jsonArray = data_obj.getJSONArray("response");
            JSONObject jsonObject = jsonArray.getJSONObject(1);
            JSONObject team = jsonObject.getJSONObject("team");

            System.out.println(team.getString("name"));

    }

    public Leagues getLeagueForId(int id){
        if (id == 39) {
            return Leagues.PremierLeague;
        }else if (id == 61){
            return Leagues.Ligue1;
        }else if (id == 78){
            return Leagues.Bundesliga1;
        }else if (id == 140){
            return Leagues.LaLiga;
        }else if (id == 135){
            return Leagues.SerieA;
        }
        return null;
    }

    public HttpResponse<String> getLeague(Leagues league) throws UnirestException {
        HttpResponse<String> response = null;

        if (league.equals(Leagues.PremierLeague)){
            response = makeApiConnectionWithParamenter("leagues", "id", "39");
        }
        else if (league.equals(Leagues.Ligue1)){
            response = makeApiConnectionWithParamenter("leagues", "id", "61");
        }
        else if (league.equals(Leagues.Bundesliga1)){
            response = makeApiConnectionWithParamenter("leagues", "id", "78");
        }
        else if (league.equals(Leagues.LaLiga)){
            response = makeApiConnectionWithParamenter("leagues", "id", "140");
        }
        else if (league.equals(Leagues.SerieA)){
            response = makeApiConnectionWithParamenter("leagues", "id", "135");
        }

        return response;
    }

    public int getLeagueId(Leagues league) throws UnirestException {
        int id = 0;

        if (league.equals(Leagues.PremierLeague)){
            id = 39;
        }
        else if (league.equals(Leagues.Ligue1)){
            id =61;
        }
        else if (league.equals(Leagues.Bundesliga1)){
            id =78;
        }
        else if (league.equals(Leagues.LaLiga)){
            id =140;
        }
        else if (league.equals(Leagues.SerieA)){
            id =135;
        }

        return id;
    }

    public String checkForRightPassword(String password){
        String message = "correct";

        if (password == ""){
            message = "Kein Passwort eingegeben";
        }

        else if (password.length()< 8){
            message = "Passwort muss mehr wie acht Zeichen beinhalten";
        }
        else if (password.length() > 15){
            message = "Passwort darf nicht mehr wie 15 Zeichen beinhalten";
        }


        return message;
    }


    public String checkForCorrectLogin(User user) throws SQLException {
        if (database.checkUserLogin(user)==true){
            return "correct";
        }
        return "wrong password or email";
    }

    public int addUser(User user) throws SQLException {
        return database.addUser(user);
    }
    public String getUserWithEmail(String email) throws SQLException {
        return database.getUserWithEmail(email);
    }

    public void getDataFromApi() throws UnirestException, SQLException {
        HttpResponse<String> response = makeApiConnectionWith2Paramenter("standings","league","season",String.valueOf(getLeagueId(Leagues.PremierLeague)),Integer.valueOf("2021"));
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray jsonArray = jsonObject.getJSONArray("response").getJSONObject(0).getJSONObject("league").getJSONArray("standings");

        for(int i = 0; i < 20; i++){
            JSONObject jB = jsonArray.getJSONObject(i);
            System.out.println(jB.getJSONObject("team").get("name"));
            database.createTeam(new Team((String) jB.getJSONObject("team").get("name"), i));
        }
    }

    public HttpResponse<String> makeApiConnectionWith2Paramenter(String type, String filter,String filter2, String value, int value2) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://v3.football.api-sports.io/" +type +"?" + filter +"="+ value + "&" + filter2 + "=" + value2)
                .header("x-rapidapi-key", "87cc7f4e67ba3b53809562372a349d51")
                .header("x-rapidapi-host", "v3.football.api-sports.io")
                .asString();

        return response;
    }

    public int getIdForUser(User user) throws SQLException {
        return database.getIdForUser(user);
    }

    public String checkForRightGroupName(String name) {
        if (name.length() > 15){
            return "Gruppenname darf maximal 15 Zeichen haben";
        }
        return "";
    }

    public int createGroup(Group group, int creatorId) throws SQLException, UnirestException {
        return database.createGroup(group, creatorId);
    }

    public void addUserToGroup(int userId, int groupId) throws SQLException {
        database.addUserToGroup(userId, groupId);
    }

    public List<User> getFriendsForUser(int id) throws SQLException {
        return database.getFriendsForUsr(id);
    }

    public List<User> getFriendsSearched(User user,User friend) throws SQLException {
        return database.getFriendsSearched(user, friend);
    }

    public void sendFriendRequest(int user, int friend) throws SQLException {
        database.sendFriendRequest(user, friend);
    }

    public List<User> getUsersForGroup(Group group) throws SQLException {
        return database.getUsersForGroup(group);
    }

    public int getIdForGroup(Group group) throws SQLException {
        return database.getIdForGroup(group);
    }

    public void addFriend(User user, User friend) throws SQLException {
        database.addFriends(user, friend);
    }
}
