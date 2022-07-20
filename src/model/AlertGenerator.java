package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Generates all alerts used in the application.
 *
 * @author Tabish Abbasi
 */
public abstract class AlertGenerator {
    /**
     * Produces an error alert.
     *
     * @param reasonForError the message to display in the alert
     */
    public static void generateErrorAlert(String reasonForError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(reasonForError);
        alert.showAndWait();
    }

    /**
     * Produces an information alert.
     *
     * @param info the message to display in the alert
     */
    public static void generateInfoAlert(String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("InfoBox");
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * Produces a confirmation alert.
     *
     * @param confirmation the message to display in the alert
     */
    public static Optional<ButtonType> generateConfirmAlert(String confirmation){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmation);
        alert.setTitle("Are you Sure?");
        return alert.showAndWait();
    }
}
