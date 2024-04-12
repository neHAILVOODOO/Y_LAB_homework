package ru.ylab.in.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class WeightType implements TrainingTypeInterface {

    int index;
    private LocalDate trainingDate;
    String trainingName;
    int repeats;
    int approaches;
    int weight;

    String param1Name = "Повторения";
    String param2Name = "Подходы";
    String param3Name = "Вес";

    String type;

    public WeightType(LocalDate trainingDate, String trainingName, int repeats, int approaches, int weight, String type) {

        super();
        this.trainingDate = trainingDate;
        this.trainingName = trainingName;
        this.repeats = repeats;
        this.approaches = approaches;
        this.weight = weight;
        this.type = type;
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
    public int getParameter1() {
        return repeats;
    }
    @Override
    public int getParameter2() {
        return approaches;
    }
    @Override
    public int getParameter3() {
        return weight;
    }

    @Override
    public void setParam1(int param1) {
        this.repeats = param1;
    }

    @Override
    public void setParam2(int param2) {
        this.approaches = param2;
    }

    @Override
    public void setParam3(int param3) {
        this.weight = param3;
    }


    public String toString() {

        return "Тип тренировки: " + this.type + "\n" +
                "Вид тренировки: " + this.trainingName + "\n" +
                "Дата проведения тренировки: " + this.trainingDate + "\n" +
                "Подходы: " + this.approaches + "\n" +
                "Повторения: " + this.repeats + "\n" +
                "Вес: " + this.weight;
    }


}
