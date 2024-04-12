package ru.ylab.in.models;

import java.time.LocalDate;

public interface TrainingTypeInterface {


    void setDate(LocalDate trainingDate);

    void setTrainingName(String trainingName);

    String getTrainingName();
    LocalDate getDate();

    int getParameter1();

    int getParameter2();

    int getParameter3();

     void setIndex(int index);
     int getIndex();

    void setParam1(int param1);

    void setParam2(int param2);

    void setParam3(int param3);

    public String getParam1Name();

    public String getParam2Name();

    public String getParam3Name();

}
