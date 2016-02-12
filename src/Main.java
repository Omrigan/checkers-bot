import java.io.*;
import java.util.Scanner;

/**
 * Created by oleg on 2/11/16.
 */
public class Main {

    char[][] field = new char[8][8];
    String[] help = new String[8];

    void startGame() throws IOException {
        boolean gameIsOn = true;

        BufferedReader buff = new BufferedReader(new FileReader("help.txt"));
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        for (int i = 0; i < 8; i++) {
            help[i] = buff.readLine();
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {

                    field[i][j] = '.';

                } else {
                    if (i < 3)
                        field[i][j] = 'W';
                    else if (i > 4)
                        field[i][j] = 'B';
                    else
                        field[i][j] = '#';
                }
            }
        }


        while (gameIsOn) {
            for (int i = 0; i < 3; ++i) out.println();

            for (int i = 7; i >= 0; i--) {
                out.print((char) (i));
                out.print(' ');
                for (int j = 0; j < 8; j++) {

                    out.print(field[i][j]);
                }
                out.print("  ");
                out.println(help[i]);
            }
            out.print("  ");
            for (int i = 0; i < 8; i++) {
                out.print((char) ('A' + i));
            }
            out.println();
            out.print("Select your checker: ");
            out.flush();
            int x = -1, y = 1;
            do {


                String nxt = in.next();

                if (nxt.length() == 2) {
                    x = nxt.charAt(0);
                    y = nxt.charAt(1);
                    if ('A' <= x && x <= 'H')
                        x -= 'A';
                    else
                        x = -1;
                    if ('0' <= y && y <= '9')
                        y -= '0';
                    else
                        y = -1;
                }
            }while (x!=-1 && y!=-1 && field[x][y]=='W');



        }


    }


    public static void main(String[] argc) throws IOException {
        Main m = new Main();
        m.startGame();

    }
}
