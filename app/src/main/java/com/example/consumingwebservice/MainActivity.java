package com.example.consumingwebservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText txtT, txtB, txtI;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Relacionar vistas
        txtI = findViewById(R.id.txtid);
        txtT = findViewById(R.id.txtTitle);
        txtB = findViewById(R.id.txtBody);
        button = findViewById(R.id.button);

        // Agregar listener al botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ejecutarServicio();
                insertarServicioweb();
            }
        });
    }

    private void ejecutarServicio() {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            txtI.setText(jsonObject.getString("userId"));
                            txtT.setText(jsonObject.getString("title"));
                            txtB.setText(jsonObject.getString("body")); // ← "body" en minúscula
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: el sistema no está disponible", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(postRequest);
    }
    //Metodo para postear datos a un servicio web
    private void insertarServicioweb() {
        //url
        String url = "https://jsonplaceholder.typicode.com/posts";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /* JSONObject jsonObject = new JSONObject(response);
                            txtI.setText(jsonObject.getString("userId"));
                            txtT.setText(jsonObject.getString("title"));
                            txtB.setText(jsonObject.getString("body")); // ← "body" en minúscula */
                            Toast.makeText(MainActivity.this, "Datos insertados correctamente"+ response, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(com.android.volley.VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: el sistema no está disponible", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", txtI.getText().toString());
                params.put("title", txtT.getText().toString());
                params.put("body", txtB.getText().toString());

                return params;
            }

        }
                ;

        Volley.newRequestQueue(this).add(postRequest);
    }
}
