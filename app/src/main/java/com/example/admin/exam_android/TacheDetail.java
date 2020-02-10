package com.example.admin.exam_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TacheDetail extends Activity {
    EditText titleEdit,destEdit,timeEdit,timeEditt;
    Button save,delete;
    int iclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemactivity);

        titleEdit=findViewById(R.id.mytitle);
        destEdit = findViewById(R.id.mydescription);
        timeEdit=findViewById(R.id.mydate);
        timeEditt=findViewById(R.id.mytime);
        Intent inte2 = getIntent();
        final int i = inte2.getIntExtra("title",0);
        //final String titleTop=  inte2.getStringExtra("titletop");
        //titleEdit.setText(titleTop);
        for(int ii=0;ii<=i;ii++){
            if(MainActivity.title[ii]!="") {
                titleEdit.setText(MainActivity.title[ii]);
                destEdit.setText(MainActivity.description[ii]);
                Calendar calendar = Calendar.getInstance();
                String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                String mYear = String.valueOf(calendar.get(Calendar.YEAR));
                String mHours,mMinute;
                if (calendar.get(Calendar.HOUR) < 10) {
                    mHours = "0" + calendar.get(Calendar.HOUR);
                } else {
                    mHours = String.valueOf(calendar.get(Calendar.HOUR));
                }
                if (calendar.get(Calendar.MINUTE) < 10) {
                    mMinute = "0" + calendar.get(Calendar.MINUTE);
                } else {
                    mMinute = String.valueOf(calendar.get(Calendar.MINUTE));
                }
                timeEdit.setText(mDay+"/"+mMonth+"/"+mYear+"  " +mHours+":"+mMinute);
                timeEditt.setText("预计完成日期： "+mDay+"/"+mMonth+"/"+mYear);
            }
            iclose=i;

        }



    }
    @Override
    protected void onDestroy() {
        MainActivity.title[iclose] = titleEdit.getText().toString();
        MainActivity.description[iclose]=destEdit.getText().toString();
        MainActivity.time[iclose]=timeEdit.getText().toString();
        MainActivity.time2[iclose]=timeEditt.getText().toString();
        super.onDestroy();
    }
}
