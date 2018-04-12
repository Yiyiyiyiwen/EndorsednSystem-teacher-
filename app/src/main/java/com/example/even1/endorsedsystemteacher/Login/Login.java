package com.example.even1.endorsedsystemteacher.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.MainActivity;
import com.example.even1.endorsedsystemteacher.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView appname;
    private EditText name,pass;
    private Button button;
    private int loginstate = 0;
    private CheckBox rememberpass;
    private SharedPreferences pref;
    private MainActivity main = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appname = (TextView)findViewById(R.id.appname);
        name = (EditText)findViewById(R.id.usename);
        pass = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.submit);
        rememberpass = (CheckBox)findViewById(R.id.remember_password) ;

        init();

    }

    private void init() {
        Typeface fz=Typeface.createFromAsset(getAssets(),"FZlight.ttf");
        appname.setTypeface(fz);
        //pref= PreferenceManager.getDefaultSharedPreferences(this);
        pref = getSharedPreferences("User",MODE_PRIVATE);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            name.setText(pref.getString("USER_NAME",""));
            pass.setText(pref.getString("PASSWORD",""));
            rememberpass.setChecked(true);
        }
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                String mName = name.getText().toString();
                String mPassword = pass.getText().toString();

                if (TextUtils.isEmpty(mName.trim()) || TextUtils.isEmpty(mPassword.trim())) {
                    Toast.makeText(Login.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    loginByAsyncHttpClientGet(mName, mPassword);
                    finish();
                }
        }
    }
    public void loginByAsyncHttpClientGet(final String userName, final String passWord) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/auth/applogin.action";
        RequestParams params = new RequestParams();
        params.put("username",userName);
        params.put("password",passWord);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONObject js = new JSONObject(resultDate);
                    int id = js.getInt("id");
                    String state = js.getString("state");
                    String nickname = js.getString("nickname");
                    String biref = js.getString("biref");
                    String phone = js.getString("phone");
                    String email = js.getString("email");
                    String job = js.getString("job");
                    String birth = js.getString("birth");
                    String place = js.getString("place");
                    String school = js.getString("school");
                    String imgurl = js.getString("img");
                    System.out.println(
                            "id-------------"+id+"nickname---------------"+nickname
                    );

                    if(i==200){
                        SharedPreferences.Editor editor= pref.edit();
                        editor.putString("USER_NAME",name.getText().toString());
                        editor.putString("PASSWORD",pass.getText().toString());
                        editor.putBoolean("remember_password",true);
                        editor.putString("name",nickname);
                        editor.putInt("id",id);
                        editor.putString("biref",biref);
                        editor.putString("phone",phone);
                        editor.putString("email",email);
                        editor.putString("job",job);
                        editor.putString("birth",birth);
                        editor.putString("place",place);
                        editor.putString("school",school);
                        editor.putString("img",imgurl);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);


                    }
                    else{
                        Toast.makeText(Login.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(Login.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
