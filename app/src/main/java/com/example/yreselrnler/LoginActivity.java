package com.example.yreselrnler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText txtemail,txtpassword;
    String email,pass,userEmail,userPassword,name,userName;
    TextView member;
    Button login;
    ArrayList<String> mailList,passList;
    boolean entry = false;
    private static String url = "http://192.168.1.33:80/YöreselÜrünler/memberretrieve.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        txtemail = findViewById(R.id.email);
        txtpassword = findViewById(R.id.password);
        member = findViewById(R.id.member);
        login = findViewById(R.id.login);

        member.setOnClickListener(memberIntent);
        login.setOnClickListener(Login);

        isMember();
    }
    public View.OnClickListener Login = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            userEmail = String.valueOf(txtemail.getText());
            userPassword = String.valueOf(txtpassword.getText());

            if(!(userEmail.equals("")) && !(userPassword.equals("")))
            {
                girisYap();

            }

            else{
                Toast.makeText(LoginActivity.this, "Lütfen boş alanları doldurun", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void girisYap(){

        for(int i=0;i<mailList.size();i++){
            if(userEmail.equals(mailList.get(i))&&userPassword.equals(passList.get(i))){
                if(userEmail.equals("admin@admin.com")){
                    Toast.makeText(LoginActivity.this, "Admin Sayfasına Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                    String mail = mailList.get(i);
                    intent.putExtra("email",mail);
                    startActivity(intent);
                }



                entry = true;


            }
        }
        if(!entry){
            Toast.makeText(LoginActivity.this, "Kullanıcı adınız yada şifreniz yanlış!", Toast.LENGTH_SHORT).show();
        }



    }

    public boolean isMember(){
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject veri_json;
                try {
                    veri_json = new JSONObject(response);
                    JSONArray array = veri_json.getJSONArray("showarray");
                    mailList = new ArrayList<>();
                    passList = new ArrayList<>();


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        email = jsonObject.getString("member_mail");
                        pass = jsonObject.getString("member_pass");

                        mailList.add(i,email);
                        passList.add(i,pass);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })  {
            protected Map<String,String> getParams() throws com.android.volley.AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("komut","bilgileriver");
                return params;
            }

        };
        request.setShouldCache(false);
        queue.add(request);
        return true;
    }




    public View.OnClickListener memberIntent = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent memberintent = new Intent(LoginActivity.this,MembershipActivity.class);
            startActivity(memberintent);
        }
    };
}