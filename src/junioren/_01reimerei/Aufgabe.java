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
            String line;
            while ((line = in.readLine()) != null) {
                dynarr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aufgabeLoesen() {
        while (!dynarr.isEmpty()) {
            String a = dynarr.getItem(0);

            for (int i = 1; i < dynarr.getLength(); i++) {
                String b = dynarr.getItem(i);

                if (vergleicheWorte(a, b)) {
                    System.out.println(a + " + " + b);
                }
            }

            dynarr.delete(0);
        }
    }

    private boolean vergleicheWorte(String a, String b) {
        if (b.toUpperCase().endsWith(a.toUpperCase()) || a.toUpperCase().endsWith(b.toUpperCase())) {
            return false;
        }

        String sa = findeVokalgruppe(a);
        String sb = findeVokalgruppe(b);

        if (sa.equals("") || sb.equals("")) {
            return false; // Keine Vokale gefunden
        }

        if (!sa.equalsIgnoreCase(sb)) {
            return false; // Wörter enden gleich
        }

        if (sa.length() * 2 < a.length() || sb.length() * 2 < b.length()) {
            return false; // Ende enthält mind. 1/2 der Buchstaben
        }

        return true;
    }

    private String findeVokalgruppe(String wort) {
        Stack<Integer> stack = new Stack<>();

        Pattern pattern = Pattern.compile("[aeiouAEIOUäöüÄÖÜ]+");
        Matcher matcher = pattern.matcher(wort);

        while (matcher.find()) {
            stack.push(matcher.start());
        }

        int start;

        if (!stack.isEmpty()) {
            start = stack.top();
            stack.pop();

            if (!stack.isEmpty()) {
                start = stack.top();
            }
        } else {
            return "";
        }

        return wort.substring(start);
    }

    public static void main(String[] args) {
        Aufgabe a = new Aufgabe("src/junioren/_01reimerei/reimerei3.txt");
    }
}
