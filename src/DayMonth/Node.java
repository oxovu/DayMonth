package DayMonth;

import static DayMonth.Readers.*;

public class Node {

    Pair pair; // день и месяц
    Node rightRight; // потомок с увеличением месяца на 2
    Node right;  // потомок с увеличением месяца на 1
    Node left; // потомок с увеличением дня на два
    Node leftLeft; // потомок с увеличением дня на 1
    boolean goodChoice; // определяет хороший ход или нет
    Node parent;
    boolean myTurn = true; // мой ход = ход компьютера

    public static Pair root = start(); // дата, с которой начинается игра


    Node(Pair pair, Node parent) {
        this.pair = pair;
        this.parent = parent;
    }

    // создание потомков

    public boolean createLeftLeft() {
        changeTurn();
        this.leftLeft = new Node(new Pair(this.pair.day + 1, this.pair.month), this);
        return checkDate(this.leftLeft.pair.day, this.leftLeft.pair.month);
    }

    public boolean createLeft() {
        changeTurn();
        this.left = new Node(new Pair(this.pair.day + 2, this.pair.month), this);
        return checkDate(this.left.pair.day, this.left.pair.month);
    }

    public boolean createRight() {
        changeTurn();
        this.right = new Node(new Pair(this.pair.day, this.pair.month + 1), this);
        return checkDate(this.right.pair.day, this.right.pair.month);
    }

    public boolean createRightRight() {
        changeTurn();
        this.rightRight = new Node(new Pair(this.pair.day, this.pair.month + 2), this);

        return checkDate(this.rightRight.pair.day, this.rightRight.pair.month);
    }

    // проверка, чья очередь ходить

    public static boolean checkTurn(Node node){
        node.myTurn = node.changeTurn();
        return node.myTurn;
    }

    // смена хода (если требуется)

    private boolean changeTurn() {
        int count = 0;
        Node node = this;
        myTurn = true;
        while (node.pair != root) {
            count++;
            node = node.parent;
        }
        if (count % 2 == 1) myTurn = false;
        return myTurn;
    }

    // проверка даты на существование

    public static boolean checkDate(int day, int month) {
        if (day > 31) return false;
        if (month > 12) return false;
        if (month == 2) if (day > 28) return false;
        switch ((int) ((month + Math.floor(month / 8)) % 2)) {
            case 1:
                if (day > 31) return false;
                break;
            case 0:
                if (day > 30) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pair + "";
    }



}

