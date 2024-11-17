/*
1. Реализуем метод поиска данных по ключу в хэш-таблице.
2. Теперь, когда у нас есть базовая структура нашей хэш-таблицы, можно
написать алгоритм поиска элементов, включающий в себя поиск нужного
бакета и поиск по бакету.
 */
package Seminars.Seminar_04;

public class task3 {
    public static void main(String[] args) {
        Tree tree = new Tree();

        for (int i = 1; i <= 5; i++)
            tree.insert(i);

        System.out.println(tree.find(1));
        System.out.println(tree.find(6));
    }
}
