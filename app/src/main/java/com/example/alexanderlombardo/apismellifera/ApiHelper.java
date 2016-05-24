package com.example.alexanderlombardo.apismellifera;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ApiHelper {
    private static ApiHelper instance;
    private static ApiResponseHandler responseHandler;

    private ApiHelper(){
    }

    public static ApiHelper getInstance(ApiResponseHandler handler){
        responseHandler = handler;
        if(instance == null) {
            instance = new ApiHelper();
        }
        return instance;
    }

    public void doRequest(String varSearch) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=05e79061d9a589d9c0f9d35788e8e2c8&format=json&text="+varSearch+"&nojsoncallback=1",
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                        String id, secret, server, farm, address = null;

                        try {
                            JSONObject jsonObject = responseBody.getJSONObject("photos");
                            JSONArray jArray = jsonObject.getJSONArray("photo");
                            JSONObject jObject = jArray.getJSONObject(0);

                            id = jObject.getString("id");
                            secret = jObject.getString("secret");
                            server = jObject.getString("server");
                            farm = jObject.getString("farm");
                            address = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg";
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        responseHandler.handleResponse(address);
                    }
        });
    }

    public interface ApiResponseHandler{
        void handleResponse(String address);
    }
}
