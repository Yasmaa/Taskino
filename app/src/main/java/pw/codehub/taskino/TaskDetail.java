package pw.codehub.taskino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import pw.codehub.taskino.db.TasksDoa;

public class TaskDetail<value> extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    Button button2;
    Button button3;
    Button button4;
    Button button5;


    TasksDoa datasource;

    Long id;
    Long tt;
    String t;

    String time;
    String tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);




        datasource = new TasksDoa(this);
        datasource.open();






        Intent i = getIntent(); // get the intent that started me
        tk = i.getStringExtra("id");
        time = String.valueOf(datasource.getTime(tk).get(0).getTime());
        id = Long.parseLong(tk);
        tt = Long.parseLong(time);






        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat(" %s");
        //chronometer.setBase(t);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {


                    tt = SystemClock.elapsedRealtime() - chronometer.getBase();




                int h   = (int)(tt /3600000);
                int m = (int)(tt - h*3600000)/60000;
                int s= (int)(tt - h*3600000- m*60000)/1000 ;
                t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });

        if(tt!=null) {
            chronometer.setBase(SystemClock.elapsedRealtime()-tt);

        }

        Long tv =  (SystemClock.elapsedRealtime()-tt);

        int h   = (int)(tt /3600000);
        int m = (int)(tt - h*3600000)/60000;
        int s= (int)(tt - h*3600000- m*60000)/1000 ;
        t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
        chronometer.setText(t);


        //chronometer.setText("00:00:00");






    }










    public void startChronometer(View v) {
        if (!running) {

            chronometer.setBase(SystemClock.elapsedRealtime()-tt - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() -tt - chronometer.getBase();
            running = false;

            datasource.updatetime(tk,""+tt);


        }
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        datasource.updatetime(tk,""+t);
    }

    public void finishChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        datasource.updatetime(tk,""+tt);
    }
}