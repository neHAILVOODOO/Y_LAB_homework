package ru.ylab;


import ru.ylab.in.controllers.AdminController;
import ru.ylab.in.controllers.DiaryController;
import ru.ylab.in.controllers.SessionController;
import ru.ylab.in.models.SessionPanelInterface;
import ru.ylab.in.models.User;

import java.time.LocalDate;
import java.util.*;

public class SessionPanel implements SessionPanelInterface {

    //Обработка запросов в 60 фпс
    int FPS = 60;
    Long ID = 0L;


    //Состояния сессии
    public int sessionState; //Сессия
    public final int sessionTitleState = 0; //Главный экран
    public final int sessionLoginState = 1; //Логин
    public final int sessionRegistrationState = 2; //Регистрация
    public final int sessionCheckingDiaryState = 3; //Просмотр дневника
    public final int sessionChooseOptionState = 4; //Выбор нужной нам панели
    public final int sessionInformationState = 5; //Информация о проекте и авторе


    //Состояния дневника

    public int diaryState = 1; //Собственно, дневник
    public final int diaryGreetingsState = 1; //Здравствуйте!
    public final int diaryChooseOptionState = 2; //Выбор нужной нам опции
    public final int diaryAddTrainingState = 3; //Добавить тренировку в дневник
    public final int diaryEditTrainingState = 4; //Изменить тренировку
    public final int diaryDeleteTrainingState = 5; //Удалить тренировку
    public final int diaryStatsState = 6; // Статистика по тренировкам
    public final int diaryAdminConsoleState = 7; // Перейти в консоль админа

    //Состояния консоли админа

    public int adminConsoleState = 1; //Консоль админа
    public final int adminConsoleChooseOptionState = 1; //Выбор опции
    public final int adminConsoleShowUsersState = 2; // Вывести список пользователей
    public final int adminConsoleDeleteUserState = 3; //Удалить пользователя
    public final int adminConsoleAddNewTrainingState = 4; //Добавить новую тренировку


    Thread sessionThread;


    //Пользователи
    SessionController sessionController = new SessionController();
    DiaryController diaryController = new DiaryController();
    AdminController adminController = new AdminController();
    List<User> userList = new ArrayList<>();
    User currentUser;


    Map<String, List<String>> workoutTypes = new HashMap<>();
    Map<String, List<String>> workoutParametres = new HashMap<>();

    LocalDate currentDate = LocalDate.now();

    /**
     * Метод для добавления нескольких типов тренировок в листы с тренировками и параметрами
     */

    public void setTrainings() {
        workoutTypes.put("Кардио", Arrays.asList("Бег", "Езда на велосипеде", "Бег в гору"));
        workoutTypes.put("Силовые", Arrays.asList("Жим штанги", "Подтягивания", "Приседания"));

        workoutParametres.put("Кардио", Arrays.asList("Расстояние", "Сожженные калории", "Потраченное время"));
        workoutParametres.put("Силовые", Arrays.asList("Повторения", "Подходы", "Вес"));
    }


    /**
     * Метод для установления начального статуса сессии
     */

    public void setupSession() {
        sessionState = sessionTitleState;
        setTrainings();
    }

    /**
     * Метод для старта потока сессии
     */

    //Создаем поток
    public void startSessionThread() {

        sessionThread = new Thread();
        sessionThread.start();

    }


    /**
     * Метод определяет, сколько раз в секунду срабатывает метод update() (60 раз)
     */
    @Override
    public void run() {

        double timeInterval = 1000000000 / FPS;
        double nextTimeInterval = System.nanoTime() + timeInterval;


        while (sessionThread != null) {

            update();

            try {
                double remainingTime = nextTimeInterval - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextTimeInterval += timeInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Метод для проверки текущего статуса сессии и выполнения соответствующих статусу действий.
     */
    private void update() {

        if (sessionState == sessionTitleState) {
            title();
            sessionState = sessionChooseOptionState;
        }

        if (sessionState == sessionChooseOptionState) {
            choose();
        }

        if (sessionState == sessionLoginState) {
            login();
        }

        if (sessionState == sessionRegistrationState) {
            register();
        }

        if (sessionState == sessionCheckingDiaryState) {
            diary();
        }

        if (sessionState == sessionInformationState) {
            printHelpMessage();
        }

    }


    /**
     * Метод для вывода заглавного сообщения
     */
    private void title() {


        System.out.println("Приветствую вас в своем проекте <TrainingDiary!> \n" +
                "Этот проект разработан в рамках интенсива университета Y_LAB. \n\n" +
                "В данном проекте вам доступны такие функции, как: \n\n" +
                "1. Логин \n" +
                "2. Регистрация \n" +
                "3. О проекте \n\n" +
                "Введите цифру для выбора соответствующей вкладки:  ");

    }

    /**
     * Метод для вызова метода из sessionController`a с выводом сообщения о помощи
     */

    private void printHelpMessage() {
        sessionController.printHelpMessage();
        sessionState = sessionChooseOptionState;
    }


    /**
     * Метод для выбора нужного нам пункта в меню
     */
    private void choose() {

        Scanner scanner = new Scanner(System.in);

        try {
            int number = scanner.nextInt();

            if (number == 1) {
                sessionState = sessionLoginState;
            } else if (number == 2) {
                sessionState = sessionRegistrationState;
            } else if (number == 3) {
                sessionState = sessionInformationState;
            } else if (number > 3 && number != 101) {
                System.out.println("\nВведите номер, соответствующий команде!\n");
            } else if (number == 101) {
                addAdmin();
            }

        } catch (Exception e) {
            System.out.println("\nВведите номер, соответствующий команде!\n");
        }


    }

    /**
     * Метод для регистрации пользователя. Вызывает метод register из sessionController`a
     */
    private void register() {

        User user = sessionController.register(ID, userList);

        if (user != null) {
            System.out.println("Пользователь успешно добавлен");
            userList.add(user);
            sessionState = sessionLoginState;
            ID++;
        } else {
            System.out.println("Пользователь с таким логином уже существует. Пожалуйста, выберите другой логин.\n");
        }
    }

    /**
     * Метод для входа в аккаунт. Вызывает метод login из sessionController`a
     */

    private void login() {

        if (!userList.isEmpty()) {

            currentUser = sessionController.login(userList);

            if (currentUser != null) {
                System.out.println("Вы вошли в аккаунт!");
                sessionState = sessionCheckingDiaryState;
            } else {
                System.out.println("Вы ввели неверные данные! Попробуйте еще раз:\n");
            }
        } else {
            System.out.println("В данный момент не существует ни одной записи о пользователе. \n" +
                    "Зарегестрируйте нового!\n");

            sessionState = sessionChooseOptionState;
        }

    }

    /**
     * Метод, выводящий сообщение <Здравствуйте, пользователь!>.
     */

    private void greetingsInDiary() {
        diaryController.greetingsInDiary(currentUser);
    }

    /**
     * Метод, вызывающий метод из diaryControllera для удаления записи о тренировки
     */

    private void deleteTraining() {
        if (!currentUser.trainingList.isEmpty()) {
            diaryController.deleteTraining(currentUser);
        } else {
            System.out.println("\nУ вас нет ни одной записи о тренировках =( ");
        }
    }

    /**
     * Метод, вызывающий метод из sessionControllera для добавления админа
     */

    private void addAdmin() {
        User user = sessionController.addAdmin(ID, userList);
        userList.add(user);
    }

    /**
     * Метод, вызывающий метод из diaryControllera для вывода всех тренировок
     */

    private void printAllTrainings() {
        diaryController.printAllTrainingsOfUser(currentUser);
    }

    /**
     * Метод, вызывающий метод из diaryControllera для добавления тренировки в дневник пользователя
     */
    private void addTrainingToUser() {
        diaryController.addTrainingToUser(workoutTypes, workoutParametres, currentDate, currentUser);

    }

    /**
     * Метод, вызывающий метод из diaryControllera для изменения записи о тренировки
     */

    private void editUserTraining() {
        if (!currentUser.trainingList.isEmpty()) {
            diaryController.editTraining(currentUser);
        } else {
            System.out.println("У вас нет ни одной записи о тренировках =( \n");
        }
    }

    /**
     * Метод, вызывающий метод из diaryControllera для вывода меню статистики
     */

    private void userStatsMenu() {
        diaryController.printStats(currentUser);
    }

    /**
     * Метод, для вывода меню дневника пользователя и выбора нужного нам пункта
     */

    private void chooseOption() {

        System.out.println(diaryController.profileInfo(currentUser));


        try {

            Scanner checkScanner = new Scanner(System.in);

            int number = checkScanner.nextInt();

            if (number == 1) {
                diaryState = diaryAddTrainingState;

            } else if (number == 2) {
                diaryState = diaryEditTrainingState;

            } else if (number == 3) {
                diaryState = diaryDeleteTrainingState;

            } else if (number == 4) {
                System.out.println("\nВывожу все данные о тренировках:\n\n" +
                        "_________________________________");
                printAllTrainings();
                System.out.println("_________________________________\n");

            } else if (number == 5) {
                diaryState = diaryStatsState;

            } else if (number == 6) {
                sessionState = sessionTitleState;
                diaryState = diaryGreetingsState;
                System.out.println("\nВы вышли в главное меню!\n");

            } else if (number == 7) {
                if (currentUser.getRole().equals("ADMIN")) {
                    diaryState = diaryAdminConsoleState;
                } else {
                    System.out.println("\nВведите номер, соответствующий команде!\n");
                }
            } else if (number > 7) {
                System.out.println("\nВведите номер, соответствующий команде!\n");
            }


        } catch (Exception e) {
            System.out.println("\nВведите номер, соответствующий команде!\n");
        }

    }


    /**
     * Метод, проверяющий, какой сейчас статус у дневника и выполняющий соответствующие ему действия
     */

    private void diary() {


        if (diaryState == diaryGreetingsState) {
            greetingsInDiary();
            diaryState = diaryChooseOptionState;
        }


        if (diaryState == diaryChooseOptionState) {
            chooseOption();
        }


        if (diaryState == diaryAddTrainingState) {
            addTrainingToUser();
            diaryState = diaryChooseOptionState;
        }

        if (diaryState == diaryDeleteTrainingState) {
            deleteTraining();
            diaryState = diaryChooseOptionState;
        }

        if (diaryState == diaryEditTrainingState) {
            editUserTraining();
            diaryState = diaryChooseOptionState;
        }
        if (diaryState == diaryStatsState) {
            userStatsMenu();
            diaryState = diaryChooseOptionState;
        }
        if (diaryState == diaryAdminConsoleState) {
            adminConsole();
        }
    }
    /**
     * Метод, вызывающий метод из adminController`a для вывода списка пользователей
     */

    public void adminPrintUsers() {
        adminController.printUserList(userList);
    }

    /**
     * Метод, вызывающий метод из adminController`a для удаления пользователя из списка
     */

    public void adminDeleteUser() {
        adminController.deleteUser(userList,currentUser);
    }

    /**
     * Метод, вызывающий метод из adminController`a для получения значения ID последнего пользователя
     */

    public void updateID() {
        adminController.returnCurrentId(userList);
    }

    /**
     * Метод, вызывающий метод из adminController`a для добавления нового типа тренировок
     */

    public void adminAddTraining() {
        adminController.addNewTraining(workoutTypes, workoutParametres);
    }

    /**
     * Метод, проверяющий, какой сейчас статус у консоли админа и выполняющий соответствующие действия
     */

    public void adminConsole() {

        if (adminConsoleState == adminConsoleChooseOptionState) {
            adminConsoleChoose();
        }
         if (adminConsoleState == adminConsoleShowUsersState) {
            adminPrintUsers();
            adminConsoleState = adminConsoleChooseOptionState;
        }
         if (adminConsoleState == adminConsoleDeleteUserState) {
             adminDeleteUser();
             updateID();
             adminConsoleState = adminConsoleChooseOptionState;
         }
         if (adminConsoleState == adminConsoleAddNewTrainingState) {
             adminAddTraining();
             adminConsoleState = adminConsoleChooseOptionState;
         }
    }

    /**
     * Метод, проверяющий, какой сейчас статус у консоли админа и выполняющий соответствующие действия
     */

    private void adminConsoleChoose() {

        adminController.printChooseMessage();

        try {

            Scanner checkScanner = new Scanner(System.in);
            int number = checkScanner.nextInt();

            if (number == 1) {
            adminConsoleState = adminConsoleShowUsersState;
            }
           else if (number == 2) {
                adminConsoleState = adminConsoleDeleteUserState;
            }
            else if (number == 3) {
                adminConsoleState = adminConsoleAddNewTrainingState;
            }
            else if (number == 4) {
                adminConsoleState = adminConsoleChooseOptionState;
                diaryState = diaryChooseOptionState;
            } else {
                System.out.println("Вы ввели неверное число! \n");
            }


        } catch (Exception e) {
            System.out.println("\nВведите номер, соответствующий команде!\n");
        }
    }

}



