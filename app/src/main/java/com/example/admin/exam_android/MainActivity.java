package com.example.admin.exam_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.view.KeyEvent;
import android.view.View;
import java.util.ArrayList;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {
    static String title[]= new String[50];
    static String titletop[]= new String[50];
    static String titledonetop[]= new String[50];
    static String description[]= new String[50];
    static String titledone[]= new String[50];
    static String descriptiondone[]= new String[50];
    static String time[] = new String[50];
    static String time2[] = new String[50];
    static String timedone[] = new String[50];
    static String time2done[] = new String[50];
    final ArrayList todoItems = new ArrayList();
    final ArrayList finisheditems = new ArrayList();
    static int i=0,j=0,ii=0;
    ListView Mylistview;
    ListView finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mylistview = findViewById(R.id.myListView);
        finished = findViewById(R.id.finished);
        final ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,finisheditems);
        final EditText Myedittext = findViewById(R.id.myEditText);
        finished.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        finished.setAdapter(bb);
        final ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,todoItems);
        Mylistview.setItemsCanFocus(false);
        Mylistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Mylistview.setAdapter(aa);
            ii=getPreferences(MODE_PRIVATE).getInt("i",ii);
            Log.d("ii= ", String.valueOf(ii));
            j=getPreferences(MODE_PRIVATE).getInt("j",j);
        if (ii > 0)
        {
            for(int nb=0;nb<ii;nb++) {
                if(title[nb]!="")
                    title[nb]=getPreferences(MODE_PRIVATE).getString(String.format("title"+"%d",nb), title[nb]);
            }
            for(int nb=0;nb<ii;nb++) {
                if(titletop[nb]!=""){
                    titletop[nb]=getPreferences(MODE_PRIVATE).getString(String.format("titletop"+"%d",nb), titletop[nb]);
                    todoItems.add(titletop[nb]);
                    aa.notifyDataSetChanged();
                    Log.d("charge",titletop[nb]);
                }
            }

            for(int nb=0;nb<ii;nb++) {
                if(description[nb]!="")
                    description[nb]=getPreferences(MODE_PRIVATE).getString(String.format("description"+"%d",nb), description[nb]);
            }
            for(int nb=0;nb<ii;nb++) {
                if(time[nb]!="")
                    time[nb]=getPreferences(MODE_PRIVATE).getString(String.format("time"+"%d",nb), time[nb]);
            }
            for(int nb=0;nb<ii;nb++) {
                if(time2[nb]!="")
                    time2[nb]=getPreferences(MODE_PRIVATE).getString(String.format("time2"+"%d",nb), time2[nb]);
            }
        }
        if(j>0) {
            for (int nb = 0; nb < j; nb++) {
                if (titledonetop[nb] != "") {
                    titledonetop[nb] = getPreferences(MODE_PRIVATE).getString(String.format("titledonetop" + "%d", nb), titledonetop[nb]);
                    finisheditems.add(titledonetop[nb]);
                    bb.notifyDataSetChanged();
                }
            }
        }
        Myedittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    if(i==KeyEvent.KEYCODE_DPAD_CENTER || i==keyEvent.KEYCODE_ENTER)
                    {
                        todoItems.add(Myedittext.getText().toString());
                        aa.notifyDataSetChanged();
                        Myedittext.setText("");
                        ii++;

                        return true;
                    }
                }
                return false;
            }
        });
        Mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFastDoubleClick()){
                    finisheditems.add(view);
                    todoItems.remove(i);
                    aa.notifyDataSetChanged();
                    bb.notifyDataSetChanged();
                }
                else{
                    Intent inte = new Intent(MainActivity.this, TacheDetail.class);
                    inte.putExtra("title", i);
                    //inte.putExtra("titletop", titletop[i]);
                    startActivity(inte);
                    Log.d("new act","act successed");
                }
            }
        });
        Mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                finisheditems.add("Complete:  "+todoItems.get(i));
                todoItems.remove(i);
                bb.notifyDataSetChanged();
                aa.notifyDataSetChanged();


                titledone[j]=title[i];
                descriptiondone[j]=description[i];
                timedone[j]=timedone[i];
                time2done[j]=time2done[i];
                titledonetop[j]=titletop[i];
                j++;
                for (int num = i;num<ii;num++)
                {
                    title[num]=title[num+1];
                    description[num]=description[num+1];
                    time[num]=time[num+1];
                    time2[num]=time2[num+1];
                }

                ii--;
                return true;
            }

        });
        finished.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                finisheditems.remove(i);
                bb.notifyDataSetChanged();
                for (int num = i;num<j;num++)
                {
                    titledone[num]=titledone[num+1];
                    titledonetop[num]=titledonetop[num+1];
                    descriptiondone[num]=descriptiondone[num+1];
                    timedone[num]=timedone[num+1];
                    time2done[num]=time2done[num+1];
                }
                j--;
                return false;
            }
        });

    }
    @Override
    protected void onPause() {
        Log.d("ii=???", String.valueOf(ii));
        if(ii>0) {
            for (int nb = 0; nb < ii; nb++) {
                titletop[nb] = (String) Mylistview.getItemAtPosition(nb);
                Log.d("title  ", (String) Mylistview.getItemAtPosition(nb));
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("titletop" + "%d", nb), titletop[nb]);
                ed.commit();
            }
            for (int nb = 0; nb < ii; nb++) {
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("title" + "%d", nb), title[nb]);
                ed.commit();
            }
            for (int nb = 0; nb < ii; nb++) {
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("description" + "%d", nb), description[nb]);
                ed.commit();
            }
            for (int nb = 0; nb < ii; nb++) {
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("time" + "%d", nb), time[nb]);
                ed.commit();
            }
            for (int nb = 0; nb < ii; nb++) {
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("time2" + "%d", nb), time2[nb]);
                ed.commit();
            }
        }
        if (j > 0) {

            for (int nb = 0; nb < j; nb++) {

                titledonetop[nb] = (String) finished.getItemAtPosition(nb);
                SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
                ed.putString(String.format("titledonetop" + "%d", nb), titledonetop[nb]);
                ed.commit();
            }

        }

        SharedPreferences.Editor ed=getPreferences(MODE_PRIVATE).edit();
        ed.putInt("i",ii);
        ed.commit();
        ed.putInt("j",j);
        ed.commit();

        super.onPause();
    }
    @Override
    protected void onDestroy() {
        SharedPreferences mShare = getSharedPreferences("zijian", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit = mShare.edit();
        super.onDestroy();
    }
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }


}
