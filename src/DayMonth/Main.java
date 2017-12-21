package DayMonth;


import java.util.Scanner;

import static DayMonth.Game.*;
import static DayMonth.Readers.*;
import static DayMonth.Node.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Pair lose = new Pair(31, 12); // проигрышная позиция

    public static void main(String[] arg) {

        Pair turn; // ход компьютера
        Pair pair = new Pair(1, 1); // ход противника
        while (!pair.equals(lose)) { // выполняется, пока кто-то не сходит в проигрышную позицию
            turn = theGame(root).pair;
            if (turn.equals(lose)) break;
            pair = nextStep(turn);
            root = pair;
        }
        scanner.close();
        System.out.println("Конец игры");
        if (pair.equals(lose)) {
            System.out.println("Вы проиграли");
        } else System.out.println("Вы выиграли");
    }


}
