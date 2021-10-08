package ru.jawaprogrammer.lab02;

import ru.jawaprogrammer.lab02.collections.JawaList;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        JawaList<Integer> list1 = new JawaList<>(), list2 = new JawaList<>();
        list1.add(4);
        list1.add(7);
        list1.add(42);
        list2.add(1);
        list2.add(8);
        list2.add(24);
        System.out.println(list1.merge(list2));
        System.out.println(list2.merge(list1));
    }
}
