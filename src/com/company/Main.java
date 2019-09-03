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

        Map<Integer, Integer> numbersOfGroup = new HashMap();
        int maxValue = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (numbersOfGroup.containsKey(i)) {
                continue;
            }
            String st1 = lines.get(i);
            numbersOfGroup.put(i, maxValue);
            for (int j = i + 1; j < lines.size(); j++) {
                String st2 = lines.get(j);
                String[] columnsOfSt1 = st1.split(";");
                String[] columnsOfSt2 = st2.split(";");
                for (int k = 0; k < Math.min(columnsOfSt1.length, columnsOfSt2.length); k++) {
                    if (columnsOfSt1[k] != null && columnsOfSt1[k].equals(columnsOfSt2[k])) {
                         Integer jValue = numbersOfGroup.get(j);
                         if (jValue != null) {
                             numbersOfGroup.put(i, jValue);
                             maxValue--;
                             /*int valueToRebase = numbersOfGroup.get(i);
                             numbersOfGroup.keySet().stream().filter(key -> numbersOfGroup.get(key) == valueToRebase).
                                     forEach(key -> numbersOfGroup.put(key, valueToRebase));*/

                         } else {
                             numbersOfGroup.put(j, maxValue);
                         }
                         break;
                    }
                }
            }
            maxValue++;
        }
        List<String>[] groups = new List[maxValue];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ArrayList<>();
        }
        for (int i = 0; i < numbersOfGroup.size(); i++) {
            int x = numbersOfGroup.get(i);
            groups[x].add(lines.get(i));
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
