package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.MainActivity;
import com.example.even1.endorsedsystemteacher.R;

import java.util.Calendar;

public class Do_Homework extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView title,startdate,enddate,starttime,endtime,chapters,bookname;
    private LinearLayout start,end,chapter,book;

    int mYear, mMonth, mDay;
    int hour,Minute;


    final int DATE_DIALOG = 1;
    final int TIME_DIALOG = 2;

    private DatePickerDialog datePickerDialog,datePickerDialog2;
    private TimePickerDialog timePickerDialog,timePickerDialog2;

    private Button button;
    private String choose ="";

    private int number=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do__homework);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (TextView)findViewById(R.id.title);
        start = (LinearLayout)findViewById(R.id.start);
        end = (LinearLayout)findViewById(R.id.end);
        startdate = (TextView)findViewById(R.id.startdate);
        enddate = (TextView)findViewById(R.id.enddate);
        starttime = (TextView)findViewById(R.id.starttime);
        endtime = (TextView)findViewById(R.id.endtime);
        button = (Button)findViewById(R.id.button);
        chapter = (LinearLayout) findViewById(R.id.chapter);
        chapters = (TextView)findViewById(R.id.chapters);

        book = (LinearLayout)findViewById(R.id.book);
        bookname = (TextView)findViewById(R.id.bookname);

        init();
    }

    private void init() {
        title.setText("作业详情");
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        datePickerDialog =  new DatePickerDialog(this,R.style.MyDatePickerDialogTheme, mdateListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        timePickerDialog =  new TimePickerDialog(this,R.style.MyDatePickerDialogTheme,mtimeListener,hour,Minute,true);

        datePickerDialog2 =  new DatePickerDialog(this,R.style.MyDatePickerDialogTheme, mdateListener2, mYear, mMonth, mDay);
        datePickerDialog2.getDatePicker().setMinDate(System.currentTimeMillis());
        timePickerDialog2 =  new TimePickerDialog(this,R.style.MyDatePickerDialogTheme,mtimeListener2,hour,Minute,true);

        button.setOnClickListener(this);
        chapter.setOnClickListener(this);
        book.setOnClickListener(this);

        Intent intent = getIntent();
        bookname.setText(intent.getStringExtra("name"));
        number = intent.getIntExtra("n",0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                showDialog(DATE_DIALOG);
                final Calendar ca = Calendar.getInstance();
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                hour = ca.get(Calendar.HOUR_OF_DAY);
                Minute = ca.get(Calendar.MINUTE);
                break;
            case R.id.end:
                showDialog(3);
                final Calendar ca2 = Calendar.getInstance();
                mYear = ca2.get(Calendar.YEAR);
                mMonth = ca2.get(Calendar.MONTH);
                mDay = ca2.get(Calendar.DAY_OF_MONTH);
                hour = ca2.get(Calendar.HOUR_OF_DAY);
                Minute = ca2.get(Calendar.MINUTE);
                break;
            case R.id.button:
                if(number==1){
                    startActivity(new Intent(this,MyClass_detail.class));
                }
                if(number==2){
                    Intent main = new Intent(this, MainActivity.class);
                    main.putExtra("n",2);
                    startActivity(main);
                }
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.chapter:
                showMultiChoiceDialogFragment();
                break;
            case R.id.book:
                startActivity(new Intent(this,Search_books.class));
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return datePickerDialog;
            case TIME_DIALOG:
                return timePickerDialog;
            case 3:
                return datePickerDialog2;
            case 4:
                return timePickerDialog2;
        }
        return null;
    }
    public void display(int n) {
        switch (n){
            case 1:
                startdate.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
                starttime.setText(new StringBuffer().append(pad(hour)).append(":").append(pad(Minute)));
                break;
            case 2:
                enddate.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
                endtime.setText(new StringBuffer().append(pad(hour)).append(":").append(pad(Minute)));
                break;
            default:
                break;
        }

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display(1);
            showDialog(TIME_DIALOG);
        }
    };

    private TimePickerDialog.OnTimeSetListener mtimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            Minute = minute;
            display(1);
        }
    };
    private DatePickerDialog.OnDateSetListener mdateListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display(2);
            showDialog(4);
        }
    };

    private TimePickerDialog.OnTimeSetListener mtimeListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            Minute = minute;
            display(2);
        }
    };

    public void showMultiChoiceDialogFragment() {
        final String items[] = {"第一章", "第二章", "第三章", "第四章"};
        final boolean selected[] = {false,false,false,false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择要阅读的章节");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMultiChoiceItems(items, selected,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {

                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                // android会自动根据你选择的改变selected数组的值。
             for (int i = 0; i < selected.length; i++) {
                 if(selected[i]){
                     choose+= items[i]+"/";
                 }
             }
                chapters.setText(choose);
            }
        });
        builder.create().show();

    }
}
