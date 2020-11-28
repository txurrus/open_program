package open_program.models;

import javafx.beans.property.SimpleStringProperty;

public class Program {

    private final SimpleStringProperty programNumber;
    private final SimpleStringProperty programMachine;
    private final SimpleStringProperty programVersion;
    private final SimpleStringProperty programDate;

    public Program() {

        programNumber = new SimpleStringProperty();
        programMachine = new SimpleStringProperty();
        programVersion = new SimpleStringProperty();
        programDate = new SimpleStringProperty();
    }

    public Program(String pNumber, String pMachine, String pVersion, String pDate) {

        this();
        this.programNumber.set(pNumber);
        this.programMachine.set(pMachine);
        this.programVersion.set(pVersion);
        this.programDate.set(pDate);
    }

    public SimpleStringProperty programNumberProperty() {

        return programNumber;
    }

    public SimpleStringProperty programMachineProperty() {

        return programMachine;
    }

    public SimpleStringProperty programVersionProperty() {

        return programVersion;
    }

    public SimpleStringProperty programDateProperty() {

        return programDate;
    }

    public void setProgramNumber(String pNumber) {

        this.programNumber.set(pNumber);
    }

    public String getProgramNumber() {

        return programNumber.get();
    }

    public void setProgramMachine(String pMachine) {

        this.programMachine.set(pMachine);
    }

    public String getProgramMachine() {

        return programMachine.get();
    }

    public void setProgramVersion(String pVersion) {

        this.programVersion.set(pVersion);
    }

    public String getProgramVersion() {

        return programVersion.get();
    }

    public void setProgramDate(String pDate) {

        this.programDate.set(pDate);
    }

    public String getProgramDate() {

        return programDate.get();
    }

}
