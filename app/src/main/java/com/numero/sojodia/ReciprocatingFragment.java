package com.numero.sojodia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numero.sojodia.Utils.Constants;

public class ReciprocatingFragment extends Fragment {

    private TextView tkTimeTextViews[] = new TextView[3];
    private TextView tndTimeTextViews[] = new TextView[3];
    private TextView dateTextView;
    private CountdownTask countdownTask;
    private int round;

    public ReciprocatingFragment() {
    }

    public static ReciprocatingFragment newInstance(int round) {
        ReciprocatingFragment fragment = new ReciprocatingFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.RECIPROCATING, round);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reciprocating_fragment, null);

        round = getArguments().getInt(Constants.RECIPROCATING);
        dateTextView = (TextView) view.findViewById(R.id.date);

        initTimeTextViews(view);
        initCardClickListener(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        countdownTask = new CountdownTask(getActivity(), tkTimeTextViews, tndTimeTextViews, dateTextView, round);
        countdownTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onStop() {
        super.onStop();
        countdownTask.cancel(true);
    }

    private void initTimeTextViews(View view) {
        int tkTextViewIDs[] = {R.id.tk_next, R.id.tk_after_next, R.id.tk_limit};
        int tndTextViewIDs[] = {R.id.tnd_next, R.id.tnd_after_next, R.id.tnd_limit};
        for (int i = 0; i < tkTextViewIDs.length; i++) {
            tkTimeTextViews[i] = (TextView) view.findViewById(tkTextViewIDs[i]);
        }
        for (int i = 0; i < tndTextViewIDs.length; i++) {
            tndTimeTextViews[i] = (TextView) view.findViewById(tndTextViewIDs[i]);
        }
    }

    private void initCardClickListener(View view){
        CardView tkCardView = (CardView) view.findViewById(R.id.card_tk);
        CardView tndCardView = (CardView) view.findViewById(R.id.card_tnd);
        tkCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), Constants.ROUTE_TK, round);
                dialog.show();
            }
        });
        tndCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog dialog = new TimeTableDialog(getActivity(), Constants.ROUTE_TND, round);
                dialog.show();
            }
        });
    }
}
