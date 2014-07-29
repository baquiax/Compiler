import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import java.util.Hashtable;
import java.io.File;

/**
 * Clase principal del Compilador, se encarga
 * de validar y verificar toda la informacion necesaria
 * para iniciar el proceso de compilacion.
 * @author Alexander Baquiax (alexbqb@galileo.edu)
 */

public class Compiler {

    /**
     * Verifica la existencia de un archivo a partir del path enviado.
     * @param path Representa la direccion del source code a compilar.     
     * @return boolean Devuevel true si existe el archivo, false si no.
     */

    public static boolean existsFile(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    /**
     * Unicamente imprime el resultado del commando <b>-h</b>
     */

    private static void help() {
        System.out.println(
        String.format("%-5s %5s\n", "Uso: ", "java Compiler [options] <source FILE>*\n* es requerido.\nDonde las opciones posibles son: "));
        String[][] validFlags = {
            {
                "-target <stage>*", "Donde <stage> puede ser: scan, parse, ast, semantic, irt, codegen; la compilacion porcede hasta la etapa indicada."
            }, {
                "-o <outcome>", "Escribir el output a un archivo llamado <outname>."
            }, {
                "-opt <optimization>", "<optimization> puede ser: constant o algebraic."
            }, {
                "-debug <stage1:stage2:..>", "Imprimir información en las etapas indicadas. "
            }, {
                "-h", "Muestra esta ayuda al usuario."
            }
        };

        for (String[] flag: validFlags) {
            System.out.println(
            String.format("%-25s %10s\n", flag[0], flag[1]));
        }
    }

    public static void main(String[] args) {
        //Deben haber al menos 3 parametros.            
        if (args.length == 0 || (args.length == 1 && args[0].equals("-h"))) {
            help();
            System.exit(0);
        } else if (args.length < 3) {
            Compiler.printMessageAndExit("No ha indicado los parametros minimos requeridos\nUse el flag -h para ver la ayuda.", 1);
        }

        String supportedFlags = "-target,-opt,-debug,-h,-o,";
        String[][] supportedFlagValues = {
            {
                "-target", "scan,parse,semantic,ast,irt,codegen,"
            }, {
                "-opt", "constant,algebraic,"
            }, {
                "-debug", "scan,parse,semantic,ast,irt,codegen,"
            }, {
                "-h", ""
            }
        };

        Hashtable < String, String > flags = new Hashtable < String, String > ();
                
        int i = 0;
        for (; (i + 2) <= args.length; i += 2) {
            if (!supportedFlags.contains(args[i] + ",")) Compiler.printMessageAndExit("El flag " + args[i] + ", no se reconoce.", 1);
            flags.put(args[i], args[i + 1]);
        }

        if (((args.length - i) == 0)) Compiler.printMessageAndExit("No has indicado el archivo fuente a compilar.", 1);
        flags.put("inputFile", args[args.length - 1]);

        String fileName = flags.get("inputFile");

        if (fileName.contains("/") && fileName.split("/").length > 0) {
            String[] fileNameParted = fileName.split("/");
            fileName = fileNameParted[fileNameParted.length - 1];
        }

        if(fileName.matches("[\\.-]+.*")) Compiler.printMessageAndExit("El nombre del archivo de entrada no debe empezar con . o -", 1);        

        if (!Compiler.existsFile(flags.get("inputFile"))) Compiler.printMessageAndExit("El archivo a compilar no existe!", 1);

        for (String[] f: supportedFlagValues) {
            if (flags.get(f[0]) != null && !Compiler.checkIn(flags.get(f[0]), f[1])) {
                Compiler.printMessageAndExit("\tHa indicado un valor no valido para " + f[0], 1);
            }
        }

        //Settear el flag limite. Hasta donde se debe llegar.
        if (flags.get("-target") == null)  Compiler.printMessageAndExit("\nEl flag -target es OBLIGATORIO.", 1);

        int stopStage = 1;
        for (String f: "scan,parse,semantic,ast,irt,codegen".split(",")) {
            if (f.trim().equals(flags.get("-target"))) break;
            stopStage++;
        }

        Configuration configuration = new Configuration(flags, stopStage);

        //Scanner s = new Scanner(flags, stopStage);
        //s.scan();
    }

    public static boolean checkIn(String toFind, String findIn) {
        if (toFind.contains(":")) {
            String[] values = toFind.split(":");
            for (String s: values) {
                if (!findIn.contains(s + ",")) return false;
            }
            return true;
        } else {
            return findIn.contains(toFind + ",");
        }
    }

    private static void printMessageAndExit (String message, int type) {
        //type 0 no error, 1 error.

        switch (type) {
            case 1:
                System.err.println(message);
                System.exit(type);
                break;
            default:
                System.out.println(message);
                System.exit(type);
        }
    }
}
