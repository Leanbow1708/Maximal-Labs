package com.iniesta.learningbackgroundtask;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyBroadcastReceiver extends BroadcastReceiver {

    PreferenceHelper helper;
    Retrofit retrofit;
    Context mContext;



    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }



    private void buildRetrofit() {



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization","Bearer AAAAAAAAAAAAAAAAAAAAAK9OEwEAAAAAP3CKtIV9YaMMlLNknveozNx06q4%3D3CC9p33bcMT3iRvbP6hX3sth0rHuDbJTgWbw04tu66JZ01oUQ2")
                        .build();

                return chain.proceed(request);
            }
        });


        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/search/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();


    }


    private void updateProfile() {


        helper = new PreferenceHelper(mContext);
        String hash = helper.getHashtag();
        String geocode = helper.getLocation();

        ApiHolder jsonApiHolder = retrofit.create(ApiHolder.class);

        Call<String> call = jsonApiHolder.getTweets("%23"+hash, "recent", geocode);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {


                    try {
                        JSONObject object = new JSONObject(response.body().toString());

                        JSONArray array = object.getJSONArray("statuses");
                        Log.d("responseArray", "onResponse: "+array.length());

                        String mainString = "";
                        for(int i = 0;i<array.length();i++)
                        {

                            JSONObject object1 = array.getJSONObject(i);
                            String text = (i+1)+"\n\n"+object1.getString("text");
                            mainString = mainString + text +"\n\n";



                        }




                        helper.putData(mainString);

                        int reqCode = 1;
                        Intent intent = new Intent(mContext, ShowTweetsActivity.class);
                        showNotification(mContext, "Title", "Total feeds fetched are - "+array.length(), intent, reqCode);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(mContext, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
//        mp=MediaPlayer.create(context, R.raw.alarm);
//        mp.start();
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();




        buildRetrofit();
        updateProfile();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    Log.d("alarm", "run: " + i);
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();


    }


}