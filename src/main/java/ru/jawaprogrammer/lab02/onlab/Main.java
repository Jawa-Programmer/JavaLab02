package ru.jawaprogrammer.lab02.onlab;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main
{
	// тут задание, которое было сделано на лабе - подсчет строк и удаление повторений
	public static void main(String[] args)
	{
		LinkedHashMap<String, Integer> stats = new LinkedHashMap<>();
		Scanner scan = new Scanner(System.in);
		String word;
		while(scan.hasNext())
		{
			word = scan.next();
			stats.put(word, stats.getOrDefault(word, 0) + 1);
			}
		for(Map.Entry<String, Integer> e : stats.entrySet())
			System.out.println("Слово " + e.getKey() + " встречается " + e.getValue() + " раз.");
		System.out.println(stats.keySet());
		//System.out.println(stats);
	}
}
