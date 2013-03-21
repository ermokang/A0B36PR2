package semestralni.prace;

import java.io.*;
import java.util.Scanner;

public class Main {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String townfrom, townto;
        int odp, townA, townB, i;
        Automat automat = new Automat();
        automat.readTowns();
        automat.readDistanceAndPrice();
        //menu na vyber;
        do {
            System.out.println("1.Nakup;" + "\n"
                    + "2.Editace mest;" + "\n"
                    + "3.Editace vzdalenosti a ceny;" + "\n"
                    + "4.Konec.");
            odp = scan.nextInt();
            if (odp == 1) {//Nakup;
                if (odp == 4) {
                    break;
                }
                //cteni dat odkud a kam;
                townA = automat.readTownA();
                townB = automat.readTownB(townA);

                System.out.println("Vzdalenost: " + automat.Distance[townA][townB] + " km" + " Cena: " + automat.Price[townA][townB] + " Kc");//vypise cenu a vzdalenost;
                //Platba;
                int mince, now = 0, realmince = 0;
                System.out.println("Platne jsou mince(1 Kc, 2 Kc, 5 Kc, 10 Kc, 50 Kc) nebo bankovky(100 Kc, 200 Kc, 500 Kc).");
                do {
                    System.out.println("Vlozte penize. Kredit: " + now + ". Jeste nutne: " + (automat.Price[townA][townB] - now) + ".");
                    mince = scan.nextInt();
                    if ((mince == 1) || (mince == 2) || (mince == 5) || (mince == 10) || (mince == 20) || (mince == 50) || (mince == 100) || (mince == 200) || (mince == 500)) {
                        realmince = 1;
                        now += mince;
                    } else {
                        System.out.println("Neexistuje takova mince nebo bankovka. Zkuste jeste jednou.");
                    }
                } while (now < automat.Price[townA][townB]);

                System.out.print("Dekuji za nakup. Vlozili jste: " + now + " Kc. Vraceno: ");
                //vraceni zbytku;
                if ((now - automat.Price[townA][townB]) > 0) {
                    int vrac = now - automat.Price[townA][townB];
                    System.out.println(vrac + " Kc.");


                    vrac = automat.vraceniMince(vrac, 500);
                    vrac = automat.vraceniMince(vrac, 200);
                    vrac = automat.vraceniMince(vrac, 100);
                    vrac = automat.vraceniMince(vrac, 50);
                    vrac = automat.vraceniMince(vrac, 20);
                    vrac = automat.vraceniMince(vrac, 10);
                    vrac = automat.vraceniMince(vrac, 5);
                    vrac = automat.vraceniMince(vrac, 2);
                    vrac = automat.vraceniMince(vrac, 1);
                } else {
                    System.out.println("0 Kc.");
                }

            } else if (odp == 2) {//Editace mest;
                if (odp == 4) {
                    break;
                }
                String jmeno;
                int flag = 0;


                System.out.println("Uvedtte jmeno noveho mesta:");
                jmeno = scan.next();

                for (i = 1; i < automat.n; i++) {
                    if (jmeno.equals(automat.Towns[i])) {
                        System.out.println("Takove mesto uz existuje.");
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    automat.Towns[automat.n] = jmeno;
                    automat.n++;
                }

            } else if (odp == 3) {//Editace vzdalenosti a ceny;
                if (odp == 4) {
                    break;
                }
                townA = automat.readTownA();
                townB = automat.readTownB(townA);
                System.out.println("Ted' mame: vzdalenost = " + automat.Distance[townA][townB] + " km, cena = " + automat.Price[townA][townB] + " Kc");

                System.out.println("1.Zmenit vzdalenost;" + "\n"
                        + "2.Zmenit cenu.");
                String odp1;
                odp1 = scan.next();
                if (odp1.equals("1")) {
                    System.out.println("Uvedtte vzdalenost:");
                    automat.Distance[townA][townB] = scan.nextInt();
                    automat.Distance[townB][townA] = automat.Distance[townA][townB];
                }
                if (odp1.equals("2")) {
                    System.out.println("Uvedtte cenu:");
                    automat.Price[townA][townB] = scan.nextInt();
                    automat.Price[townB][townA] = automat.Price[townA][townB];
                }
            }

            automat.saveData();//prenos z dolu tabulky a zachovani novych nenelovych prvku pole do souboru;
        } while (odp != 4);//Konec programu;
    }
}