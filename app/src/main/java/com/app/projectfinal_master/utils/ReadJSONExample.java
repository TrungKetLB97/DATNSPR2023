package com.app.projectfinal_master.utils;

import android.content.Context;

import com.app.projectfinal_master.R;
import com.app.projectfinal_master.model.Address;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadJSONExample {
    static List<String> cityList = new ArrayList<>();
    static List<String> districtList = new ArrayList<>();
    static List<String> communeList = new ArrayList<>();

    public static List<String> cityCoded = new ArrayList<>();
    public static List<String> districtCoded = new ArrayList<>();

    // Read the company.json file and convert it to a java object.
//    public static Address readCompanyJSONFile(Context context) throws IOException, JSONException {
//
//        // Read content of company.json
//        String jsonCity = readText(context, R.raw.tinh_tp);
//        String jsonCommune1 = readText(context, R.raw.xa_phuong_1);
//        String jsonCommune2 = readText(context, R.raw.xa_phuong_2);
//
//        JSONObject jsonRootCity = new JSONObject(jsonCity);
//        JSONObject jsonRootDistrict = new JSONObject(jsonDistrict);
//        JSONObject jsonRootCommune1 = new JSONObject(jsonCommune1);
////        JSONObject jsonRootCommune2 = new JSONObject(jsonCommune2);
//
//        String cityDetail = "";
//        String districtDetail = "";
//        String communeDetail = "";
//
//        String cityName = "";
//        String communeName = "";
//        for (int i = 1; i <= 96; i++) {
//            try {
//                if (i < 10) {
//                    cityDetail = jsonRootCity.getString("0" + i);
//                } else {
//                    cityDetail = jsonRootCity.getString("" + i);
//                }
//                JSONObject jsonCityDetail = new JSONObject(cityDetail);
//                cityName = jsonCityDetail.getString("name");
//                cityCode = jsonCityDetail.getString("code");
//
////                ReadDistrictData(jsonDistrict, cityCode);
//
//            } catch (Exception e) {
//                continue;
//            }
//        }
//
////        String district = jsonRootDistrict.getString("name");
////        String commune1 = jsonRootDistrict.getString("name");
////        String commune2 = jsonRootDistrict.getString("name");
//
//        return address;
//    }

    public static void readCityData(Context context) throws IOException, JSONException {
        String jsonCity = readText(context, R.raw.tinh_tp);
        JSONObject jsonRootCity = new JSONObject(jsonCity);
        StringBuffer sb = new StringBuffer("00");
        for (int i = 1; i <= 96; i++) {
            try {
                sb.setLength(sb.length() - String.valueOf(i).length());
                sb.append(i);
                String cityDetail = jsonRootCity.getString(sb.toString());
                JSONObject jsonCityDetail = new JSONObject(cityDetail);
                cityCoded.add(jsonCityDetail.getString("code"));
                cityList.add(cityDetail);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static List<String> getCity() {
        List<String> city = new ArrayList<>();
        for (String cityDetail :
                cityList) {
            try {
                JSONObject jsonDistrictDetail = new JSONObject(cityDetail);
                String cityName = jsonDistrictDetail.getString("name_with_type");
                city.add(cityName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return city;
    }

    public static void readDistrictData(Context context) throws IOException, JSONException {
        String jsonDistrict = readText(context, R.raw.quan_huyen);
        JSONObject jsonRootDistrict = new JSONObject(jsonDistrict);
        StringBuffer sb = new StringBuffer("000");
        for (int i = 1; i <= 973; i++) {
            try {
                sb.setLength(sb.length() - String.valueOf(i).length());
                sb.append(i);
                String districtDetail = jsonRootDistrict.getString(sb.toString());
                JSONObject jsonDistrictDetail = new JSONObject(districtDetail);
//                String districtName = jsonDistrictDetail.getString("name_with_type");
//                String districtCode = jsonDistrictDetail.getString("code");
                districtCoded.add(jsonDistrictDetail.getString("code"));
                districtList.add(districtDetail);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static List<String> getDistrictWithCode(String cityCode) {
        List<String> district = new ArrayList<>();
        for (String districtDetail :
                districtList) {
            try {
                JSONObject jsonDistrictDetail = new JSONObject(districtDetail);

                if (jsonDistrictDetail.getString("parent_code").equals(cityCode)) {
                    String districtName = jsonDistrictDetail.getString("name_with_type");
                    district.add(districtName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return district;
    }

    public static void readCommuneData(Context context) throws IOException, JSONException {
        String jsonCommune = readText(context, R.raw.xa_phuong);
        JSONObject jsonRootCommune = new JSONObject(jsonCommune);
        StringBuffer sb = new StringBuffer("00000");
        for (int i = 1; i <= 32248; i++) {
            try {
                sb.setLength(sb.length() - String.valueOf(i).length());
                sb.append(i);
                String communeDetail = jsonRootCommune.getString(sb.toString());
                communeList.add(communeDetail);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public static List<String> getCommuneWithCode(String districtCode) {
        List<String> commune = new ArrayList<>();
        for (String communeDetail :
                communeList) {
            try {
                JSONObject jsonCommuneDetail = new JSONObject(communeDetail);

                if (jsonCommuneDetail.getString("parent_code").equals(districtCode)) {
                    String communeName = jsonCommuneDetail.getString("name_with_type");
                    commune.add(communeName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return commune;
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

}
