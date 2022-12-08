package com.example.sebastiensandroidlabs.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sebastiensandroidlabs.R;
import com.example.sebastiensandroidlabs.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Sebastien Ramsay
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    protected  RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.getForecastButton.setOnClickListener(click ->{
            String url = null;


            try {
                url = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(binding.cityEditText.getText().toString(), "UTF-8")
                        + "&appid=a594961d8ddf7a96b790d4c7eb89e522&Units=metric";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {
                        try {
                            JSONObject coord = response.getJSONObject("coord");

                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);

                            String description = position0.getString("description");
                            String iconName = position0.getString("icon");

                            int vis = response.getInt("visibility");

                            String name = response.getString("name");

                            JSONObject mainObject = response.getJSONObject("main");
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");

                            runOnUiThread( (  )  -> {
                                binding.tempTextView.setText(String.valueOf("The current temperature is: " + current));
                                binding.tempTextView.setVisibility(View.VISIBLE);

                                binding.maxTempTextView.setText(String.valueOf("The max temperature is: " + max));
                                binding.maxTempTextView.setVisibility(View.VISIBLE);

                                binding.minTempTextView.setText(String.valueOf("The min temperature is: " + min));
                                binding.minTempTextView.setVisibility(View.VISIBLE);

                                binding.humidityTextView.setText(String.valueOf("The humidity is: " + humidity));
                                binding.humidityTextView.setVisibility(View.VISIBLE);



                                try{
                                    binding.descriptionTextView.setText(description);
                                    binding.descriptionTextView.setVisibility(View.VISIBLE);
                                }catch(Exception e){}

                                String pathname = getFilesDir() + "/" + iconName + ".png";
                                File file = new File(pathname);
                                if (file.exists()){
                                    runOnUiThread( (  )  -> {
                                        binding.imageView.setImageBitmap(BitmapFactory.decodeFile(pathname));
                                        binding.imageView.setVisibility(View.VISIBLE);
                                    });

                                }else{
                                    String imageURL = "https://openweathermap.org/img/w/" + iconName + ".png";
                                    ImageRequest imgReq = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {

                                            FileOutputStream fOut = null;
                                            try {
                                                fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);


                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                                fOut.flush();
                                                fOut.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }

                                            runOnUiThread( (  )  -> {
                                                binding.imageView.setImageBitmap(bitmap);
                                                binding.imageView.setVisibility(View.VISIBLE);


                                            });

                                        }
                                    }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error ) -> {

                                    });

                                    queue.add(imgReq);
                                }

                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    },
                    (error) -> {    });


            queue.add(request);







        });


    }

}
