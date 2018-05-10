package cn.edu.gdmec.android.sendjson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                while (msg.what==1){

                }

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
            editText = (EditText) findViewById(R.id.editText);
            textView = (TextView) findViewById(R.id.textView);
            editText2 = (EditText) findViewById(R.id.editText2);

            Message message=new Message();
            message.what=1;
        }
    };



}
