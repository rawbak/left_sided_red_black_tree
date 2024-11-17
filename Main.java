package Seminars.Seminar_04;

import Seminars.Seminar_04.HW.Tree;

public class Main {
    public static void main(String[] args) {
        Seminars.Seminar_04.HW.Tree tree = new Tree();
        for (int i = 1; i <= 20; i++)
            tree.insert(i);

        tree.print();
    }
}
