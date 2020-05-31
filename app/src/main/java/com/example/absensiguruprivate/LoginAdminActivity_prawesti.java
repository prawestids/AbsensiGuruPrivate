package com.example.absensiguruprivate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.absensiguruprivate.helper.Session_prawesti;
import com.example.absensiguruprivate.model.User_prawesti;
import com.example.absensiguruprivate.rest.ApiClient_prawesti;
import com.example.absensiguruprivate.rest.ApiInterface_prawesti;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdminActivity_prawesti extends AppCompatActivity {
    private EditText inputUsername, inputPassword;
    private TextView result;
    private Button loginButton;
    private ConstraintLayout loginForm;
    private Session_prawesti session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_prawesti);

        session = new Session_prawesti(getApplicationContext());
        inputUsername = findViewById(R.id.edt_username_admin);
        inputPassword = findViewById(R.id.edt_password_admin);
        result = findViewById(R.id.txt_login_guru);
        loginButton = findViewById(R.id.btn_login_admin);
        loginForm = findViewById(R.id.login_admin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin(inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    private void userLogin(String username, String password) {
        ApiInterface_prawesti apiInterface = ApiClient_prawesti.getClient().create(ApiInterface_prawesti.class);

        Call<ResponseBody> call = apiInterface.loginAdmin(new User_prawesti(username, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray json = new JSONArray(response.body().string());
                        String username = json.getJSONObject(0).getString("username");
                        String password = json.getJSONObject(0).getString("password");

                        session.setLoggedInStatus(true);
                        session.setLoggedInRole("admin");
                        session.setUsername(username);
                        session.setPassword(password);

                        Intent intent = new Intent(getApplicationContext(), AdminActivity_prawesti.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Credentials are not Valid.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void switchLoginGuru(View view) {
        Intent intent = new Intent(LoginAdminActivity_prawesti.this, LoginGuruActivity_prawesti.class);
        startActivity(intent);
    }
}
