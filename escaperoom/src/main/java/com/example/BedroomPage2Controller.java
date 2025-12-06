package com.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

public class BedroomPage2Controller {

    // Routes used by App.setRoot(...) (reflection). Adjust names if your App uses different route keys.
    private static final String HOME_ROUTE = "landing";
    private static final String ROOMS_ROUTE = "explorerooms";
    private static final String ITEMS_ROUTE = "items";
    private static final String NEXT_ROUTE = "Bedroom3"; // adjust if you have a different next route

    // Fallback candidate FXML paths to load if App.setRoot wasn't available
    private static final String[] PREV_CANDIDATES = {
            "/com/example/ExploreRooms.fxml",
            "/com/example/explorerooms.fxml",
            "/com/example/ExploreRoomsPage.fxml",
            "/com/example/exploreRooms.fxml"
    };

    private static final String[] NEXT_CANDIDATES = {
            "/com/example/Bedroom3.fxml",
            "/com/example/Bedroom2.fxml" // fallback if Bedroom3 not present
    };

    @FXML private ImageView bgImage;
    @FXML private Button backBtn1;
    @FXML private Button nextBtn1;

    // collected nav items (.nav-item labels)
    private final LinkedHashSet<Label> navItems = new LinkedHashSet<>();

    // runtime-located controls (because FXML didn't provide fx:id for them)
    private TextField answerField;
    private Button enterButton;
    private Button hintButton;

    @FXML
    private void initialize() {
        // wait for scene to be attached to bgImage or run after layout
        if (bgImage != null) {
            bgImage.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    Platform.runLater(() -> {
                        setupScene(newScene);
                    });
                }
            });
        }

        // also attempt immediate wiring if scene already present
        Platform.runLater(() -> {
            Scene scene = bgImage != null ? bgImage.getScene() : null;
            if (scene != null) setupScene(scene);
        });
    }

    private void setupScene(Scene scene) {
        collectNavItems(scene);
        wireKeyboardHandlers(scene);
        locateRuntimeControls(scene);
        wireRuntimeControlHandlers();
    }

    /**
     * Find .nav-item labels and attach mouse handlers (same UX as BedroomPage1Controller).
     */
    private void collectNavItems(Scene scene) {
        Set<Node> nodes = scene.getRoot().lookupAll(".nav-item");
        for (Node n : nodes) {
            if (n instanceof Label) {
                Label lbl = (Label) n;
                navItems.add(lbl);
                lbl.setOnMouseClicked(e -> {
                    String text = lbl.getText() == null ? "" : lbl.getText().trim();
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
            // add more nav items here if needed
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
            } else if (e.getCode() == KeyCode.ENTER) {
                // if focus is in the answer field, treat Enter as submit
                if (answerField != null && answerField.isFocused()) {
                    onEnter(null);
                    e.consume();
                }
            }
        });
    }

    /**
     * Try to find controls that were not given fx:id in the FXML:
     * - first TextField in scene (assumed to be the answer input)
     * - Button with exact text "ENTER"
     * - Button with exact text "HINT ?"
     */
    private void locateRuntimeControls(Scene scene) {
        // find TextField(s)
        Set<Node> tfs = scene.getRoot().lookupAll(".text-field");
        if (!tfs.isEmpty()) {
            for (Node n : tfs) {
                if (n instanceof TextField) {
                    answerField = (TextField) n;
                    break;
                }
            }
        }

        // find Buttons by their text (ENTER, HINT ?). If multiple found, pick first matching.
        Set<Node> btns = scene.getRoot().lookupAll(".button");
        for (Node n : btns) {
            if (!(n instanceof Button)) continue;
            Button b = (Button) n;
            String txt = b.getText() == null ? "" : b.getText().trim();
            if ("ENTER".equalsIgnoreCase(txt) && enterButton == null) {
                enterButton = b;
            } else if ("HINT ?".equalsIgnoreCase(txt) && hintButton == null) {
                hintButton = b;
            }
            if (enterButton != null && hintButton != null) break;
        }
    }

    private void wireRuntimeControlHandlers() {
        if (enterButton != null) {
            enterButton.setOnAction(this::onEnter);
        }
        if (hintButton != null) {
            hintButton.setOnAction(this::onHint);
        }

        // Optional: press Enter while focus in TextField triggers enter action
        if (answerField != null) {
            answerField.setOnAction(this::onEnter);
        }
    }

    /**
     * Back button handler (wired in FXML to onBack)
     */
    @FXML
    private void onBack(ActionEvent event) {
        boolean ok = tryAppSetRoot(ROOMS_ROUTE);
        if (!ok) navigateToFirstAvailable(PREV_CANDIDATES, "Explore Rooms");
    }

    /**
     * Next button handler (wired in FXML to onNext)
     */
    @FXML
    private void onNext(ActionEvent event) {
        boolean ok = tryAppSetRoot(NEXT_ROUTE);
        if (!ok) navigateToFirstAvailable(NEXT_CANDIDATES, "Next Bedroom");
    }

    /**
     * ENTER button (submit answer to the riddle). If the answer is correct -> navigate next.
     * Riddle in your FXML: "WHAT EXISTS WHEN ONE PERSON HAS IT BUT CEASES TO EXIST WHEN ANOTHER PERSON GETS IT?"
     * Classic answer: "secret"
     */
    @FXML
    private void onEnter(ActionEvent event) {
        String answer = null;
        if (answerField != null) answer = answerField.getText();

        if (answer == null || answer.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Answer required");
            a.setHeaderText(null);
            a.setContentText("Please enter an answer before pressing ENTER.");
            a.showAndWait();
            return;
        }

        String norm = answer.trim().toLowerCase();
        if (norm.equals("secret") || norm.equals("a secret")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Correct");
            a.setHeaderText(null);
            a.setContentText("Correct — it is a secret!!!");
            a.showAndWait();

            // navigate to next route
            boolean ok = tryAppSetRoot(NEXT_ROUTE);
            if (!ok) navigateToFirstAvailable(NEXT_CANDIDATES, "Next Bedroom");
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Incorrect");
            a.setHeaderText(null);
            a.setContentText("That's not correct. Try again or press HINT ?");
            a.showAndWait();
        }
    }

    /**
     * HINT button: show a gentle hint.
     */
    @FXML
    private void onHint(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Hint");
        a.setHeaderText(null);
        a.setContentText("Think of something that stops being 'what it is' once someone else learns it.");
        a.showAndWait();
    }

    /**
     * Try to call com.example.App.setRoot(String) via reflection (your other controllers use this pattern).
     * Returns true if successful, false otherwise.
     */
    private boolean tryAppSetRoot(String routeName) {
        try {
            Class<?> appClass = Class.forName("com.example.App");
            java.lang.reflect.Method method = appClass.getMethod("setRoot", String.class);
            method.invoke(null, routeName);
            return true;
        } catch (Exception e) {
            // intentionally swallow - caller will fall back to FXML navigation
            return false;
        }
    }

    /**
     * Fallback navigator: attempt to load first candidate FXML that exists and set it on the current scene.
     */
    private void navigateToFirstAvailable(String[] candidates, String name) {
        Scene scene = bgImage != null ? bgImage.getScene() : null;
        if (scene == null) return;

        for (String candidate : candidates) {
            URL url = getClass().getResource(candidate);
            if (url == null) continue;
            try {
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                scene.setRoot(root);
                return;
            } catch (IOException ignored) {}
        }

        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Navigation Error");
        a.setHeaderText(null);
        a.setContentText("Could not load: " + name);
        a.showAndWait();
    }

    /**
     * Utility to set the background image from code (same helper pattern as BedroomPage1Controller).
     */
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
