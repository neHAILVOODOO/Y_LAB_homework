package ru.ylab.in.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CardioType implements TrainingTypeInterface {


   private int index;
    private int distance;
    private int burnedCalories;
    private int time;
    private  LocalDate trainingDate;
    private String trainingName;

    public String param1Name = "Расстояние";
    public String param2Name = "Сожженные калории";
    public String param3Name = "Затраченное время";




    public CardioType(LocalDate trainingDate, String trainingName, int distance, int time, int burnedCalories) {

        super();
        this.trainingDate = trainingDate;
        this.trainingName = trainingName;
        this.distance = distance;
        this.burnedCalories = burnedCalories;
        this.time = time;

    }

    @Override
    public String getParam1Name() {
        return param1Name;
    }

    @Override
    public String getParam2Name() {
        return param2Name;
    }

    @Override
    public String getParam3Name() {
        return param3Name;
    }

    @Override
    public int getParameter1() {
        return distance;
    }
    @Override
    public int getParameter2() {
        return burnedCalories;
    }
    @Override
    public int getParameter3() {
        return time;
    }

    @Override
    public void setDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    @Override
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    @Override
    public String getTrainingName() {
        return trainingName;
    }

    @Override
    public LocalDate getDate() {
        return trainingDate;
    }

    @Override
    public void setParam1(int param1) {
        this.distance = param1;
    }

    @Override
    public void setParam2(int param2) {
        this.burnedCalories = param2;
    }

    @Override
    public void setParam3(int param3) {
        this.time = param3;
    }

    public String toString() {

        return "Тип тренировки: Кардио \n" +
                "Вид тренировки: " + this.trainingName + "\n" +
                "Дата проведения тренировки: " + this.trainingDate + "\n" +
                "Пройденная дистанция: " + this.distance + "\n" +
                "Сожженные калории: " + this.burnedCalories + "\n" +
                "Потраченное время: " + this.time + "\n";
    }



}
