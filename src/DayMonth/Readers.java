package DayMonth;

import java.util.InputMismatchException;

import static DayMonth.Main.*;
import static DayMonth.Node.*;

public class Readers {

    // чтение даты для начала игры

    public static Pair start() {
        try {
            System.out.println("Введите день: ");
            int day = scanner.nextInt();
            System.out.println("Введите месяц: ");
            int month = scanner.nextInt();
            Pair pair = new Pair(day, month);
            while (!checkDate(day, month)) {
                System.out.println("Некорректная дата. Введите новую");
                pair = start();
                break;
            }
            return pair;
        } catch (Exception e) {
            System.out.println("Некорректная дата. Введите новую");
            scanner.nextLine();
            root = start();
        }
        return null;
    }

    // промежуточное чтение даты

    public static Pair nextStep(Pair turn) {
        try {
            System.out.println("Введите день для продолжения игры: ");
            int day = scanner.nextInt();
            System.out.println("Введите месяц: ");
            int month = scanner.nextInt();
            Pair pair = new Pair(day, month);
            while (!checkDate(day, month)) {
                System.out.println("Некорректная дата. Введите новую");
                pair = nextStep(turn);
                break;
            }
            while (!checkTurnDate(day, month, turn)) {
                System.out.println("Невозможный ход. Введите новую дату");
                pair = nextStep(turn);
                break;
            }
            return pair;
        } catch (Exception e) {
            System.out.println("Некорректная дата. Введите новую");
            scanner.nextLine();
            return nextStep(turn);
        }
    }

    // проверка возможности хода игрока

    private static boolean checkTurnDate(int day, int month, Pair lastTurn) {
        if (day == lastTurn.day + 1 && month == lastTurn.month) return true;
        if (day == lastTurn.day + 2 && month == lastTurn.month) return true;
        if (day == lastTurn.day && month == lastTurn.month + 1) return true;
        if (day == lastTurn.day && month == lastTurn.month + 2) return true;
        return false;
    }
}
