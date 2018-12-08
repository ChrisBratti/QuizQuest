package com.example.owner.quizquest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VerifyUserAPI extends AsyncTask<String, Void, ArrayList<String>> {
    private DataInterface dataInterface;

    public VerifyUserAPI(DataInterface data){

        this.dataInterface = data;
    }


    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        if(strings.get(0).equals(MainActivity.GET_USER_INFO_URL)){
            dataInterface.sendName(strings.get(1));
        }else{
            dataInterface.sendVerified(strings.get(1));
        }

    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String url = null;
        OkHttpClient client = new OkHttpClient();
        Log.d("Test", strings[0]);
        if(strings[0].equals(MainActivity.VERIFY_URL)){
            String email = strings[1];
            String password = strings[2];
            url = MainActivity.BASE_URL + MainActivity.VERIFY_URL + email
                    + "/" + password + "/";
        }else if(strings[0].equals(MainActivity.GET_USER_INFO_URL)){
            String email = strings[1];
            url = MainActivity.BASE_URL + strings[0] + email;
        }else if(strings[0].equals(MainActivity.GET_CLASS_QUIZZES_URL)){
            String classCode = strings[1];
            url = MainActivity.BASE_URL + strings[0] + classCode;
        }else if(strings[0].equals(MainActivity.GET_CLASS_URL)){
            String email = strings[1];
            url = MainActivity.BASE_URL + strings[0] + email;
        }

        Log.d("Test", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String jsonString = null;
        try {
            Response response = client.newCall(request).execute();
            jsonString = response.body().string();
            Log.d("Test", jsonString);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(strings[0].equals(MainActivity.GET_USER_INFO_URL)){
            Log.d("Test", "Strings is get user info url");
            ArrayList returning = new ArrayList<>();
            returning.add(strings[0]);
            returning.add(getName(jsonString));
            return returning;
        }else{
            Log.d("Test", "Strings is validate user url");
            ArrayList returning = new ArrayList<>();
            returning.add(strings[0]);
            returning.add(validateUser(jsonString));
            return returning;
        }

    }


    private String validateUser(String json){
        if(json == null){return "false";}
        try{
            JSONObject root = new JSONObject(json);
            boolean valid = root.getBoolean("Verified");
            return Boolean.toString(valid);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return "false";
    }

    private String getName(String json){
        String fullName = null;
        if(json == null){return null;}
        try{
            JSONObject root = new JSONObject(json);
            boolean exists = root.getBoolean("exists");
            if(!exists){
                return null;
            }
            String firstName = root.getString("f_name");
            String lastName = root.getString("l_name");
            fullName = firstName + " " + lastName;
        }catch(JSONException e){
            e.printStackTrace();
        }
        Log.d("Test", "getName: " + fullName);
        return fullName;
    }

    public static interface DataInterface{
        public void sendVerified(String valid);
        public void sendName(String name);
    }
}
