package open_program.helpers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Alerta {

    public static void show (String cabecera, String titulo, String mensaje, boolean modificable, String tipo) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (tipo) {

            case "error":
                alert.setAlertType(Alert.AlertType.ERROR);
                break;

            case "infor":
                alert.setAlertType(Alert.AlertType.INFORMATION);
                break;

            case "confir":
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                break;

            case "alerta":
                alert.setAlertType(Alert.AlertType.WARNING);
                break;

            default:
                alert.setAlertType(Alert.AlertType.NONE);
                break;

        }

        alert.setHeaderText(cabecera);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.setResizable(modificable);

        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(
                Alerta.class.getResourceAsStream("../resources/app_icon.png")));

        alert.onShownProperty().addListener(ex -> {
            Platform.runLater(() -> alert.setResizable(false));
        });

        alert.showAndWait();
    }

}