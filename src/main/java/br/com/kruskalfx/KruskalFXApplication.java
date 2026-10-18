package br.com.kruskalfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KruskalFXApplication extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("KruskalFX");
        title.getStyleClass().add("title");

        Label description = new Label("Visualizador do algoritmo de Kruskal.");
        description.getStyleClass().add("description");

        VBox content = new VBox(12, title, description);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(content);
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 960, 640);
        scene.getStylesheets().add(
                KruskalFXApplication.class.getResource("styles.css").toExternalForm()
        );

        stage.setTitle("KruskalFX");
        stage.setMinWidth(640);
        stage.setMinHeight(420);
        stage.setScene(scene);
        stage.show();
    }

}
