package cn.edu.gdmec.android.sendjson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private EditText editText2;
    private Button button;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();

                super.handleMessage(msg);
            }
        };


        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(sendJson).start();

            }
        });


    }
    Runnable sendJson=new Runnable() {
        @Override
        public void run() {
            HttpURLConnection httpURLConnection=null;
            try{
                editText = (EditText) findViewById(R.id.editText);
                textView = (TextView) findViewById(R.id.textView);
                editText2 = (EditText) findViewById(R.id.editText2);

                String username = editText.getText().toString();
                username= URLEncoder.encode(username,"UTF-8");
                String password = editText2.getText().toString();

                JSONObject jsonObject=new JSONObject();
                jsonObject.put("UserName",username);
                jsonObject.put("Password",password);
                //打开和URL之间的连接

                URL url=new URL("http://192.168.186.1/ch5/webservice2");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                //设置通用的请求属性
                httpURLConnection.setRequestProperty("connection","Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Charset","UTF-8");
                //发送post请求的设置如下：
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                ///获取URLConnection对象对应的输出流
                DataOutputStream dataOutputStream=new DataOutputStream(httpURLConnection.getOutputStream());
                //发送请求参数
                dataOutputStream.writeBytes(jsonObject.toString());
                //flush输出流的缓冲
                dataOutputStream.flush();
                //根据getResponseCode()判断是否连接成功
                if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    ///用输出流来读取url的
                    InputStream is=httpURLConnection.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    StringBuffer buffer=new StringBuffer();
                    String line="";
                    while ((line=br.readLine())!=null){
                        buffer.append(line);
                    }
              //      Log.i("buffer", buffer.toString());

                    Message message=new Message();
                    message.obj=buffer;
                    handler.sendMessage(message);

                }
                dataOutputStream.close();




            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }

            }

        }
    };



}
