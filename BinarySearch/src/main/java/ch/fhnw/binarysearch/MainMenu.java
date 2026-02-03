package ch.fhnw.binarysearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f5f5f5, #e8e8e8);");

        // Titel
        Label title = new Label("Algorithmen-Demonstrator");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: #343d46;");

        // Binary Search Button
        Button binarySearchBtn = createAlgorithmButton(
                "Binary Search",
                "#0065A4"
        );
        binarySearchBtn.setOnAction(e -> openBinarySearch(stage));

        // MergeSort Button
        Button mergeSortBtn = createAlgorithmButton(
                "MergeSort",
                "#6ba43a"
        );
        mergeSortBtn.setOnAction(e -> openMergeSort(stage));
        mergeSortBtn.setDisable(true); // Noch nicht implementiert

        root.getChildren().addAll(title, binarySearchBtn, mergeSortBtn);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Algorithmen-Demonstrator - FHNW");
        stage.setScene(scene);
        stage.show();
    }

    private Button createAlgorithmButton(String mainText, String color) {
        Button btn = new Button();

        // Haupt- und Untertitel
        Label main = new Label(mainText);
        main.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        main.setStyle("-fx-text-fill: white;");

        VBox content = new VBox(8, main);
        content.setAlignment(Pos.CENTER);

        btn.setGraphic(content);
        btn.setPrefSize(500, 120);
        btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);",
                color
        ));

        // Hover-Effekt
        btn.setOnMouseEntered(e -> btn.setStyle(String.format(
                "-fx-background-color: derive(%s, -10%%); " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 7); " +
                        "-fx-scale-x: 1.02; -fx-scale-y: 1.02;",
                color
        )));

        btn.setOnMouseExited(e -> btn.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);",
                color
        )));

        return btn;
    }

    private void openBinarySearch(Stage mainStage) {
        try {
            BinarySearchGUI binarySearch = new BinarySearchGUI();
            Stage binaryStage = new Stage();
            binarySearch.start(binaryStage);
            mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMergeSort(Stage mainStage) {
        // TODO: Implementiere MergeSort
        }

    public static void main(String[] args) {
        launch(args);
    }
}

