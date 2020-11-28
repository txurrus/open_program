package open_program.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import open_program.helpers.Alerta;
import open_program.helpers.Util;
import open_program.models.Program;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OpenController implements Initializable {

    @FXML
    private Button btnFolder;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Program> tblPrograms;

    @FXML
    private TableColumn<Program, byte[]> colImage;

    @FXML
    private TableColumn<Program, String> colProgram;

    @FXML
    private TableColumn<Program, String> colMachine;

    @FXML
    private TableColumn<Program, String> colVersion;

    @FXML
    private TableColumn<Program, String> colDate;

    @FXML
    private TextField txtProgram;

    private ObservableList<Program> programList;

    @FXML
    void btnCancelClick(MouseEvent event) {

        System.exit(0);
    }

    @FXML
    void btnFolderClick(MouseEvent event) {

        try {

            Desktop.getDesktop().open(new File( parentDirectory + planeDirectory));

        } catch (IllegalArgumentException | IOException e) {

            Alerta.show("", "Abrir programa",
                    e.getMessage(),true, "error");

        }
    }

    @FXML
    void txtProgramKeyPressed(KeyEvent event) {

        if(event.getCode() == KeyCode.ENTER) {

            if(txtProgram.getText().length() != 7) {

                Alerta.show("","Abrir Programa - Error en número de plano",
                        "El número de plano tiene que tener 7 dígitos.", true, "error");
                txtProgram.requestFocus();
            }
            else {

                Alerta.show("", "Abrir Programa - Número de plano",
                       "El número de plano es correcto " + txtProgram.getText(), true, "infor");
            }
        }
    }

    private String parentDirectory = null;
    private String planeDirectory = null;
    private String machineList [][] = new String[20][];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if(!Util.parentDirectory().equals("Error")) {

            parentDirectory = Util.parentDirectory();
        }
        else {

            Alerta.show("", "Abrir Programa",
                    "No se ha podido abrir el archivo de configuración. ", true, "error");

            System.exit(0);
        }

        machineList = Util.machineList();

        if(machineList[0][0].equals("Error")) {

            Alerta.show("", "Abrir Programa",
                    "No se ha podido abrir el archivo de máquinas. ", true, "error");

            System.exit(0);
        }


        programList = tblPrograms.getItems();
        programList.clear();

        colProgram.setCellValueFactory(new PropertyValueFactory<>("programNumber"));
        colMachine.setCellValueFactory(new PropertyValueFactory<>("programMachine"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("programVersion"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("programDate"));



        //tblPrograms.setVisible(false);
        btnFolder.setDisable(true);
        btnOpen.setDisable(true);

        Platform.runLater(()->txtProgram.requestFocus());
        txtProgram.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(7));
    }

    public EventHandler<KeyEvent> numericValidation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();

                if (txt_TextField.getText().length() >= max_Lengh) {

                    e.consume();
                }

                if(!e.getCharacter().matches("[0-9]")){

                    btnFolder.setDisable(true);
                    e.consume();
                }
                else if(txt_TextField.getText().length() == max_Lengh - 1) {

                    if(Util.checkPlane(txt_TextField.getText() + e.getCharacter()) == 0) {

                        btnFolder.setDisable(false);
                        tblPrograms.setVisible(true);

                        planeDirectory = Util.planeDirectory(txtProgram.getText() + e.getCharacter());

                        String progFiles[] = Util.filesExtension(parentDirectory, txtProgram.getText() + e.getCharacter(), ".prt");
                        String pathProgs = parentDirectory + planeDirectory;
                        int numFiles = progFiles.length;

                        if (numFiles == 1) {

                            programList.add(new Program(Util.programNumber(progFiles[0]), Util.programMachine(progFiles[0], machineList)
                                    ,Util.programVersion(progFiles[0])
                                    ,Util.programDate(parentDirectory + planeDirectory + progFiles[0])));
                        }

                        else if(numFiles > 1) {

                            for (String programData: progFiles) {

                                programList.add(new Program(Util.programNumber(programData), Util.programMachine(programData, machineList)
                                        ,Util.programVersion(programData)
                                        ,Util.programDate(parentDirectory + planeDirectory + programData)));
                            }

                            //programList.add(new Program("1112233", "V300","05","22/10/2020"));
                            //programList.add(new Program("2222222", "V800","00","23/10/2020"));
                        }
                        else {

                            programList.add(new Program("0", "0","0","0"));
                        }

                    }
                }

                /*if(e.getCharacter().matches("[0-9.]")){
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume();
                    }
                }else{*/
            }
        };
    }
}
