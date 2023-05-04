package com.example.task11_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int[] integers=null;
    int clicks = 0;

    TextView text, text2;
    Button button, asyncBtn;

    ProgressBar indicator;

    TextView clicksView;

    AsyncTask asyncTask;

    Button clicksBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(this::startThread);
        asyncBtn = findViewById(R.id.progressBtn2);
        text2 = findViewById(R.id.statusView2);
        indicator = findViewById(R.id.indicator2);
        integers = new int[100];
        for(int i=0;i<100;i++) {
            integers[i] = i + 1;
        }
        asyncBtn.setOnClickListener(this::useAsyncTask);

        clicksView = findViewById(R.id.clicksView);
        clicksBtn = findViewById(R.id.clicksBtn);
        clicksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                clicksView.setText("Кликов: " + clicks);
            }
        });
    }


    public void startThread(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // получаем текущее время
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                String time = hours + ":" + minutes + ":" + seconds;
                // отображаем в текстовом поле
                text.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(time);
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void useAsyncTask(View view) {
        asyncTask = new ProgressTask().execute();
    }

    class ProgressTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            for (int i = 0; i<integers.length;i++) {
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return(null);
        }
        @Override
        protected void onProgressUpdate(Integer... items) {
            indicator.setProgress(items[0]+1);
            text2.setText("Статус: " + String.valueOf(items[0]+1));
        }
        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(getApplicationContext(), "Задача завершена",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void goToSecond(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //asyncTask.cancel(true);
    }
}
