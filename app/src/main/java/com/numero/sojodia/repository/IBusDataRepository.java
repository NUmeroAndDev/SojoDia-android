package com.numero.sojodia.repository;

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;

import java.util.List;

import io.reactivex.Single;

public interface IBusDataRepository {

    Single<List<BusTime>> loadBusData(BusDataFile busDataFile);

}
