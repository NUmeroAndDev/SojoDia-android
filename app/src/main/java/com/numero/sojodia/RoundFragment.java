package com.numero.sojodia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RoundFragment extends Fragment {

    private TextView tkTimeText[] = new TextView[3];
    private TextView tndTimeText[] = new TextView[3];
    private TextView day;
    private ViewTask viewTask;
    private int round;

    public RoundFragment(){
    }

    public static RoundFragment newInstance(int round) {
        RoundFragment fragment = new RoundFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ROUND, round);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, null);

        round = getArguments().getInt(Constants.ROUND);

        int tkId[] = {R.id.tk_next,R.id.tk_after_next,R.id.tk_limit};
        int tndId[] = {R.id.tnd_next,R.id.tnd_after_next,R.id.tnd_limit};
        for(int i = 0;i < tkId.length;i++){
            tkTimeText[i] = (TextView)view.findViewById(tkId[i]);
        }
        for(int i = 0;i < tndId.length;i++){
            tndTimeText[i] = (TextView)view.findViewById(tndId[i]);
        }

        day = (TextView)view.findViewById(R.id.day);

        TextView tkStation = (TextView) view.findViewById(R.id.tk_station);
        TextView tndStation = (TextView) view.findViewById(R.id.tnd_station);
        tkStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), getActivity().getLayoutInflater(), Constants.ROUTE_TK, round);
                dialog.show();
            }
        });
        tndStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), getActivity().getLayoutInflater(), Constants.ROUTE_TND, round);
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        viewTask = new ViewTask(getActivity(), tkTimeText, tndTimeText, day, round);
        viewTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewTask.cancel(true);
    }
}
