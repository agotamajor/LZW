import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.String.valueOf;

public class LZW {
    public static void main(String[] args) throws IOException {
        // kodolas
        File file = new File("be.txt");
        Scanner scanner = new Scanner(file);
        FileWriter writer = new FileWriter("kiLZW.txt");
        String string = "";
        while (scanner.hasNextLine()) {
            string += scanner.nextLine();
        }

        Map<String, Integer> dictionary = new TreeMap<>();
        int n = string.length();
        for (int i = 0; i < n; i++) {
            char next = string.charAt(i);
            if (!dictionary.containsKey(valueOf(next))) {
                dictionary.put(valueOf(next), dictionary.size() + 1);
            }
        }

        int i = 0;
        while (i < n) {
            char next = string.charAt(i);
            String word = "" + next;
            int nr = 1;
            if (i == n - 1) {
                writer.write(dictionary.get(word) + " ");
                i = n;
                break;
            }
            for (int j = i + 1; j < n; j++) {
                char current = string.charAt(j);
                word += current;
                if (dictionary.containsKey(word)) {
                    nr++;
                } else {
                    int length = word.length() - 1;
                    writer.write(dictionary.get(word.substring(0, length)) + " ");
                    dictionary.put(word, dictionary.size() + 1);
                    break;
                }
                if (j == n - 1) {
                    writer.write(dictionary.get(word) + " ");
                }
            }
            i += nr;
        }
        writer.close();
        scanner.close();

        // dekodolas
        File decodeFile = new File("kiLZW.txt");
        Scanner scan = new Scanner(decodeFile);
        String str = "";
        while (scan.hasNextLine()) {
            str += scan.nextLine();
        }
        scan.close();

        String decodeString = "";
        String[] tokens = str.split(" ");
        for (String token: tokens) {
            int ind = Integer.parseInt(token);
            for (Map.Entry<String, Integer> entry: dictionary.entrySet()) {
                if (ind == entry.getValue()) {
                    decodeString += entry.getKey();
                }
            }
        }
        System.out.println(decodeString);
    }
}
