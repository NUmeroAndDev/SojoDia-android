package com.numero.sojodia.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.numero.sojodia.Manager.BusScheduleClockManager;
import com.numero.sojodia.View.TimeTableDialog;
import com.numero.sojodia.R;
import com.numero.sojodia.Util.Constants;

public class BusScheduleFragment extends Fragment implements BusScheduleClockManager.CallbackOnProgress {

    private TextView tkBusTimeTextViews[] = new TextView[3];
    private TextView tndBusTimeTextViews[] = new TextView[3];
    private TextView dateTextView;
    private BusScheduleClockManager busScheduleClockManager;
    private int reciprocating;

    public BusScheduleFragment() {
    }

    public static BusScheduleFragment newInstance(int reciprocating) {
        BusScheduleFragment fragment = new BusScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.RECIPROCATING, reciprocating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_schedule_fragment, null);

        reciprocating = getArguments().getInt(Constants.RECIPROCATING);
        dateTextView = (TextView) view.findViewById(R.id.date);

        initTimeTextViews(view);
        initButtonClickListener(view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        busScheduleClockManager = new BusScheduleClockManager(getContext(), reciprocating);
        busScheduleClockManager.setCallbackOnProgress(this);
        busScheduleClockManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onStop() {
        super.onStop();
        busScheduleClockManager.cancel(true);
    }

    private void initTimeTextViews(View view) {
        int tkTextViewIDs[] = {R.id.text_bus_time_tk, R.id.text_next_bus_time_tk, R.id.text_limit_time_tk};
        int tndTextViewIDs[] = {R.id.text_bus_time_tnd, R.id.text_next_bus_time_tnd, R.id.text_limit_time_tnd};
        for (int i = 0; i < tkTextViewIDs.length; i++) {
            tkBusTimeTextViews[i] = (TextView) view.findViewById(tkTextViewIDs[i]);
        }
        for (int i = 0; i < tndTextViewIDs.length; i++) {
            tndBusTimeTextViews[i] = (TextView) view.findViewById(tndTextViewIDs[i]);
        }
    }

    private void initButtonClickListener(View view) {
        ImageButton tkTimeTableButton = (ImageButton) view.findViewById(R.id.time_table_tk_button);
        ImageButton tndTimeTableButton = (ImageButton) view.findViewById(R.id.time_table_tnd_button);
        tkTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), Constants.ROUTE_TK, reciprocating);
                dialog.show();
            }
        });
        tndTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), Constants.ROUTE_TND, reciprocating);
                dialog.show();
            }
        });
    }

    @Override
    public void onProgressTK(String nextBusTimeString, String nextNextBusTimeString, String limitTimeString, int resColor) {
        tkBusTimeTextViews[0].setText(nextBusTimeString);
        tkBusTimeTextViews[1].setText(nextNextBusTimeString);
        tkBusTimeTextViews[2].setText(limitTimeString);
        tkBusTimeTextViews[2].setTextColor(ContextCompat.getColor(getContext(), resColor));
    }

    @Override
    public void onProgressTND(String nextBusTimeString, String nextNextBusTimeString, String limitTimeString, int resColor) {
        tndBusTimeTextViews[0].setText(nextBusTimeString);
        tndBusTimeTextViews[1].setText(nextNextBusTimeString);
        tndBusTimeTextViews[2].setText(limitTimeString);
        tndBusTimeTextViews[2].setTextColor(ContextCompat.getColor(getContext(), resColor));
    }

    @Override
    public void onHandlerDateChanged(String todayDateString) {
        dateTextView.setText(todayDateString);
    }
}
