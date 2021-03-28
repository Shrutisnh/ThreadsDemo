package com.example.threadsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "appDebug:";
    private Button buttonStartThread;
    private Handler mainHandler = new Handler();

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view){
        stopThread = false;
//        ExampleThread thread = new ExampleThread(10);
//        thread.start();

        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();
    }

    public void stopThread(View view){
        stopThread = true;
    }
    class ExampleThread extends Thread{
        int seconds;
        public ExampleThread(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for(int i=0;i<seconds;i++){
                Log.d(TAG, "startThread: ");
                SystemClock.sleep(1000);
            }
        }
    }

    class ExampleRunnable implements Runnable{
        int seconds;
        public ExampleRunnable(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for(int i=0;i<seconds;i++){
                if(stopThread)
                    return;
                if(i==5) {
                    //below are the 4 ways to update the view from background thread

                 /*   Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    */
                   /* buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });

                    */
                    /*mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                     */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });

                }
                Log.d(TAG, "startRunnable: "+i);
                SystemClock.sleep(1000);
            }
        }
    }
}