package open_program.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public static String planeDirectory (String plane_number) {

        // A esta función ya llega filtrado un número de plano con siete dígitos

        return plane_number.substring(0,3) + "/" + plane_number.substring(3,5) + "/" + plane_number + "/";
    }

    public static boolean existDirectory (String plane_number) throws FileNotFoundException {

        return Files.isDirectory(Paths.get(parentDirectory() + planeDirectory(plane_number)));
    }

    public static String actualDirectory()  {

        String currentDirectory = Paths.get("").toAbsolutePath().toString();

        if(currentDirectory.charAt(currentDirectory.length() - 1) != '/') {

            currentDirectory += "/";
        }

        return  currentDirectory;

    }

    public static String parentDirectory()  {

        try {

            Scanner br = new Scanner(new File(actualDirectory() + "open_program.cfg"));

            while (br.hasNext()) {

                String lineText = br.nextLine();

                if (lineText.charAt(0) != '#') {

                    br.close();
                    return lineText;
                }
            }
        }catch (FileNotFoundException ex) {

            return "Error";
        }

        return "Error";
    }

    public static String [][] machineList()  {

        String [][] machineList = new String[20][2];
        int index = 0;

        try {

            Scanner br = new Scanner(new File(actualDirectory() + "op_machines.cfg"));

            while (br.hasNext()) {

                String lineText = br.nextLine();

                if (lineText.charAt(0) != '#') {

                    machineList[index] = lineText.split("-");
                    index ++;
                }
            }

            br.close();
            return machineList;

        }catch (FileNotFoundException ex) {

            machineList[0][0] = "Error";
            return machineList;
        }
    }

    public static int checkPlane(String planeNumber)  {

        String directory = null;

        directory = Util.parentDirectory();

        if (!directory.equals("Error")) {

            try {
                if(!existDirectory(planeNumber)) {

                   /* Alerta.show("", "Abrir programa",
                            "No existe el directorio." + Util.parentDirectory() + Util.planeDirectory(planeNumber)
                            ,true, "error");*/

                    return 1;
                }
            } catch (FileNotFoundException e) {

                Alerta.show("", "Abrir programa",
                        e.getMessage(),true, "error");
            }
        }
        else {

            Alerta.show("", "Abrir programa","Error al leer el archivo de configuración."
                ,true, "error");
        }

        return 0;
    }
   public static String[] filesExtension(String parentDirectory, String planeNumber, String extension) {

        File fileDir = new File(parentDirectory + planeDirectory(planeNumber));
        String[] programs = fileDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extension);
            }
        });

        return programs;
    }

    public static String programNumber(String programName) {

        programName = programName.substring(0, programName.length() - 4);
        String [] programParts = programName.split("_");

        if(programParts.length != 3) {

            return "Error";
        }

        return programParts[0];
    }

    public static String programMachine(String programName, String [][] machineList) {

        programName = programName.substring(0, programName.length() - 4);
        String [] programParts = programName.split("_");

        if(programParts.length != 3) {

            return "Error";
        }

        for(String[] machine : machineList) {

            if(machine[0]!= null && machine[0].equals(programParts[1].toUpperCase())) {

                return machine[1];
            }
        }

        return "Máquina desconocida";
    }

    public static String programVersion(String programName) {

        programName = programName.substring(0, programName.length() - 4);
        String [] programParts = programName.split("_");

        if(programParts.length != 3) {

            return "Error";
        }

        return programParts[2];
    }

    public static String programDate(String programName) {

        File file = new File(programName);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(file.lastModified());
    }
}
