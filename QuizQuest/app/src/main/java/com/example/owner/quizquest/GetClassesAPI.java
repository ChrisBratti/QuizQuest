package com.example.owner.quizquest;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetClassesAPI extends AsyncTask<String, Void, ArrayList<Class>>{
    private DataInterface dataInterface;

    public GetClassesAPI(DataInterface dataInterface){
        this.dataInterface = dataInterface;
    }

    @Override
    protected void onPostExecute(ArrayList<Class> classes) {
        super.onPostExecute(classes);
        dataInterface.sendClasses(classes);
    }

    @Override
    protected ArrayList<Class> doInBackground(String... strings) {

        String url = MainActivity.BASE_URL + MainActivity.GET_STUDENT_CLASSES_URL + strings[0];
        Log.d("Test", url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        String jsonString = null;
        try {
            Response response = client.newCall(request).execute();
            jsonString = response.body().string();
            Log.d("Test", jsonString);
        }catch(IOException e) {
            e.printStackTrace();
        }


        return parseClasses(jsonString);
    }

    private ArrayList<Class> parseClasses(String json){
        if(json == null){
            return null;
        }

        ArrayList<Class> classes = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(json);
            if(!root.getBoolean("exists")){
                return null;
            }

            JSONArray classInfo = root.getJSONArray("classes_info");
            for(int i = 0; i < classInfo.length(); i++){
                JSONArray info = classInfo.getJSONArray(i);
                Log.d("Test", info.toString());
                int number = info.getInt(0);
                String name = info.getString(1);
                String teacher = info.getString(2);
                classes.add(new Class(name, teacher, number));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }


        return classes;
    }

    public static interface DataInterface{
        public void sendClasses(ArrayList<Class> classes);
    }
}
