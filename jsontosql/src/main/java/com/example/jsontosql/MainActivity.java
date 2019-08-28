package com.example.jsontosql;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connector.getDatabase();


        final GetJsonDataUtil util = new GetJsonDataUtil();

        util.getProvinceList(this, new GetJsonDataUtil.Callback() {
            @Override
            public void getData(List<? extends JSONObject> data) throws JSONException {

                for (int i = 0; i < data.size(); i++) {
                    Province province = parseProvince(data.get(i));
                    province.save();
                    List<JSONObject> cityList=util.getCityList(data.get(i));

                    for(JSONObject cityJ:cityList){
                        City city=parseCity(province.code,cityJ);
                        city.save();
                        if(cityJ.has("areaList")){
                            List<JSONObject> areaList=util.getAreaList(cityJ);

                            for(JSONObject areaJ:areaList) {
                                Area area = parstArea(city.code,areaJ);
                                area.save();
                            }
                        }
                    }
                }
            }
        });
    }

    private Province parseProvince(JSONObject jsonObject) throws JSONException {

        Province p = new Province(
                jsonObject.getInt("code"),
                jsonObject.getString("name")
        );

        return p;
    }


    private City parseCity(int provinceCode,JSONObject jsonObject) throws JSONException {
        City city = new City(
                provinceCode,
                jsonObject.getInt("code"),
                jsonObject.getString("name")
        );
        return city;
    }


    private Area parstArea(int cityCode,JSONObject jsonObject) throws JSONException {

        Area area=new Area(
                cityCode,
                jsonObject.getInt("code"),
                jsonObject.getString("name")
        );
        return area;
    }


    public static class Province extends DataSupport {

        private int code;

        private String name;

        public Province(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public Province() {
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class City extends DataSupport {

        private int provinceCode;

        private int code;

        private String name;

        public City(int provinceCode, int code, String name) {
            this.provinceCode = provinceCode;
            this.code = code;
            this.name = name;
        }

        public City() {
        }

        public int getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(int provinceCode) {
            this.provinceCode = provinceCode;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Area extends DataSupport {
        private int cityCode;

        private int code;

        private String name;


        public Area(int cityCode, int code, String name) {
            this.cityCode = cityCode;
            this.code = code;
            this.name = name;
        }

        public Area() {
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
