package com.example.alexanderlombardo.apismellifera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String varSearch;
    final int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        varSearch = "people";

    }

    public void doSearch(View view) {
        EditText criteria = (EditText)findViewById(R.id.search_field);
        varSearch = criteria.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=05e79061d9a589d9c0f9d35788e8e2c8&format=json&text="+varSearch+"&nojsoncallback=1",
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                        String id, secret, server, farm;

                        try {
                            ImageView imageView = (ImageView)findViewById(R.id.image_view_main);
                            JSONObject jsonObject = responseBody.getJSONObject("photos");
                            JSONArray jArray = jsonObject.getJSONArray("photo");
                            JSONObject jObject = jArray.getJSONObject(index);
                            id = jObject.getString("id");
                            secret = jObject.getString("secret");
                            server = jObject.getString("server");
                            farm = jObject.getString("farm");
                            Picasso.with(MainActivity.this).load("https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg").into(imageView);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
