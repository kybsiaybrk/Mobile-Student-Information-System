package com.example.unimobilobs;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unimobilobs.adapter.CalenderAdapter;
import com.example.unimobilobs.data.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TakvimActivity extends AppCompatActivity {

    private TextView monthYearTextView;
    private RecyclerView calendarRecyclerView;
    private Calendar currentMonth;
    private CalenderAdapter calendarAdapter;
    private List<Day> dayList;

    private HashMap<String, String> eventsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_takvim);

        Window window = getWindow();
        int appStatusColor = ContextCompat.getColor(this, R.color.appstatusbar);
        getWindow().setStatusBarColor(appStatusColor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.appbackground));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        monthYearTextView = findViewById(R.id.monthYearTextView);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        currentMonth = Calendar.getInstance();
        dayList = new ArrayList<>();
        calendarAdapter = new CalenderAdapter(dayList);
        calendarRecyclerView.setAdapter(calendarAdapter);

        eventsMap = new HashMap<>();
        loadEvents();

        updateCalendar();

        ImageButton previousMonthButton = findViewById(R.id.previousMonthButton);
        ImageButton nextMonthButton = findViewById(R.id.nextMonthButton);

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });
    }

    private void updateCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearTextView.setText(sdf.format(currentMonth.getTime()));

        dayList.clear();

        Calendar calendar = (Calendar) currentMonth.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < firstDayOfWeek; i++) {
            dayList.add(new Day("", ""));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            String day = String.valueOf(i);
            String key = sdf.format(currentMonth.getTime()) + " " + day;
            String event = eventsMap.get(key);
            dayList.add(new Day(day, event));
        }

        calendarAdapter.notifyDataSetChanged();
    }

    private void loadEvents() {
        // Bu kısımda örnek olaylar yükleniyor
        eventsMap.put("April 2024 10", "Meeting with Bob");
        eventsMap.put("April 2024 15", "Dentist Appointment");
        eventsMap.put("April 2024 25", "Conference");
    }
}
