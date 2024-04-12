package ru.ylab.in.controllers;

import org.junit.jupiter.api.Test;
import ru.ylab.in.models.CardioType;
import ru.ylab.in.models.TrainingTypeInterface;
import ru.ylab.in.models.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiaryControllerTest {

    DiaryController diaryController = new DiaryController();
    User currentUser = new User();

    @Test
    public void testPrintAllTrainingsOfUser_whenUserHasNoTrainings() {

        currentUser.setTrainingList(new ArrayList<>());


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        diaryController.printAllTrainingsOfUser(currentUser);

        String expectedOutput = "\nУ вас нет ни одной записи о тренировках =( \n";

        String actualOutput = outputStream.toString();

        assertTrue(actualOutput.contains(expectedOutput));
    }


    @Test
    void countTrainingsByDate_shouldCountDistanceTrainingsInDateRange() {

        List<TrainingTypeInterface> trainings = List.of(
                new CardioType(LocalDate.of(2023, 3, 8), "Бег", 10, 60, 500),
                new CardioType(LocalDate.of(2023, 3, 10), "Плавание", 1500, 90, 750),
                new CardioType(LocalDate.of(2023, 3, 12), "Велоспорт", 20, 120, 600)
        );
        LocalDate startDate = LocalDate.of(2023, 3, 9);
        LocalDate endDate = LocalDate.of(2023, 3, 11);


        int countDistance = DiaryController.countDistanceByDate(trainings, startDate, endDate);


        assertEquals(10, countDistance);
    }

    @Test
    public void test_CalculateTodaysDistance() {


        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);

        CardioType trainingYesterday = new CardioType(yesterday, "Тренировка вчера", 1000, 60, 200);
        CardioType trainingToday1 = new CardioType(currentDate, "Тренировка сегодня 1", 1500, 45, 250);
        CardioType trainingToday2 = new CardioType(currentDate, "Тренировка сегодня 2", 2000, 70, 300);

        List<TrainingTypeInterface> trainingList = new ArrayList<>();
        trainingList.add(trainingYesterday);
        trainingList.add(trainingToday1);
        trainingList.add(trainingToday2);

        currentUser.setTrainingList(trainingList);

        diaryController.calculateTodaysDistance(currentUser);

        int expectedDistance = trainingToday1.getDistance() + trainingToday2.getDistance();

        String expectedOutput = "Количество шагов за последний день: " + expectedDistance + "\n";

        assertEquals(expectedOutput, "Количество шагов за последний день: 3500\n");
    }


}









