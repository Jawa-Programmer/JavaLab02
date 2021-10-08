package ru.jawaprogrammer.lab02.collections;

import org.junit.jupiter.api.*;

import java.util.Arrays;

/// так как в лабе нужно было добавить только один метод merge - его и тестируем
class JawaListTest {
    static JawaList<Integer> listA, listB;
    static final Integer[] setA = {1, 4, 6, 14, 54}, setB = {2, 3, 4, 42, 128}, setC = {1, 2, 3, 4, 4, 6, 14, 42, 54, 128};

    @BeforeAll
    static void init() {
        listA = new JawaList<>(Arrays.asList(setA));
        listB = new JawaList<>(Arrays.asList(setB));
    }

    @Test
    void merge() {
        JawaList<Integer> test1 = listA.merge(listB), test2 = listB.merge(listA);
        Assertions.assertIterableEquals(Arrays.asList(setC), test1);
        Assertions.assertIterableEquals(Arrays.asList(setC), test2);
        test1.clear();
        test2.clear();
    }

    @AfterAll
    static void gc() {
        listA.clear();
        listB.clear();
    }
}