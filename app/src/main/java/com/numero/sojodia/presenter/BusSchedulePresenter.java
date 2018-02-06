package com.numero.sojodia.presenter;

import com.numero.sojodia.contract.BusScheduleContract;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.repository.BusDataRepository;

import java.util.ArrayList;
import java.util.List;

public class BusSchedulePresenter implements BusScheduleContract.Presenter {

    private final BusDataRepository busDataRepository;

    private final BusScheduleContract.View view;

    private List<BusTime> tkBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListReturn = new ArrayList<>();

    public BusSchedulePresenter(BusDataRepository busDataRepository, BusScheduleContract.View view) {
        this.busDataRepository = busDataRepository;
        this.view = view;

        tkBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TK_TO_KUTC).blockingFirst();
        tkBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TK).blockingFirst();
        tndBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TND_TO_KUTC).blockingFirst();
        tndBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TND).blockingFirst();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
