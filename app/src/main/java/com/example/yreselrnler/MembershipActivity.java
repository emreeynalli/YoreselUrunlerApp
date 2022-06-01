package com.example.yreselrnler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MembershipActivity extends AppCompatActivity {
    EditText email, password, phone;
    Button birthday,signup;
    String Email,Password,Phone,Birthday;
    private static String url = "http://192.168.1.48:80/YöreselÜrünler/memberinsert.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        birthday = findViewById(R.id.birthday);
        signup = findViewById(R.id.signup);

        Intent intent = getIntent();

        birthday.setOnClickListener(dateofbirth);
        signup.setOnClickListener(sign);


    }
    public View.OnClickListener sign = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Email= String.valueOf(email.getText());
            Password= String.valueOf(password.getText());
            Phone= String.valueOf(phone.getText());
            Birthday= String.valueOf(birthday.getText());

            if(!(Email.equals("")) && !(Password.equals("")) && !(Phone.equals("")) && !(Birthday.equals("BIRTHDAY")) )
            {    //değerler girilmişse

                Toast.makeText(MembershipActivity.this, "Kaydınız başarıyla tamamlandı", Toast.LENGTH_SHORT).show();
                uyeEkle();
            }
            else{
                Toast.makeText(MembershipActivity.this, "Lütfen boş alanları doldurun", Toast.LENGTH_SHORT).show();
            }


        }
    };

    public void uyeEkle(){
        RequestQueue queue = Volley.newRequestQueue(MembershipActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MembershipActivity.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MembershipActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("member_mail", Email);
                params.put("member_pass", Password);
                params.put("member_phone", Phone);
                params.put("member_birth", Birthday);


                return params;
            }
        };
        queue.add(postRequest);
    }

    public View.OnClickListener dateofbirth = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar takvim = Calendar.getInstance();
            int yil = takvim.get(Calendar.YEAR);
            int ay = takvim.get(Calendar.MONTH);
            int gun = takvim.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(MembershipActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            // ay değeri 0 dan başladığı için (Ocak=0, Şubat=1,..,Aralık=11)
                            // değeri 1 artırarak gösteriyoruz.
                            month += 1;
                            // year, month ve dayOfMonth değerleri seçilen tarihin değerleridir.
                            // Edittextte bu değerleri gösteriyoruz.
                            birthday.setText(dayOfMonth + "/" + month + "/" + year);
                        }
                    }, yil, ay, gun);
            // datepicker açıldığında set edilecek değerleri buraya yazıyoruz.
            // şimdiki zamanı göstermesi için yukarda tanmladığımz değşkenleri kullanyoruz.

            // dialog penceresinin button bilgilerini ayarlıyoruz ve ekranda gösteriyoruz.
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
            dpd.show();
        }
    };
}