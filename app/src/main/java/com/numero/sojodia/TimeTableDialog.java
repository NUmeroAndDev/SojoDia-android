package com.numero.sojodia;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeTableDialog {

    private AlertDialog.Builder builder;

    private ArrayList<TableFormat> listTable;
    private TimeList timeList;
    private ArrayList<TimeFormat> list;
    private int route, round;

    TimeTableDialog(Context context, LayoutInflater inflater, int route, int round) {
        String roundTitles[] = {"登校", "下校"};
        String stations[] = {"JR高槻駅北", "JR富田駅"};

        View view = inflater.inflate(R.layout.table_dialog_layout, null);
        builder = new AlertDialog.Builder(context);
        builder.setTitle(roundTitles[round]);
        builder.setView(view);
        builder.setPositiveButton("閉じる", null);

        this.route = route;
        this.round = round;

        list = new ArrayList<>();
        timeList = new TimeList();
        listTable = new ArrayList<>();

        TextView station = (TextView) view.findViewById(R.id.station);
        station.setText(stations[route]);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.Adapter adapter = new CustomAdapter(listTable, context);

        recyclerView.setAdapter(adapter);
    }

    public void show() {
        timeList.setAll(list, route, round);
        setList();
        AlertDialog dialog = builder.create();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void setList() {
        for (int i = 0; i < 18; i++) {
            TableFormat buffer = new TableFormat();
            buffer.setTime((i + 6 > 9 ? "" + String.valueOf(i + 6) : "0" + String.valueOf(i + 6)));
            listTable.add(buffer);
        }
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).week) {
                case 0:
                    listTable.get(list.get(i).hour - 6).addTimeWeekday((list.get(i).min > 9 ? "" + String.valueOf(list.get(i).min) : "0" + String.valueOf(list.get(i).min)));
                    break;
                case 1:
                    listTable.get(list.get(i).hour - 6).addTimeSaturday((list.get(i).min > 9 ? "" + String.valueOf(list.get(i).min) : "0" + String.valueOf(list.get(i).min)));
                    break;
                case 2:
                    listTable.get(list.get(i).hour - 6).addTimeSunday((list.get(i).min > 9 ? "" + String.valueOf(list.get(i).min) : "0" + String.valueOf(list.get(i).min)));
                    break;
                case 3:
                    listTable.get(list.get(i).hour - 6).addTimeSundaySp((list.get(i).min > 9 ? "" + String.valueOf(list.get(i).min) : "0" + String.valueOf(list.get(i).min)));
                    break;
            }
        }
    }
}
