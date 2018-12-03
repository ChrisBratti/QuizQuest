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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VerifyUserAPI extends AsyncTask<String, Void, Boolean> {
    private DataInterface dataInterface;

    public VerifyUserAPI(DataInterface data){

        this.dataInterface = data;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        dataInterface.sendVerified(aBoolean);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String password = strings[1];
        OkHttpClient client = new OkHttpClient();
        String url = MainActivity.BASE_URL + MainActivity.VERIFY_URL + email
                + "/" + password + "/";
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
        return validateUser(jsonString);
    }


    private boolean validateUser(String json){
        if(json == null){return false;}
        try{
            JSONObject root = new JSONObject(json);
            boolean valid = root.getBoolean("Verified");
            return valid;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public static interface DataInterface{
        public void sendVerified(boolean valid);
    }
}
