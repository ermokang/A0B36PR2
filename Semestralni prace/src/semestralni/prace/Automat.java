package semestralni.prace;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Automat {

    private Scanner scan;
    private String Towns[];//pole  ze jmen mest;
    int Distance[][];//pole se hodnotami vzdalenosti;
    int Price[][];//pole se hodnotami cen jizdenek;
    int n;//pocet mest, ktere mame;

    public Automat() {
    }

    public int vraceniMince(int vrac, int mince) {
        //pocita kolik automat musi vracet penez hodnoty mince, kdyz pro vraceni ma castku vrac korun;
        if ((vrac / mince) > 0) {
            System.out.println((vrac / mince) + " x " + mince + " Kc");
            vrac -= (vrac / mince) * mince;
        }
        return vrac;
    }

    public int readTownA() {
        //cteni a kontrola jmena mesta ve seznamu moznych jmen mest pole towns, odkud uzivatel chce jet;
        Scanner scan = new Scanner(System.in);
        String townfrom;
        int townA = 0, i;
        do {
            townA = 0;
            System.out.println("Odkud:");
            townfrom = scan.next();
            for (i = 1; i < n; i++) {
                if (townfrom.equals(Towns[i])) {
                    townA = i;
                    break;
                }
            }
            if (townA == 0) {
                System.out.println("Mesto neexistuje. Zkuste jeste jednou.");
            }
        } while (townA == 0);
        return townA;
    }

    public int readTownB(int townA) {
        //cteni a kontrola jmena mesta ve seznamu možnych jmen mest pole towns, kam uzivatel chce jet;
        Scanner scan = new Scanner(System.in);
        String townto;
        int townB = 0, i;
        do {
            townB = 0;
            System.out.println("Kam:");
            townto = scan.next();
            for (i = 1; i < n; i++) {
                if (townto.equals(Towns[i])) {
                    townB = i;
                    break;
                }
            }
            if (townA == townB) {
                System.out.println("Vy uz vybrali toto mesto jako vychozni mesto. Zkuste jeste jednou.");
            }
            if (townB == 0) {
                System.out.println("Mesto neexistuje. Zkuste jeste jednou.");
            }
        } while ((townB == 0) || (townA == townB));
        return townB;
    }

    public void readTowns() {
        //cteni souboru towns.txt a vytvoreni pole Towns se seznamem moznych mest;

        Towns = new String[100];
        try {
            int i = 1;
            scan = new Scanner(new FileInputStream("towns.txt"));
            while (scan.hasNext()) {
                Towns[i] = scan.next();
                i++;
            }
            n = i;
        } catch (IOException ex) {
            System.out.println("Soubor towns.txt nebyl nalezen.");
        }
    }

    public void readDistanceAndPrice() {
        //čtení souboru distance.text a vytvoreni dvourozmernych polu Price a Distance pro chraneni vzdalenosti mezi mestami a cen na jizdenky;
        Distance = new int[100][100];
        Price = new int[100][100];
        try {
            int m, k, distan, price, i = 0;
            scan = new Scanner(new FileInputStream("distance.txt"));
            while (scan.hasNext()) {
                k = scan.nextInt();
                m = scan.nextInt();
                distan = scan.nextInt();
                price = scan.nextInt();
                Distance[k][m] = distan;
                Distance[m][k] = distan;
                Price[k][m] = price;
                Price[m][k] = price;
                i++;
            }
        } catch (IOException ex) {
            System.out.println("Soubor distance.txt nebyl nalezen.");
        }
    }

    public void saveData() {
        //editovani existujicich souboru towns.txt a distance.txt pro pristi pouziti;
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream("towns.txt"));
            for (int i = 1; i < n; i++) {
                if (!Towns[i].equals("")) {
                    out.write(Towns[i] + "\n");
                }
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Soubor towns.txt nebyl nalezen.");
        }

        try {
            Writer out = new OutputStreamWriter(new FileOutputStream("distance.txt"));
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < i; j++) {
                    if ((Distance[i][j] != 0) && (Price[i][j] != 0)) {
                        out.write(i + " " + j + " " + Distance[i][j] + " " + Price[i][j] + "\n");
                    }
                }
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Soubor distance.txt nebyl nalezen.");
        }
    }
}
