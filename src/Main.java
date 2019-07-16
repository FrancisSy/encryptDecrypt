import java.io.*;
import java.util.*;

public class Main {
    private static String uniCodeEncrypt(String s, int key) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] += key;
        }

        String retStr = new String(c);
        return retStr;
    }

    private static String shiftCodeEncrypt(String s, int key) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 'A' &&  c[i] <= 'Z') {
                c[i] += key;

                if (c[i] > 'Z') {
                    c[i] = (char)(c[i] - 'Z' + 'A' - 1);
                }
            }
            else if (c[i] >= 'a' &&  c[i] <= 'z') {
                c[i] += key;

                if (c[i] > 'z') {
                    c[i] = (char)(c[i] - 'z' + 'a' - 1);
                }
            }
        }

        String retStr = new String(c);
        return retStr;
    }

    private static String uniCodeDecrypt(String s, int key) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] -= key;
        }

        String retStr = new String(c);
        return retStr;
    }

    private static String shiftCodeDecrypt(String s, int key) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 'A' &&  c[i] <= 'Z') {
                c[i] -= key;

                if (c[i] < 'A') {
                    c[i] = (char)(c[i] + 'Z' - 'A' + 1);
                }
            }
            else if (c[i] >= 'a' &&  c[i] <= 'z') {
                c[i] -= key;

                if (c[i] < 'a') {
                    c[i] = (char)(c[i] + 'z' - 'a' + 1);
                }
            }
        }

        String retStr = new String(c);
        return retStr;
    }

    private static String cipherMessage(String mode, String s, int key, String algorithm) {
        if (algorithm.equals("unicode")) {
            if (mode.equals("enc")) {
                return uniCodeEncrypt(s, key);
            }
            else {
                return uniCodeDecrypt(s, key);
            }
        }
        else {
            if (mode.equals("enc")) {
                return shiftCodeEncrypt(s, key);
            }
            else {
                return shiftCodeDecrypt(s, key);
            }

        }
    }

    private static String readInFile(String filePath) {
        StringBuilder str = new StringBuilder();
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                str.append(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException err) {
            System.out.println("Error: Input File does not exist");
            System.exit(0);
        }

        String retStr = str.toString();
        return retStr;
    }

    private static void writeOutFile(String filename, String s) {
        File file = new File(filename);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.close();
        }
        catch (IOException err) {
            // empty on purpose
        }
    }

    public static void main(String[] args) {
        int key = 0;
        String s = " ", mode = " ";
        String inFile = " ", outFile = " ", algorithm = " ";
        Boolean modeExist = false, dataExist = false, keyExist = false;
        Boolean inFileExist = false, outFileExist = false;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                mode = args[i + 1];
                modeExist = true;
            }
            else if (args[i].equals("-data")) {
                s = args[i + 1];
                dataExist = true;
            }
            else if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
                keyExist = true;
            }
            else if (args[i].equals("-in")) {
                inFile = args[i + 1];
                inFileExist = true;
            }
            else if (args[i].equals("-out")) {
                outFile = args[i + 1];
                outFileExist = true;
            }
            else if (args[i].equals("-alg")) {
                algorithm = args[i + 1];
            }
        }

        if (!modeExist) { // if mode was not specified
            mode = "enc";
        }

        if (inFileExist) { // input file requirement
            s = readInFile(inFile);
            s = cipherMessage(mode, s, key, algorithm);

            if (!outFileExist) { // out file does not exist, print out output
                System.out.println(s);
            }
            else { // out file exists
                writeOutFile(outFile, s);
            }
        }
        else { // data and key requirement
            while (!dataExist || !keyExist) {
                if (!dataExist) {
                    s = scanner.nextLine();
                    dataExist = true;
                }
                else if (!keyExist) {
                    key = scanner.nextInt();
                    keyExist = true;
                }
            }

            s = cipherMessage(mode, s, key, algorithm);
            System.out.println(s);
        }
    }
}
