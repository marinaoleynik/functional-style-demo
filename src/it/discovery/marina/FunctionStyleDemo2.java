package it.discovery.marina;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FunctionStyleDemo2 {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(4);
        list2.add(5);
        list2.add(5);
        list2.add(6);
        System.out.println(combine(list1, list2));

        ArrayList<String> listStr1 = new ArrayList<>();
        listStr1.add("1");
        listStr1.add("2");
        listStr1.add("3");
        ArrayList<String> listStr2 = new ArrayList<>();
        listStr2.add("4");
        listStr2.add("5");
        listStr2.add("5");
        listStr2.add("6");
        System.out.println(combine(listStr1, listStr2));

        System.out.println(combine(listStr1, null));

        System.out.println(combine(null, null));
    }

    public static <T> List<T> combine(List<T> firstList, List<T> secondList)
    {
        return Stream.concat(getStream(firstList), getStream(secondList)).distinct().collect(Collectors.toList());
    }

    public static <T> Stream<T> getStream(List<T> collection) {
        return Optional.ofNullable(collection).map(Collection::stream).orElse(Stream.empty());
    }


 }
