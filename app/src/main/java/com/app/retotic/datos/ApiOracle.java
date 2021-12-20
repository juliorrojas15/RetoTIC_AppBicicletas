package com.app.retotic.datos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.retotic.adaptadores.SucursalAdapter;
import com.app.retotic.casos_uso.CasoUsoSucursal;
import com.app.retotic.modelos.Sucursal;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiOracle {
    private RequestQueue queue;
    private String uriSucursales = "https://gf004e1e5b60160-nomina.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/sucursales/sucursales";
    private CasoUsoSucursal casoUsoSucursal;
    private Context context;

    public ApiOracle(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
        casoUsoSucursal = new CasoUsoSucursal();
    }

    //1 Request.Method(POST,PUT,DELETE,GET), 2.URL,3.JASON,4.REQUEST,5. ERROR
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertSucursal(String name, String description, String address, ImageView imageView){
        JSONObject json = new JSONObject();
        String image = casoUsoSucursal.imageViewToString(imageView);
        try {
            json.put("name",name);
            json.put("description",description);
            json.put("address",address);
            json.put("image",image);
        }catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uriSucursales, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void  getAllSucursales(GridView gridView){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uriSucursales, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    ArrayList<Sucursal> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        byte[] image = casoUsoSucursal.stringToByte(jsonObject.getString("image"));
                        Sucursal sucursal = new Sucursal(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("address"),
                                image
                        );
                        list.add(sucursal);
                    }
                    SucursalAdapter sucursalAdapter = new SucursalAdapter(context,list);
                    gridView.setAdapter(sucursalAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void  deleteSucursal(String id){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, uriSucursales + "/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getSucursalById(String id, EditText name, EditText description, EditText address, ImageView imageView, GoogleMap googleMap){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uriSucursales + "/" + id, null, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    byte[] image = casoUsoSucursal.stringToByte(jsonObject.getString("image"));
                    Sucursal sucursal = new Sucursal(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("address"),
                            image
                    );
                    byte[] imageSuc = sucursal.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageSuc,0,image.length);
                    imageView.setImageBitmap(bitmap);
                    name.setText(sucursal.getName());
                    description.setText(sucursal.getDescription());
                    address.setText(sucursal.getAddress());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(sucursal.locationToCoord());
                    markerOptions.title(sucursal.getName());
                    googleMap.clear();
                    googleMap.addMarker(markerOptions);


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSucursal(String id, String name, String description, String address, ImageView imageView){
        JSONObject json = new JSONObject();
        String image = casoUsoSucursal.imageViewToString(imageView);
        try {
            json.put("id",id);
            json.put("name",name);
            json.put("description",description);
            json.put("address",address);
            json.put("image",image);
        }catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, uriSucursales, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
}
