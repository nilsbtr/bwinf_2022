import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DateiLesen {
    String dateiName;
    String[] arr;

    public DateiLesen() {
        dateiName = "beispiel.txt";
        File file = new File(dateiName);
        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }
        dateiLesen();
    }

    public void dateiLesen() {
        int fl = 0;

        try (BufferedReader in = new BufferedReader(new FileReader(dateiName))) {
            while (in.readLine() != null) {
                fl++;
            }
            arr = new String[fl];
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader in = new BufferedReader(new FileReader(dateiName))) {
            for (int i = 0; i < fl; i++) {
                arr[i] = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DateiLesen a = new DateiLesen();
    }
}
