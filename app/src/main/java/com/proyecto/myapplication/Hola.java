package com.proyecto.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Hola extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hola);
        /*Intent intent = getIntent();
        String cip = intent.getStringExtra("cip");
        if (cip != null) {
            cip = cip.trim(); // Eliminar espacios en blanco alrededor de "cip"
            Log.d("CIP_VALUE", "Valor de: " + cip); // Mostrar valor de "cip" en el Log
            Toast.makeText(this, "cip: " + cip, Toast.LENGTH_SHORT).show(); // Mostrar valor de "cip" en un Toast
        } else {
            Log.d("CIP_VALUE", "El valor de cip es nulo"); // Mostrar mensaje en el Log si "cip" es nulo
            Toast.makeText(this, "No se recibió el CIP", Toast.LENGTH_SHORT).show(); // Mostrar mensaje si "cip" es nulo en un Toast
        }*/
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cip")) {
            String cip = intent.getStringExtra("cip").trim();
            Toast.makeText(this, "cip"+cip, Toast.LENGTH_SHORT).show();
            Log.d("CIP_VALUE", "Valor de cip: " + cip); // Mostrar valor de "cip" en el Log
            // Hacer la solicitud GET a la API REST utilizando Volley
            String url = "http://192.168.16.104:9094/api/admin/users/user/"+cip.trim();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Parsear los datos JSON recibidos
                            try {
                                int id = response.getInt("id");
                                String lastname = response.getString("lastname");
                                String fullname = response.getString("fullname");
                                String grado = response.getString("grado");
                                String role = response.getString("role");
                                boolean estado = response.getBoolean("estado");

                                // Mostrar los datos en un Toast
                                String message = "ID: " + id + "\n" +
                                        "Apellido: " + lastname + "\n" +
                                        "Nombre: " + fullname + "\n" +
                                        "Grado: " + grado + "\n" +
                                        "Rol: " + role + "\n" +
                                        "Estado: " + estado;
                                Toast.makeText(Hola.this, message, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejar errores de la solicitud
                            Toast.makeText(Hola.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Añadir la solicitud a la cola de Volley
            Volley.newRequestQueue(this).add(jsonObjectRequest);

        } else {
            // Manejar si no se envió el extra correctamente
            Toast.makeText(this, "No se recibió el CIP", Toast.LENGTH_SHORT).show();
        }


    }

}