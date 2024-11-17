/*
Необходимо преобразовать собранное на семинаре дерево поиска в полноценное левостороннее красно-чёрное дерево. Реализовать метод добавления новых элементов с балансировкой.
- Красно-чёрное дерево имеет следующие критерии:
- Каждая нода имеет цвет (красный или черный);
- Корень дерева всегда черный;
- Новая нода всегда красная;
- Красные ноды могут быть только левым дочерним элементом;
- У красной ноды все дочерние элементы черного цвета.

Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо произвести балансировку, благодаря которой все критерии выше станут валидными.
Для балансировки существует 3 операции:
- левый малый поворот
- правый малый поворот
- смена цвета.

Критерии применения этих операций следующие:
- Если правый дочерний элемент красный, а левый черный, то применяем малый правый поворот
- Если левый дочерний элемент красный и его левый дочерний элемент тоже красный, то применяем малый левый поворот
- Если оба дочерних элемента красные, то делаем смену цвета
- Если корень стал красным, то перекрашиваем его в черный
 */
package Seminars.Seminar_04.HW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tree {
    Node root;
    class Node {
        int value;
        Node left;
        Node right;
        Color color;
    }

    enum Color {
        BLACK,
        RED
    }

    public boolean insert(int value) {
        if (root != null) {
            boolean result = insert(root, value);
            root = balance(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.value = value;
            root.color = Color.BLACK;
            return true;
        }
    }

    private boolean insert(Node node, int value) {
        if (node.value == value) {
            return false;
        }

        boolean result;
        if (node.value < value) {
            if (node.right == null) {
                node.right = new Node();
                node.right.value = value;
                node.right.color = Color.RED;
                result = true;
            } else {
                result = insert(node.right, value);
                node.right = balance(node.right);
            }
        } else {
            if (node.left == null) {
                node.left = new Node();
                node.left.value = value;
                node.left.color = Color.RED;
                result = true;
            } else {
                result = insert(node.left, value);
                node.left = balance(node.left);
            }
        }
        return result;
    }

    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (node.value == value) {
            return node;
        }
        if (node.value < value) {
            return find(node.right, value);
        } else {
            return find(node.left, value);
        }
    }

    private Node leftRotate(Node node) {
        Node cur = node.right;
        node.right = cur.left;
        cur.left = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    private Node rightRotate(Node node) {
        Node cur = node.left;
        node.left = cur.right;
        cur.right = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    private void swapColors(Node node) {
        node.color = Color.RED;
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }

    private Node balance(Node node) {
        boolean flag;
        Node cur = node;
        do {
            flag = false;

            if (cur.right != null && cur.right.color == Color.RED &&
                    (cur.left == null || cur.left.color == Color.BLACK)) {
                cur = leftRotate(cur);
                flag = true;
            }

            if (cur.left != null && cur.left.color == Color.RED &&
                    cur.left.left != null && cur.left.left.color == Color.RED) {
                cur = rightRotate(cur);
                flag = true;
            }

            if (cur.left != null && cur.left.color == Color.RED &&
                    cur.right != null && cur.right.color == Color.RED) {
                swapColors(cur);
                flag = true;
            }
        } while (flag);
        return cur;
    }

    public void print() {
        print(root, 0);
    }

    private void print(Node node, int deep) {
        if (node == null) {
            return;
        }
        print(node.left, deep + 4);

        for (int i = 0; i < deep; i++)
            System.out.print(" ");
        System.out.println("value: " + node.value + " {color: " + node.color + "}");
        print(node.right, deep + 4);
    }

    public static void main(String[] args) {
        final Tree tree = new Tree();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите целые числа для вставки в дерево. Для завершения нажмите Ctrl+C.");
            while (true) {
                try {
                    String input = reader.readLine();
                    if (input == null || input.isEmpty()) {
                        continue;
                    }
                    int value = Integer.parseInt(input.trim());
                    boolean inserted = tree.insert(value);
                    if (inserted) {
                        System.out.println("Значение " + value + " успешно добавлено.");
                    } else {
                        System.out.println("Значение " + value + " уже существует в дереве.");
                    }
                    tree.print(); // Выводим текущее состояние дерева после каждой вставки
                } catch (NumberFormatException e) {
                    System.out.println("Пожалуйста, введите корректное целое число.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
