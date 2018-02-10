package com.numero.sojodia.contract;

import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.presenter.IPresenter;
import com.numero.sojodia.view.IView;

import java.util.List;

public interface BusScheduleContract {

    interface View extends IView<Presenter> {

        void showTkBusTimeList(List<BusTime> busTimeList);

        void showTndBusTimeList(List<BusTime> busTimeList);

        void selectTkCurrentBusPosition(int position);

        void startTkCountDown(BusTime busTime);

        void selectTndCurrentBusPosition(int position);

        void startTndCountDown(BusTime busTime);

        void showTkNextButton();

        void showTkPreviewButton();

        void showTkNoBusLayout();

        void showTndNextButton();

        void showTndPreviewButton();

        void showTndNoBusLayout();

        void hideTkNextButton();

        void hideTkPreviewButton();

        void hideTkNoBusLayout();

        void hideTndNextButton();

        void hideTndPreviewButton();

        void hideTndNoBusLayout();

    }

    interface Presenter extends IPresenter {
        void onTimeChanged(int week);

        void nextTkBus();

        void previewTkBus();

        void nextTndBus();

        void previewTndBus();
    }
}
