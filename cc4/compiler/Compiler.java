import compiler.scanner.Scanner;
import compiler.parser.CC4Parser;
import compiler.ast.Ast;
import compiler.semantic.Semantic;
import compiler.irt.Irt;
import compiler.codegen.Codegen;
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
            String.format("%-5s %5s\n", "Uso: ", "java Compiler [options] <source FILE>*\n* es requerido.\nDonde las opciones posibles son: ")
        );
        String[][] validFlags = {
            {
                "-target <stage>", "Donde <stage> puede ser: scan, parse, ast, semantic, irt, codegen. Por default la compilacion porcede hasta la etapa <parser>."
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
                String.format("%-25s %10s\n", flag[0], flag[1])
            );
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        //Help por default cuando no existen arguentos
        if (args.length == 0 || (args.length == 1 && args[0].equals("-h"))) 
        	help();

		//Informacion necesaria para validar. NOT CASE-SENSITIVE
        String supportedFlags[] = {"-target","-opt","-debug","-h","-o"};
		Hashtable<String, String[]> supportedFlagValues = new Hashtable<String, String[]>();
		supportedFlagValues.put("-target", (new String[] { "scan", "parse", "semantic", "ast", "irt","codegen"}));
		supportedFlagValues.put("-opt", new String[] {"constant","algebraic"});
		supportedFlagValues.put("-debug", new String[] {"scan", "parse", "semantic", "ast", "irt", "codegen"});
		supportedFlagValues.put("-h", new String[] {});
	
        Hashtable < String, String > flags = new Hashtable < String, String > ();
               
        int i = 0;
        for (; (i + 2) <= args.length; i += 2) {
	    	//Verificar validez de flag
            if (!searchInArray(args[i], supportedFlags, false))
				Compiler.printMessageAndExit("El flag " + args[i] + ", no se reconoce.", 1);
	    
	    	//Verificar validez de valor para el flag
	    	if (!searchInArray(args[i + 1], supportedFlagValues.get(args[i]), false, ":"))
				Compiler.printMessageAndExit(args[i + 1] + " no es un valor correcto para " + args[i], 1);
	   
            flags.put(args[i], args[i + 1]);
        }

        if (((args.length - i) != 1)) 
	    	Compiler.printMessageAndExit("Debes indicar un UNICO archivo a compilar! Usa -h para ayudar.", 1);
	
        flags.put("inputFile", args[args.length - 1]);
        String fileName = flags.get("inputFile");

        if (fileName.contains("/") && fileName.split("/").length > 0) {
            String[] fileNameParted = fileName.split("/");
            fileName = fileNameParted[fileNameParted.length - 1];
        }
	
		//Verficar nombre de archivo. No se permite que el archivo empiece con [.|-]
        if(fileName.matches("[\\.-]+.*")) 
	    	Compiler.printMessageAndExit("El nombre del archivo de entrada no debe empezar con . o -", 1);        

        if (!Compiler.existsFile(flags.get("inputFile"))) 
	    	Compiler.printMessageAndExit("El archivo a compilar no existe!", 1);

        //Flag target default si no es indicado.
        if (flags.get("-target") == null) {
		flags.put("-target", "parse");
        }

        int stopStage = 1;
        for (String f: "scan,parse,semantic,ast,irt,codegen".split(",")) {
            if (f.trim().equals(flags.get("-target"))) break;
            stopStage++;
        }

        Configuration.flags=flags;
        Configuration.stopStage=stopStage;

        /*INSTANCIAS DE CLASES*/

        if (Configuration.stopStage==1) {
            instanceScanner();
        } else if (Configuration.stopStage==2) {
            instanceParser(instanceScanner());
        } else if (Configuration.stopStage==3) {
            instanceSemantic(instanceParser(instanceScanner()));
        } else if (Configuration.stopStage==4) {
            instanceAst(instanceSemantic(instanceParser(instanceScanner())));
        } else if (Configuration.stopStage==5) {
            instanceIrt(instanceAst(instanceSemantic(instanceParser(instanceScanner()))));
        } else if (Configuration.stopStage==6) {
            instanceCodegen(instanceIrt(instanceAst(instanceSemantic(instanceParser(instanceScanner())))));
        }
    }

    public static boolean searchInArray(String toFind, String[] findIn, boolean caseSensitive, String separator) {
		if (toFind.contains(":")) {
            String[] values = toFind.split(":");
            for (String s: values) {
                if (!searchInArray(toFind, findIn, caseSensitive)) return false;
            }
            return true;
        } else {
            return searchInArray(toFind, findIn, caseSensitive);
        }
    }

    public static boolean searchInArray(String toFind, String[] findIn, boolean caseSensitive) {
        for (String s : findIn) {
            if (!caseSensitive) {
                if (s.toUpperCase().equals(toFind.toUpperCase())) return true;
            } else {
                if (s.equals(toFind)) return true;
            }
        }
        return false;
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

    private static Scanner instanceScanner() {
        Scanner scanner= new Scanner();
        scanner.scan();

        return scanner;
    }

    private static CC4Parser instanceParser(Scanner scanner) {
        CC4Parser parser=new CC4Parser(scanner);
        parser.parse();

        return parser;
    }

    private static Semantic instanceSemantic(CC4Parser parser) {
        Semantic semantic = new Semantic(parser);
        semantic.checkSemantic();

        return semantic;
    }

    private static Ast instanceAst(Semantic semantic) {
        Ast ast = new Ast(semantic);
        ast.makeTree();

        return ast;
    }

    private static Irt instanceIrt(Ast ast) {
        Irt irt=new Irt(ast);
        irt.translateAst();

        return irt;
    }

    private static void instanceCodegen(Irt irt) {
        Codegen codegen=new Codegen(irt);
        codegen.generate();
    }
}
