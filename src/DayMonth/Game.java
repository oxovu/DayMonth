package DayMonth;

import static DayMonth.Main.*;
import static DayMonth.Node.*;

public class Game {

    // поиск ближайшего левого "соседа", то есть ближайшего левого потомка от родителя текущего узла

    public static Node findLeftNeighbour(Node node) {
        if (node.parent.rightRight != null && node.parent.rightRight.pair.equals(node.pair)) {
            node.parent.createRight();
            if (checkDate(node.parent.right.pair.day, node.parent.right.pair.month)) {
                checkTurn(node.parent.right);
                return node.parent.right;
            } else if (node.parent.createLeft()) {
                if (checkDate(node.parent.left.pair.day, node.parent.left.pair.month)) {
                    checkTurn(node.parent.left);
                    return node.parent.left;
                }
            } else {
                node.parent.createLeftLeft();
                if (checkDate(node.parent.leftLeft.pair.day, node.parent.leftLeft.pair.month)) {
                    checkTurn(node.parent.leftLeft);
                    return node.parent.leftLeft;
                }
            }
        } else if (node.parent.right != null && node.parent.right.pair.equals(node.pair)) {
            node.parent.createLeft();
            if (checkDate(node.parent.left.pair.day, node.parent.left.pair.month)) {
                checkTurn(node.parent.left);
                return node.parent.left;
            } else {
                node.parent.createLeftLeft();
                if (checkDate(node.parent.leftLeft.pair.day, node.parent.leftLeft.pair.month)) {
                    checkTurn(node.parent.leftLeft);
                    return node.parent.leftLeft;
                }
            }
        } else if (node.parent.left != null && node.parent.left.pair.equals(node.pair)) {
            node.parent.createLeftLeft();
            if (checkDate(node.parent.leftLeft.pair.day, node.parent.leftLeft.pair.month)) {
                checkTurn(node.parent.leftLeft);
                return node.parent.leftLeft;
            }
        }
        return null;
    }

    // быстрый поиск в глубину проигрышной позиции, то есть дать 31.12


    public static Node DownSearch(Node node) {
        checkTurn(node);
        while (!node.pair.equals(lose)) {
            while (node.createRightRight() || node.pair == lose) {
                node.createRightRight();
                node = node.rightRight;
            }
            while (node.createRight() || node.pair == lose) {
                node.createRight();
                node = node.right;
                DownSearch(node);
            }
            while (node.createLeft() || node.pair == lose) {
                node.createLeft();
                node = node.left;
                DownSearch(node);
            }
            while (node.createLeftLeft() || node.pair == lose) {
                node.createLeftLeft();
                node = node.leftLeft;
                DownSearch(node);
            }
        }
        checkTurn(node);
        node.goodChoice = node.myTurn;
        return node;
    }

    // просчитывание для одного из потомков узла хороший это ход или нет


    public static Node oneStep(Node node, Node root) {
        boolean flag;
        Node lastNode;
        node = DownSearch(node);

        // поиск самого левого в этом поддереве проигрышного значения

        while (findLeftNeighbour(node) != null) {
            node.goodChoice = node.myTurn;
            node = findLeftNeighbour(node);
            node = DownSearch(node);
        }

        // оценка родителей узлов влоть до ближайшего к корню

        while (!node.parent.equals(root)) {
            lastNode = node;
            node = node.parent;
            flag = true;
            // если мой ход, то достаточного одного хорошего потомка, иначе все потомки должны быть хорошими
            if (!node.myTurn) {
                while (findLeftNeighbour(lastNode) != null && !node.pair.equals(root.pair)) {
                    Node out = findLeftNeighbour(lastNode);
                    lastNode = oneStep(findLeftNeighbour(lastNode), root);
                    if (out != lastNode && findLeftNeighbour(out) != lastNode) break;
                }
                if (node.rightRight != null && checkDate(node.rightRight.pair.day, node.rightRight.pair.month)) {
                    flag = flag & node.rightRight.goodChoice;
                }
                if (node.right != null && checkDate(node.right.pair.day, node.right.pair.month)) {
                    flag = flag & node.right.goodChoice;
                }
                if (node.left != null && checkDate(node.left.pair.day, node.left.pair.month)) {
                    flag = flag & node.left.goodChoice;
                }
                if (node.leftLeft != null && checkDate(node.leftLeft.pair.day, node.leftLeft.pair.month)) {
                    flag = flag & node.leftLeft.goodChoice;
                }
                node.goodChoice = flag;
            } else {
                flag = false;
                while (findLeftNeighbour(lastNode) != null && !node.pair.equals(root.pair)) {
                    Node out = findLeftNeighbour(lastNode);
                    lastNode = oneStep(findLeftNeighbour(lastNode), root);
                    if (out != lastNode && findLeftNeighbour(out) != lastNode) break;
                }
                if (node.rightRight != null && checkDate(node.rightRight.pair.day, node.rightRight.pair.month)) {
                    flag = flag | node.rightRight.goodChoice;
                }
                if (node.right != null && checkDate(node.right.pair.day, node.right.pair.month)) {
                    flag = flag | node.right.goodChoice;
                }
                if (node.left != null && checkDate(node.left.pair.day, node.left.pair.month)) {
                    flag = flag | node.left.goodChoice;
                }
                if (node.leftLeft != null && checkDate(node.leftLeft.pair.day, node.leftLeft.pair.month)) {
                    flag = flag | node.leftLeft.goodChoice;
                }
                node.goodChoice = flag;
            }
        }
        return node;
    }


    public static Node theGame(Pair pair) {

        // при маленьких значениях дня и месяца, максимально их увеличиваем

        Node root = new Node(pair, null);
        if (root.pair.month < 8) {
            Node turn = new Node(new Pair(pair.day, pair.month + 2), null);
            System.out.println(turn);
            return turn;
        }
        if (root.pair.day < 25) {
            Node turn = new Node(new Pair(pair.day + 2, pair.month), null);
            System.out.println(turn);
            return turn;
        }

        // иначе, начиная с левого потомка ищем первый хороший ход
        // если хороший ход не найден, ходим, прибавив единицу ко дню

        Node node = oneStep(root, root);
        while (!node.goodChoice && findLeftNeighbour(node) != null) {
            node = oneStep(findLeftNeighbour(node), root);
        }
        return node;
    }
}
