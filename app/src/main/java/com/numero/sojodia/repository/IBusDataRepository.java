package com.numero.sojodia.repository;

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;

import java.util.List;

import io.reactivex.Observable;

public interface IBusDataRepository {

    Observable<List<BusTime>> loadBusData(BusDataFile busDataFile);

}
