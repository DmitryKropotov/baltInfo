package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        File file = new File("C:\\Data\\projects\\experiment.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        List<String> lines = new ArrayList();
        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        Map<Integer, Integer> groupNumberForLine = new HashMap();
        int maxValue = 0;
        for (int i = 0; i < lines.size(); i++) {
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
                int minValue = allValuesToChange.stream().min(Integer::compareTo).get();
                allKeysToChange.forEach(k -> groupNumberForLine.put(k, minValue));
                groupNumberForLine.put(i, minValue);
            }
        }

        Map<Integer, Integer> revertNumbers = new HashMap<>();
        int count = 0;
        for (int i = 0; i < groupNumberForLine.size(); i++) {
            if (!revertNumbers.containsKey(groupNumberForLine.get(i))) {
                revertNumbers.put(groupNumberForLine.get(i), count++);
            }
        }
        List<String>[] groups = new List[(int)groupNumberForLine.values().stream().distinct().count()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ArrayList<>();
        }
        for (int i = 0; i < groupNumberForLine.size(); i++) {
            int x = groupNumberForLine.get(i);
            groups[revertNumbers.get(x)].add(lines.get(i));
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

        PrintWriter writer = new PrintWriter("C:\\Data\\projects\\result.txt");
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
}
