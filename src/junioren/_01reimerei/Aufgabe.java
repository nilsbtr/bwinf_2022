package junioren._01reimerei;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aufgabe {
    String dateiName;
    DynArray<String> dynarr = new DynArray<>();
    Stack<int[]> stack = new Stack<>();

    Aufgabe(String dateiName) {
        this.dateiName = dateiName;
        File file = new File(dateiName);
        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }
        dateiLesen();
        aufgabeLoesen();
    }

    public void dateiLesen() {
        try (BufferedReader in = new BufferedReader(new FileReader(dateiName))) {
            int i = 0;
            String line;
            while ((line = in.readLine()) != null) {
                dynarr.append(line);
                i++;
            }
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aufgabeLoesen() {
        for (int i = 0; i < dynarr.getLength(); i++) {
            System.out.println(dynarr.getItem(i));
        }

        System.out.println("--------------------");

        while (!dynarr.isEmpty()) {
            String a = dynarr.getItem(0);

            for (int i = 1; i < dynarr.getLength(); i++) {
                String b = dynarr.getItem(i);

                if (b.endsWith(a) || a.endsWith(b)) {
                    continue;
                } else if (vergleicheWorte(a, b)) {
                    System.out.println(a + " + " + b);
                }
            }

            dynarr.delete(0);
        }
    }

    private boolean vergleicheWorte(String a, String b) {
        int[] posA = findeVokalgruppe(a);
        int[] posB = findeVokalgruppe(b);

        String vokA = a.substring(posA[0], posA[1]);
        String vokB = b.substring(posB[0], posB[1]);

        if (!vokA.equals(vokB)) {
            return false;
        }

        String subA = a.substring(posA[1] + 1);
        String subB = b.substring(posB[1] + 1);

        if (subA.equals(subB)) {
            return true;
        }

        // TODO: Hälfte der Buchstaben stimmt überein
    }

    private int[] findeVokalgruppe(String wort) {
        int[] arr = stack.top();
        stack.pop();

        if (!stack.isEmpty()) {
            arr = stack.top();
        }

        while (!stack.isEmpty()) {
            stack.pop();
        }

        return arr;

    }

    public void regexMatcher(String wort) {
        Pattern pattern = Pattern.compile("[aeiouAEIOU]+");
        Matcher matcher = pattern.matcher(wort);

        if (matcher.find()) {
            int[] arr = new int[2];
            arr[0] = matcher.start(); // Start of match
            arr[1] = (arr[0] + matcher.end() - 1); // End of match
            stack.push(arr);
            regexMatcher(wort.substring(0, arr[0] - 1));
        }
    }
}
