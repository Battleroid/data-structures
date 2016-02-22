import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by casey on 2016-02-22.
 */
public class Exercise22_19 extends Application {
    static final int SIZE = 10;
    TextField[][] tf = new TextField[SIZE][SIZE];
    BorderPane bp = new BorderPane();
    GridPane gp = new GridPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // controls
        HBox ctrlBox = new HBox();
        Button randomizeBtn = new Button("Randomize");
        Button findBtn = new Button("Find Block");
        ctrlBox.getChildren().addAll(randomizeBtn, findBtn);
        ctrlBox.setAlignment(Pos.CENTER);
        ctrlBox.setPadding(new Insets(15));
        randomizeBtn.setOnAction(e -> {
            randomize();
        });
        findBtn.setOnAction(e -> {
            find();
        });

        // init textfields
        for (int i = 0; i < tf.length; i++) {
            for (int j = 0; j < tf.length; j++) {
                tf[i][j] = new TextField();
                gp.add(tf[i][j], i, j);
            }
        }

        // scene
        bp.setCenter(gp);
        bp.setBottom(ctrlBox);
        Scene scene = new Scene(bp, 300, 300);
        stage.setTitle("Maximum Sub Matrix");
        stage.setScene(scene);
        stage.show();

        // init
        randomize();
    }

    public void find() {
        int[][] surr = new int[SIZE][SIZE];

        // copy first row/col
        for (int i = 0; i < tf.length; i++) {
            surr[0][i] = Integer.parseInt(tf[0][i].getText());
        }
        for (int i = 0; i < tf.length; i++) {
            surr[i][0] = Integer.parseInt(tf[i][0].getText());
        }

        // check if tf pos is 1
        for (int i = 1; i < tf.length; i++) {
            for (int j = 1; j < tf.length; j++) {
                if (Integer.parseInt(tf[i][j].getText()) == 1) {
                    surr[i][j] = Math.min(surr[i - 1][j - 1], Math.min(surr[i][j - 1], surr[i - 1][j])) + 1;
                } else {
                    surr[i][j] = 0;
                }
            }
        }

        // find max size of matrix
        int max = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < tf.length; i++) {
            for (int j = 0; j < tf.length; j++) {
                if (surr[i][j] > max) {
                    max = surr[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        // highlight the block
        int len = max - 1;
        int tx = x - len;
        int ty = y - len;
        for (int i = tx; i < tx + max; i++) {
            for (int j = ty; j < ty + max; j++) {
                tf[i][j].setStyle("-fx-background-color: lightcoral");
            }
        }
    }

    public void randomize() {
        Random rng = new Random();
        for (int i = 0; i < tf.length; i++) {
            for (int j = 0; j < tf.length; j++) {
                tf[i][j].setText(String.valueOf(rng.nextInt(2)));
                tf[i][j].setStyle("-fx-background-color: white");
            }
        }
    }
}
