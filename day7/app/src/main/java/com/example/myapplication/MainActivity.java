package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityTuneBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    int minutes, hour;
    int dayOfMonth, month, year;
    Spinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        edTime = findViewById(R.id.edTime);
//        edDate = findViewById(R.id.edDate);
        EventHandling();
        EventSpiner();

    }

    private void EventHandling() {
        binding.edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTime();
            }
        });
        binding.edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });
        binding.tvTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] stringTags = {"Family", "Game", "Android", "VTC", "Friends"};
                boolean[] booleans = {false, false, false, false, false};
                //Chuyển mảng stringTags qua List
                final List<String> listTags = Arrays.asList(stringTags);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Choose tags: ")
                        .setMultiChoiceItems(stringTags, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean ischecked) {
                                if (ischecked) {
                                    Toast.makeText(MainActivity.this, stringTags[which], Toast.LENGTH_LONG).show();
                                }

                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < listTags.size(); i++) {
                                    boolean checked = booleans[i];
                                    if (checked) {
                                        binding.tvTags.setText(binding.tvTags.getText() + listTags.get(i) + ",");
                                    }
                                }


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        }).create();
                alertDialog.show();
            }
        });
        binding.tvWeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] weeks = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                boolean[] booleanWeeks = {false, false, false, false, false, false, false};
                final List<String> listWeeks = Arrays.asList(weeks);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Choose weeks: ")
                        .setMultiChoiceItems(weeks, booleanWeeks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < listWeeks.size(); i++) {
                                    boolean checked = booleanWeeks[i];
                                    if (checked) {
                                        binding.tvWeeks.setText(binding.tvWeeks.getText() + listWeeks.get(i) + ",");
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cacel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();

            }
        });
        binding.btnTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragment(TuneFragment.newInstance());
            }
        });
    }

    private void chooseTime() {
        calendar = Calendar.getInstance();
        minutes = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                binding.edTime.setText(String.format("%02d:%02d", hour, minutes));
            }
        }, hour, minutes, true);
        timePickerDialog.show();
    }

    private void chooseDate() {
        calendar = Calendar.getInstance();
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String curentDate = DateFormat.getDateInstance().format(calendar.getTime());
                binding.edDate.setText(curentDate);
            }
        }, dayOfMonth, month, year);
        datePickerDialog.show();

    }

    private void EventSpiner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Work");
        arrayList.add("Friend");
        arrayList.add("Familyl");
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        binding.spinner.setAdapter(arrayAdapter);

    }

    private void onFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}