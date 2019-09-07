package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> lines = readAndFilterLines();

        Map<Integer, Integer> groupNumberForLine = new HashMap();
        int maxValue = 0;
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(i);
            String st1 = lines.get(i);
            int valueToPut = maxValue;
            if (i == 0) {
                groupNumberForLine.put(i, maxValue);
            }
            List<Integer> allKeysToChange = new ArrayList<>();
            List<Integer> allValuesToChange = new ArrayList<>();
            for (int j = i - 1; j >= 0; j--) {
                if (allValuesToChange.contains(groupNumberForLine.get(j))) {
                    allKeysToChange.add(j);
                    continue;
                }
                String st2 = lines.get(j);
                String[] columnsOfSt1 = st1.split(";");
                String[] columnsOfSt2 = st2.split(";");
                for (int k = 0; k < Math.min(columnsOfSt1.length, columnsOfSt2.length); k++) {
                    if (columnsOfSt1[k] != null && columnsOfSt1[k].equals(columnsOfSt2[k])) {
                        if(groupNumberForLine.get(j) == null) {
                            System.out.println("groupNumberForLine is " + groupNumberForLine + "j is " + j);
                        }
                        allKeysToChange.add(j);
                        allValuesToChange.add(groupNumberForLine.get(j));
                        break;
                    }
                }
            }

            if (allKeysToChange.isEmpty()) {
                groupNumberForLine.put(i, valueToPut);
                maxValue++;
            } else {
                int minValueOfValuesToChange = allValuesToChange.stream().min(Integer::compareTo).get();
                allKeysToChange.forEach(k -> groupNumberForLine.put(k, minValueOfValuesToChange));
                groupNumberForLine.put(i, minValueOfValuesToChange);
            }
        }

        Map<Integer, Integer> mapRandomGroupNumbersToOrderedNumbers = new HashMap<>();
        int countOfGroups = 0;
        for (int i = 0; i < groupNumberForLine.size(); i++) {
            if (!mapRandomGroupNumbersToOrderedNumbers.containsKey(groupNumberForLine.get(i))) {
                mapRandomGroupNumbersToOrderedNumbers.put(groupNumberForLine.get(i), countOfGroups++);
            }
        }
        List<String>[] groups = new List[(int)groupNumberForLine.values().stream().distinct().count()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ArrayList<>();
        }
        for (int i = 0; i < groupNumberForLine.size(); i++) {
            int x = groupNumberForLine.get(i);
            groups[mapRandomGroupNumbersToOrderedNumbers.get(x)].add(lines.get(i));
        }

        Arrays.sort(groups, (list1, list2) -> {
           if (list1.size() < list2.size()) {
                return 1;
           } else if (list1.size() > list2.size()) {
                return -1;
           } else {
                return 0;
           }
        });

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("result.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println("Число групп - " + groups.length);
        for (int i = 0; i < groups.length; i++) {
            List<String> group = groups[i];
            writer.println("Группа " + (i+1));
            for(String element: group) {
                writer.println(element);
            }
        }
        writer.close();
    }

    private static List<String> readAndFilterLines() {
        File file = new File("lng.csv");
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String st;
        List<String> lines = new ArrayList();
        try {
            while ((st = br.readLine()) != null) {
                lines.add(st);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines.stream().distinct().filter(s -> {
            String[] columns = s.split(";");
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].matches("\".*\"") && !columns[i].equals("")) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}
