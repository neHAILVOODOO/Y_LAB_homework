package ru.ylab.in.controllers;

import ru.ylab.in.models.CardioType;
import ru.ylab.in.models.TrainingTypeInterface;
import ru.ylab.in.models.User;
import ru.ylab.in.models.WeightType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DiaryController {

    /**
     * Метод для вывода всех тренировок пользователя
     * @param currentUser пользователь, чьи тренировки мы выводим
     *
     */
    public void printAllTrainingsOfUser(User currentUser) {

        updateTrainingIndexes(currentUser);

        if (!currentUser.trainingList.isEmpty()) {
            for (TrainingTypeInterface training : currentUser.trainingList) {
                System.out.println("\n(" + (training.getIndex() + 1) + ")\n");
                System.out.println(training.toString());
            }
        } else {
            System.out.println("\nУ вас нет ни одной записи о тренировках =( \n");
        }
    }

    /**
     * Метод для добавления тренировки пользователя в свой дневник.
     * @param workoutTypes Map, где ключ соответствует упражнению (Кардио - "бег", "велосипед", "бег в гору").
     *                     Нужен для выбора упражнения и проверки, были ли добавлено такое упражнение сегодня
     *
     * @param workoutParametres Map, где ключ соответствует параметрам (Кардио - "расстояние", "сожж. калории", "затр. время"
     *                          нужен для того, чтобы у администратора была возможность динамически расширять списки упражнений.
     *                          В данном методе определяет какой ключ соответствует каким параметрам.
     *
     * @param currentDate Сегодняшняя дата, нужна для проверки, было ли добавлено сегодня такое упражнение.
     *                    Также, при создании упражнения, эта дата будет ставиться ему в параметр "date"
     *
     * @param currentUser Пользователь, которому добавляется тренировка
     *
     */

    public void addTrainingToUser (Map<String, List<String>> workoutTypes,
                                  Map<String, List<String>> workoutParametres,
                                  LocalDate currentDate,
                                   User currentUser) {

        int param1 = 0;
        int param2 = 0;
        int param3 = 0;

        String param1Name;
        String param2Name;
        String param3Name;


        boolean selectingTrainingType = true;
        boolean selectingTrainingKind = true;

        Scanner sc = new Scanner(System.in);

        System.out.println("\nКакой тип тренировки вы хотите добавить?\n");


        for (String workoutType : workoutTypes.keySet()) {
            System.out.println(workoutType);
        }



        while (selectingTrainingType) {

            boolean notExisting = true;
            boolean enterNumbers = true;

            boolean dontAdd = false;

            System.out.print("\nВведите название нужного типа тренировки: \n");
            String selectedWorkoutType = sc.nextLine();

            if (workoutTypes.containsKey(selectedWorkoutType)) {

                List<String> workouts = workoutTypes.get(selectedWorkoutType);
                List<String> workoutParametresList = workoutParametres.get(selectedWorkoutType);

                param1Name = workoutParametresList.get(0);
                param2Name = workoutParametresList.get(1);
                param3Name = workoutParametresList.get(2);

                for (int i = 0; i < workouts.size(); i++) {
                    System.out.println((i + 1) + ". " + workouts.get(i));
                }


                int workoutIndex = 0;

                if (workouts.isEmpty()) {
                    selectingTrainingKind = false;
                    selectingTrainingType = false;
                    enterNumbers = false;

                    System.out.println("\nВ этом типе тренировок пока не существует упражнений. Попробуйте позже!\n");
                    break;
                }


                while (selectingTrainingKind) {

                    try {
                        System.out.print("\nВведите номер тренировки, которую вы выполнили: \n");
                        workoutIndex = sc.nextInt();

                        if (workoutIndex >= 0 && workoutIndex <= workouts.size()) {
                            selectingTrainingKind = false;
                        }


                    } catch (Exception e) {
                        System.out.println("\nВведите номер, соответствующий команде!\n");
                        sc.nextLine();
                    }

                }

                for (TrainingTypeInterface training : currentUser.trainingList) {
                    if (training.getTrainingName().equals(workouts.get(workoutIndex - 1)) && training.getDate().equals(LocalDate.now())) {
                        selectingTrainingKind = false;
                        selectingTrainingType = false;
                        enterNumbers = false;

                        dontAdd = true;
                    }
                }


                while (enterNumbers) {


                    try {

                        System.out.print("\nВведите первый параметр (" + param1Name + "):\n");
                        param1 = sc.nextInt();
                        System.out.print("\nВведите второй параметр (" + param2Name + "):\n");
                        param2 = sc.nextInt();
                        System.out.print("\nВведите третий параметр (" + param3Name + "):\n");
                        param3 = sc.nextInt();
                        System.out.print("\n");

                        enterNumbers = false;

                    } catch (Exception e) {
                        System.out.println("Введите число!");
                        sc.nextLine();
                    }
                }

                if (!dontAdd) {

                    if (selectedWorkoutType.equals(workoutTypes.keySet().toArray()[0])) {

                        TrainingTypeInterface training = new CardioType(currentDate, workouts.get(workoutIndex - 1), param1, param2, param3);
                        training.setIndex(currentUser.getTrainingIndex());
                        currentUser.setTrainingIndex(currentUser.getTrainingIndex() + 1);
                        selectingTrainingType = false;
                        System.out.println("Тренировка добавлена! \n" + training.toString());
                        currentUser.trainingList.add(training);

                    } else if (selectedWorkoutType.equals(workoutTypes.keySet().toArray()[1])) {

                        TrainingTypeInterface training = new WeightType(currentDate, workouts.get(workoutIndex - 1), param1, param2, param3, (String) workoutTypes.keySet().toArray()[1]);
                        training.setIndex(currentUser.getTrainingIndex());
                        currentUser.setTrainingIndex(currentUser.getTrainingIndex() + 1);
                        selectingTrainingType = false;
                        System.out.println("Тренировка добавлена! \n" + training.toString());
                        currentUser.trainingList.add(training);

                    }


                    for (int i = 2; i < workoutTypes.keySet().toArray().length; i++) {
                        if (selectedWorkoutType.equals(workoutTypes.keySet().toArray()[i])) {

                            TrainingTypeInterface training = new WeightType(currentDate, workouts.get(workoutIndex - 1), param1, param2, param3, (String) workoutTypes.keySet().toArray()[i]);
                            training.setIndex(currentUser.getTrainingIndex());
                            currentUser.setTrainingIndex(currentUser.getTrainingIndex() + 1);
                            selectingTrainingType = false;
                            System.out.println("Тренировка добавлена! \n" + training.toString());
                            currentUser.trainingList.add(training);

                            notExisting = true;

                            break;
                        } else {
                            notExisting = false;
                        }
                    }
                    if (!notExisting) {
                        System.out.println("Введено неверное значение!\n");
                    }
                }
                else {
                    System.out.println("Это упражнение уже было добавлено сегодня.\n");
                }
            }
        }

        updateTrainingIndexes(currentUser);
    }

    /**
     * Метод для обновления индексов тренировок текущего юзера (нужен для метода удаления тренировки, чтобы обновить их индексы в списке)
     * @param currentUser пользователь, у которого в листе trainingList обновляются тренировки
     *
     */

    private void updateTrainingIndexes(User currentUser) {

        int index = 0;


        for (TrainingTypeInterface training : currentUser.trainingList) {
            currentUser.setTrainingIndex(index);
            training.setIndex(currentUser.getTrainingIndex());
            index++;

        }
    }

    /**
     * Метод для вывода информации о возможностях дневника (для админа и обычного юзера)
     * @param currentUser текущий пользователь. Нужен для проверки роли: если админ - то доступна консоль.
     *
     */

    public String profileInfo(User currentUser) {

        String message = null;

        if (currentUser.getRole().equals("USER")) {
            message = "\nВы находитесь в своем личном кабинете. Здесь вам доступны такие функции, как: \n\n" +
                    "1. Добавить информацию о тренировке\n" +
                    "2. Редактировать информацию о выбранной тренировке\n" +
                    "3. Удалить тренировку\n" +
                    "4. Вывести записи тренировок\n" +
                    "5. Статистика тренировок\n" +
                    "6. Выйти в главное меню\n\n" +
                    "Для выбора действия введите соответствующую цифру: \n";
        }

       else if (currentUser.getRole().equals("ADMIN")) {

            message = "\nВы находитесь в своем личном кабинете. Здесь вам доступны такие функции, как: \n\n" +
                    "1. Добавить информацию о тренировке\n" +
                    "2. Редактировать информацию о выбранной тренировке\n" +
                    "3. Удалить тренировку\n" +
                    "4. Вывести записи тренировок\n" +
                    "5. Статистика тренировок\n" +
                    "6. Выйти в главное меню\n\n" +
                    "7. Консоль админа\n\n" +
                    "Для выбора действия введите соответствующую цифру: \n";
        }
    return message;
    }

    /**
     * Метод для запоминания имени пользователя. При каждом входе в дневник будет выводиться соответствующее сообщение.
     * @param currentUser текущий пользователь, который входит в дневник.
     *
     */

    public void greetingsInDiary(User currentUser) {

        if (currentUser.getName() == null) {

            System.out.println("\nКак мы можем к вам обращаться?");
            Scanner scanner = new Scanner(System.in);
            currentUser.setName(scanner.nextLine());

            System.out.println("\nПриятно познакомиться, " + currentUser.getName() + "!");
        } else {
            System.out.println("\nЗдравствуйте," + currentUser.getName() + "!");
        }

    }

    /**
     * Метод для удаления выбранной тренировки из листа тренировок пользователя.
     * @param currentUser текущий пользователь, который входит в дневник.
     *
     */

    public void deleteTraining(User currentUser) {

        int deleteIndex = 0;

        Scanner sc = new Scanner(System.in);

        printAllTrainingsOfUser(currentUser);

        boolean enterIndex = true;

        while (enterIndex) {

            try {

                System.out.println("Введите номер тренировки, которую хотите удалить: \n");
                deleteIndex = sc.nextInt();

                if ((deleteIndex < 1 || deleteIndex > currentUser.getTrainingIndex() + 1)) {
                    System.out.println("Вы ввели неверное значение! \n");
                } else {
                    enterIndex = false;
                }

                currentUser.trainingList.remove(deleteIndex - 1);

            } catch (Exception e) {
                System.out.println("Введите номер!\n");
                sc.nextLine();
            }


        }
        if (!currentUser.trainingList.isEmpty()) {
            System.out.println("Обновленный список тренировок: \n" +
                    "______________________________________");

            printAllTrainingsOfUser(currentUser);
        } else {
            System.out.println("Теперь лист пуст! Добавьте новую запись!\n");
        }

        updateTrainingIndexes(currentUser);
    }

    /**
     * Метод для обновления данных тренировки текущего пользователя
     * @param currentUser текущий пользователь, который хочет изменить свои тренировки
     *
     */

    public void editTraining(User currentUser) {

        boolean editing = true;
        boolean newParametersEntering = true;
        int editIndex = 0;

        int param1 = 0;
        int param2 = 0;
        int param3 = 0;

        Scanner sc = new Scanner(System.in);

        printAllTrainingsOfUser(currentUser);

        while (editing) {

            try {
                System.out.println("Введите номер тренировки, которую хотите изменить: \n");

                editIndex = sc.nextInt();

                if ((editIndex < 1 || editIndex > currentUser.getTrainingIndex() + 1)) {
                    System.out.println("Вы ввели неверное значение! \n");
                } else {
                    editing = false;
                }

            } catch (Exception e) {
                System.out.println("Введите номер!\n");
                sc.nextLine();
            }

        }

        while (newParametersEntering) {

            try {

                System.out.println("Введите новое значение параметра <" + currentUser.trainingList.get(editIndex - 1).getParam1Name() + ">:\n");
                param1 = sc.nextInt();
                currentUser.trainingList.get(editIndex - 1).setParam1(param1);
                System.out.println("Введите новое значение параметра <" + currentUser.trainingList.get(editIndex - 1).getParam2Name() + ">:\n");
                param2 = sc.nextInt();
                currentUser.trainingList.get(editIndex - 1).setParam2(param2);
                System.out.println("Введите новое значение параметра <" + currentUser.trainingList.get(editIndex - 1).getParam3Name() + ">:\n");
                param3 = sc.nextInt();
                currentUser.trainingList.get(editIndex - 1).setParam3(param3);

                System.out.println("Обновленные данные тренировки: \n\n" + currentUser.trainingList.get(editIndex - 1).toString());

                newParametersEntering = false;
                updateTrainingIndexes(currentUser);
            } catch (Exception e) {
                System.out.println("Вам нужно ввести число! \n Введите заново все параметры. \n");
                sc.nextLine();
            }

        }

    }

    /**
     * Метод для подсчета пройденного расстояния в разрезе времени
     * @param trainings лист тренировок пользователя, из которого достаются тренировки для подсчета
     * @param startDate дата, ОТ которой отсчитываем
     * @param endDate  дата, ДО которой досчитываем
     */

    public static int countDistanceByDate(List<TrainingTypeInterface> trainings, LocalDate startDate, LocalDate endDate) {
        int countDistance = 0;

        for (TrainingTypeInterface training : trainings) {
            LocalDate trainingDate = training.getDate();
           if (trainings.get(training.getIndex()).getParam1Name().equals("Расстояние")) {
               if (!trainingDate.isBefore(startDate) && !trainingDate.isAfter(endDate)) {
                   countDistance += trainings.get(training.getIndex()).getParameter1();
               }
           }
        }

        return countDistance;
    }

    /**
     * Метод для подсчета потраченных калорий в разрезе времени
     * @param trainings лист тренировок пользователя, из которого достаются тренировки для подсчета
     * @param startDate дата, ОТ которой отсчитываем
     * @param endDate  дата, ДО которой досчитываем
     */

    public static int countCaloriesByDate(List<TrainingTypeInterface> trainings, LocalDate startDate, LocalDate endDate) {
        int countDistance = 0;

        for (TrainingTypeInterface training : trainings) {
            LocalDate trainingDate = training.getDate();
            if (trainings.get(training.getIndex()).getParam2Name().equals("Сожженные калории")) {
                if (!trainingDate.isBefore(startDate) && !trainingDate.isAfter(endDate)) {
                    countDistance += trainings.get(training.getIndex()).getParameter2();
                }
            }
        }

        return countDistance;
    }

    /**
     * Метод для подсчета пройденного расстояния за сегодня
     * @param currentUser пользователь, у которого считаем пройденное расстояние
     */

    void calculateTodaysDistance(User currentUser) {

        LocalDate currentDate = LocalDate.now();
        LocalDate lastDay = currentDate.minusDays(1);
        int trainingsLastDay = countDistanceByDate(currentUser.trainingList, lastDay, currentDate);

        System.out.println("Количество шагов за последний день: " + trainingsLastDay + "\n");

    }

    /**
     * Метод для подсчета пройденного расстояния за неделю
     * @param currentUser пользователь, у которого считаем пройденное расстояние
     */


    void calculateLastWeekDistance(User currentUser) {

        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeek = currentDate.minusWeeks(1);
        int trainingsLastWeek = countDistanceByDate(currentUser.trainingList, lastWeek, currentDate);

        System.out.println("Количество шагов за последнюю неделю: " + trainingsLastWeek + "\n");

    }

    /**
     * Метод для подсчета пройденного расстояния за месяц
     * @param currentUser пользователь, у которого считаем пройденное расстояние
     */


    void calculateLastMonthDistance(User currentUser) {

        LocalDate currentDate = LocalDate.now();
        LocalDate lastMonth = currentDate.minusMonths(1);
        int trainingsLastMonth = countDistanceByDate(currentUser.trainingList, lastMonth, currentDate);

        System.out.println("Количество шагов за последний месяц: " + trainingsLastMonth + "\n");

    }

    /**
     * Метод для подсчета сожженных калорий за последнее кол-во введенных дней
     * @param currentUser пользователь, у которого считаем сожженные калории
     */


    void calculateBurnedCalories(User currentUser) {

        int days = 0;

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Введите количество дней, за которые вы желаете посчитать сожженные калории:\n");
            days = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Введите число!\n");
            sc.nextLine();
        }


        LocalDate currentDate = LocalDate.now();
        LocalDate lastDays = currentDate.minusDays(days);
        int burnedCaloriesForLastDays = countCaloriesByDate(currentUser.trainingList, lastDays, currentDate);

        System.out.println("Количество сожженных калорий за последние " + days + " дней: " + burnedCaloriesForLastDays + "\n");

    }

    /**
     * Метод для вывода количества выполненых тренировок
     * @param currentUser пользователь, у которого считаем кол-во тренировок
     */

    void printAllTrainingsCount(User currentUser) {
        System.out.println("Общее количество тренировок: " + (currentUser.getTrainingIndex() + 1) + "\n");
    }

    /**
     * Метод для вывода меню статистики
     * @param currentUser нужен для проверки, пуст ли его лист тренировок. Если пуст, то доступа к статистике нет
     */

    public void printStats(User currentUser) {

        if (!currentUser.trainingList.isEmpty()) {

            boolean chooseStatsOption = true;
            Scanner sc = new Scanner(System.in);

            int choose = 0;

            System.out.println("Выберите нужный вам пункт: \n" +
                    "1. Пройденное расстояние за день\n" +
                    "2. Пройденное расстояние за неделю\n" +
                    "3. Пройденное расстояние за месяц\n" +
                    "4. Сожженные калории за выбранный промежуток времени\n" +
                    "5. Общее количество тренировок\n" +
                    "6. Выйти обратно в меню\n" +

                    "Для выбора действия введите соответствующую цифру: \n");


            while (chooseStatsOption) {

                try {
                    choose = sc.nextInt();

                    if (choose == 1) {
                        calculateTodaysDistance(currentUser);
                    } else if (choose == 2) {
                        calculateLastWeekDistance(currentUser);
                    } else if (choose == 3) {
                        calculateLastMonthDistance(currentUser);
                    } else if (choose == 4) {
                        calculateBurnedCalories(currentUser);
                    } else if (choose == 5) {
                        printAllTrainingsCount(currentUser);
                    } else if (choose == 6) {
                        chooseStatsOption = false;
                    } else {
                        System.out.println("Не существует функции под таким номером!\n");
                    }

                } catch (Exception e) {
                    System.out.println("Введите число!\n");
                }
            }
        } else {
            System.out.println("У вас нет ни одной записи о тренировках =( \n");
        }
    }

}

