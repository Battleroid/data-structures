import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by casey on 2016-02-05.
 */
public class Exercise21_11 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BabyNamePopulation bnp = new BabyNamePopulation();
        Scene scene = new Scene(bnp);
        stage.setScene(scene);
        stage.setTitle("Baby Name Popularity");
        stage.show();
    }

    private class BabyNamePopulation extends GridPane {
        // labels
        Label lblYear;
        Label lblGender;
        Label lblName;
        Label lblResult;

        // controls
        ComboBox<String> cboxYear;
        ComboBox<String> cboxGender;
        TextField tfdName;
        Button btnFind;

        HashMap<String, Integer>[][] babyNames;
        final int MALE = 0;
        final int FEMALE = 1;

        private BabyNamePopulation() {
            String[] years = load();

            lblYear = new Label("Select Year:");
            cboxYear = new ComboBox<>(FXCollections.observableArrayList(years));
            cboxYear.setValue(years[0]);
            add(lblYear, 0, 0);
            add(cboxYear, 1, 0);

            lblGender = new Label("Boy/Girl?:");
            cboxGender = new ComboBox<>(FXCollections.observableArrayList("Boy", "Girl"));
            cboxGender.setValue("Boy");
            add(lblGender, 0, 1);
            add(cboxGender, 1, 1);

            lblName = new Label("Name:");
            tfdName = new TextField();
            add(lblName, 0, 2);
            add(tfdName, 1, 2);

            btnFind = new Button("Find Ranking");
            add(btnFind, 0, 3, 2, 1);

            lblResult = new Label("Please search for a name");
            add(lblResult, 0, 4, 2, 1);

            btnFind.setOnAction(e -> findRank());

            setHalignment(btnFind, HPos.CENTER);
            setHalignment(lblResult, HPos.CENTER);
            setHgap(10);
            setVgap(10);
            setPadding(new Insets(5, 5, 5, 5));
        }

        private String[] load() {
            String base = "http://www.cs.armstrong.edu/liang/data/babynamesranking";
            String[] urls = new String[10];
            String[] years = new String[urls.length];

            babyNames = (HashMap<String, Integer>[][]) new HashMap[2][urls.length];

            for (int i = 0; i < babyNames.length; i++) {
                for (int j = 0; j < babyNames[i].length; j++) {
                    babyNames[i][j] = new HashMap<>();
                }
            }

            for (int i = 0; i < urls.length; i++) {
                years[i] = "" + (2001 + i);
                urls[i] = base + years[i] + ".txt";

                try {
                    URL url = new URL(urls[i]);
                    Scanner in = new Scanner(url.openStream());

                    int gender = 0;
                    int rank = 0;
                    while (in.hasNext()) {
                        if (gender == 0) {
                            rank = in.nextInt();
                        }

                        String name = in.next();
                        String popularity = in.next(); // not needed
                        babyNames[gender][i].put(name, rank);

                        if (gender == 1) {
                            gender = 0;
                        } else {
                            gender++;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return years;
        }

        private void findRank() {
            String name = tfdName.getText();
            if (name.length() == 0) {
                lblResult.setText("Enter a valid name!");
            }

            int year = Integer.parseInt(cboxYear.getValue().substring(2)) - 1;
            int gender = cboxGender.getValue().equals("Boy") ? MALE : FEMALE;
            String formatName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

            if (babyNames[gender][year].containsKey(formatName)) {
                int rank = babyNames[gender][year].get(formatName);
                lblResult.setText(cboxGender.getValue() + " name " + formatName +
                        " is ranked " + rank + " for year " + (2001 + year));
            } else {
                lblResult.setText("Could not find " + name);
            }
        }
    }
}
