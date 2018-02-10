package com.numero.sojodia.repository;


public interface IConfigRepository {
    boolean isTodayUpdateChecked();

    void successUpdate();

    boolean canUpdate();
}
