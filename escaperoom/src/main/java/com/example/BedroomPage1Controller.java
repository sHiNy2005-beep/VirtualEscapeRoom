package com.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

public class BedroomPage1Controller {

    private static final String HOME_ROUTE = "landing";
    private static final String ROOMS_ROUTE = "explorerooms";
    private static final String ITEMS_ROUTE = "items";
    private static final String BEDROOM2_ROUTE = "Bedroom2";

    private static final String[] PREV_CANDIDATES = {
            "/com/example/ExploreRooms.fxml",
            "/com/example/explorerooms.fxml",
            "/com/example/ExploreRoomsPage.fxml",
            "/com/example/exploreRooms.fxml"
    };

    private static final String[] NEXT_CANDIDATES = {
            "/com/example/Bedroom2.fxml",
    };

    @FXML private ImageView bgImage;
    @FXML private Button backBtn;
    @FXML private Button nextBtn;

    private final LinkedHashSet<Label> navItems = new LinkedHashSet<>();

    @FXML
    private void initialize() {
        if (bgImage != null) {
            bgImage.sceneProperty().addListener((obs, o, n) -> {
                if (n != null) {
                    Platform.runLater(() -> {
                        collectNavItems(n);
                        wireKeyboardHandlers(n);
                    });
                }
            });
        }

        Platform.runLater(() -> {
            Scene scene = bgImage != null ? bgImage.getScene() : null;
            if (scene != null) {
                collectNavItems(scene);
                wireKeyboardHandlers(scene);
            }
        });
    }

    private void collectNavItems(Scene scene) {
        Set<Node> nodes = scene.getRoot().lookupAll(".nav-item");
        for (Node n : nodes) {
            if (n instanceof Label) {
                Label lbl = (Label) n;
                navItems.add(lbl);
                lbl.setOnMouseClicked(e -> {
                    String text = lbl.getText().trim();
                    if ("Rooms".equals(text)) {
                        boolean ok = tryAppSetRoot(ROOMS_ROUTE);
                        if (!ok) navigateToFirstAvailable(PREV_CANDIDATES, "Explore Rooms");
                    } else {
                        handleNavLabelClick(text);
                    }
                });
                lbl.setOnMouseEntered(e -> lbl.setOpacity(0.85));
                lbl.setOnMouseExited(e -> lbl.setOpacity(1.0));
            }
        }

        boolean anyActive = navItems.stream().anyMatch(l -> l.getStyleClass().contains("active"));
        if (!anyActive && !navItems.isEmpty()) {
            setActiveNav(navItems.iterator().next());
        }
    }

    private void handleNavLabelClick(String text) {
        switch (text) {
            case "Home":
                if (!tryAppSetRoot(HOME_ROUTE)) {
                    navigateToFirstAvailable(new String[]{"/com/example/landing.fxml"}, "Home");
                }
                break;
            case "Items":
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Items");
                a.setHeaderText(null);
                a.setContentText("Items list not implemented yet.");
                a.showAndWait();
                break;
        }
    }

    private void setActiveNav(Label active) {
        for (Label l : navItems) {
            if (l == active) {
                if (!l.getStyleClass().contains("active")) l.getStyleClass().add("active");
            } else {
                l.getStyleClass().remove("active");
            }
        }
    }

    private void wireKeyboardHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.LEFT) {
                onBack(null);
                e.consume();
            } else if (e.getCode() == KeyCode.RIGHT) {
                onNext(null);
                e.consume();
            }
        });
    }

    @FXML
    private void onBack(ActionEvent event) {
        boolean ok = tryAppSetRoot(ROOMS_ROUTE);
        if (!ok) navigateToFirstAvailable(PREV_CANDIDATES, "Explore Rooms");
    }

    @FXML
    private void onNext(ActionEvent event) {
        boolean ok = tryAppSetRoot(BEDROOM2_ROUTE);
        if (!ok) navigateToFirstAvailable(NEXT_CANDIDATES, "Bedroom 2");
    }

    private boolean tryAppSetRoot(String routeName) {
        try {
            Class<?> appClass = Class.forName("com.example.App");
            java.lang.reflect.Method method = appClass.getMethod("setRoot", String.class);
            method.invoke(null, routeName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void navigateToFirstAvailable(String[] candidates, String name) {
        Scene scene = bgImage != null ? bgImage.getScene() : null;
        if (scene == null) return;

        for (String candidate : candidates) {
            URL url = getClass().getResource(candidate);
            if (url == null) continue;
            try {
                FXMLLoader loader = new FXMLLoader(url);
                Node root = loader.load();
                scene.setRoot((javafx.scene.Parent) root);
                return;
            } catch (IOException ignored) {}
        }

        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Navigation Error");
        a.setHeaderText(null);
        a.setContentText("Could not load: " + name);
        a.showAndWait();
    }

    public void setBackgroundImage(String path) {
        Platform.runLater(() -> {
            try {
                Image img;
                URL url = getClass().getResource(path);
                if (url != null) img = new Image(url.toExternalForm(), true);
                else img = new Image(path, true);
                if (bgImage != null) bgImage.setImage(img);
            } catch (Exception ignored) {}
        });
    }
}
