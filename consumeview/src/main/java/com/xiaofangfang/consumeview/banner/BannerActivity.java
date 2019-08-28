package com.xiaofangfang.consumeview.banner;

import android.os.Bundle;
import android.util.Log;

import com.xiaofangfang.consumeview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BannerActivity extends AppCompatActivity {

    private static final String TAG = "test";
    BannerFlip bannerFlip;

    String json = "{\n" +
            "  \"code\": 0,\n" +
            "  \"message\": \"\",\n" +
            "  \"timestamp\": 1555516707921,\n" +
            "  \"modelId\": 0,\n" +
            "  \"count\": 0,\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"createdate\": \"2018-01-28 22:11:43\",\n" +
            "      \"updatedate\": \"2018-01-28 22:11:45\",\n" +
            "      \"imageurl\": \"/image/adImage/start1.png\",\n" +
            "      \"url\": \"/image/adImage/start1.png\",\n" +
            "      \"advtype\": \"start\",\n" +
            "      \"sorting\": 1,\n" +
            "      \"title\": \"启动图1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"createdate\": \"2018-03-02 13:24:34\",\n" +
            "      \"updatedate\": \"2018-03-02 13:24:34\",\n" +
            "      \"imageurl\": \"/Users/simon/Downloads/2eed63b0-fa31-4116-9abd-c39cc4378e68.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"start\",\n" +
            "      \"sorting\": 1,\n" +
            "      \"title\": \"启动图\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9,\n" +
            "      \"createdate\": \"2018-12-29 21:42:00\",\n" +
            "      \"updatedate\": \"2018-12-29 21:42:00\",\n" +
            "      \"imageurl\": \"/image/e5fcd10f-32b5-4359-88b6-f90dc1b51995.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 1,\n" +
            "      \"title\": \"广告图1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10,\n" +
            "      \"createdate\": \"2018-12-29 21:42:30\",\n" +
            "      \"updatedate\": \"2018-12-29 21:42:30\",\n" +
            "      \"imageurl\": \"/image/464a9e48-ab54-45aa-9d45-e89be5c415ac.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 2,\n" +
            "      \"title\": \"广告图2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 11,\n" +
            "      \"createdate\": \"2018-12-29 21:42:54\",\n" +
            "      \"updatedate\": \"2018-12-29 21:42:54\",\n" +
            "      \"imageurl\": \"/image/d390f167-7c29-4364-aeab-0336799ccaf0.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 3,\n" +
            "      \"title\": \"广告图3\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 12,\n" +
            "      \"createdate\": \"2018-12-29 21:43:13\",\n" +
            "      \"updatedate\": \"2018-12-29 21:43:13\",\n" +
            "      \"imageurl\": \"/image/cd9e46a1-ff9c-4963-8ea2-c24b7ae92b8a.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 4,\n" +
            "      \"title\": \"广告图4\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 13,\n" +
            "      \"createdate\": \"2018-12-29 21:43:29\",\n" +
            "      \"updatedate\": \"2018-12-29 21:43:29\",\n" +
            "      \"imageurl\": \"/image/9d6dc3a7-1af4-44c6-a3cc-9f926af01129.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 5,\n" +
            "      \"title\": \"广告图5\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 14,\n" +
            "      \"createdate\": \"2018-12-29 21:43:46\",\n" +
            "      \"updatedate\": \"2018-12-29 21:43:46\",\n" +
            "      \"imageurl\": \"/image/7bb136eb-0102-4e3a-aa3d-917e876675e6.jpg\",\n" +
            "      \"url\": null,\n" +
            "      \"advtype\": \"ad\",\n" +
            "      \"sorting\": 6,\n" +
            "      \"title\": \"广告图6\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    private final String SERVER = "https://www.bmgsos.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


//        Integer[] value = {
//                R.drawable.banner1,
//                R.drawable.banner2,
//                R.drawable.banner3,
//                R.drawable.banner4,
//                R.drawable.banner5,
//                R.drawable.banner6
//        };

        List<String> jsonData = parseJson(json);


        for (String data : jsonData) {
            Log.d(TAG, "onCreate: "+data);
        }


        bannerFlip = findViewById(R.id.banner);

        bannerFlip.setImageUrl(jsonData);
        bannerFlip.setBannerHeight(600);
        bannerFlip.startAutoRoll(2000);
    }


    public List<String> parseJson(String json) {

        List<String> stringList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);


            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject js = jsonArray.getJSONObject(i);

                String title = js.getString("title");
                if (!title.contains("广告")) {
                    continue;
                }
                if (title.contains("广告")) {
                    stringList.add(SERVER + js.getString("imageurl"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringList;
    }


}
