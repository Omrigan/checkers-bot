import java.io.*;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Created by oleg on 2/11/16.
 */

class State {
    char[][] field;
    boolean whiteTurn = true;
    int whiteLeft = 12;
    int blackLeft = 12;
    boolean gameFinihed = false;

    State() {
        field = new char[8][8];
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
    }

    char contentAt(int[] coors)
    {
        return field[coors[0]][coors[1]];
    }

    void applyTurn(Turn t) {

    }
}

class Turn {
    boolean whiteTurn;
    int[] coorsFrom, coorsTo;

    Turn(boolean whiteTurn, int[] coorsFrom, int[] coorsTo) {
        this.coorsFrom = coorsFrom;
        this.coorsTo = coorsTo;
        this.whiteTurn = whiteTurn;
    }
}


public class Main {
    int[][] dirs = new int[][]{
            new int [] {1, -1},
            new int [] {1, 1},
            new int [] {-1, -1},
            new int [] {-1, 1},

    };
    String[] help = new String[8];
    Scanner in = new Scanner(System.in);
    PrintWriter out = new PrintWriter(System.out);

    void reprintField(State s, char[][] layer) {
        for (int i = 7; i >= 0; i--) {
            out.print((char) ('1' + i));
            out.print(' ');
            for (int j = 0; j < 8; j++) {
                if (layer[i][j] != 0)
                    out.print(layer[i][j]);
                else
                    out.print(s.field[i][j]);
            }
            out.print("  ");
            out.println(help[i]);
        }
        out.print("  ");
        for (int i = 0; i < 8; i++) {
            out.print((char) ('A' + i));
        }
        out.println();

    }
    int[] parseCoors(String nxt){
        int[] ans = new int[2];
        if (nxt.length() == 2) {
            ans[0] = nxt.charAt(1);
            ans[1] = nxt.charAt(0);
            if ('A' <= ans[1] && ans[1] <= 'H')
                ans[1] -= 'A';
            else
                ans[1] = -1;
            if ('1' <= ans[0] && ans[0] <= '9')
                ans[0] -= '1';
            else
                ans[0] = -1;
        }
        if(ans[0]==-1 || ans[1]==-1)
            return null;
        else
            return ans;

    }


    void startGame() throws IOException {
        boolean gameIsOn = true;

        BufferedReader buff = new BufferedReader(new FileReader("help.txt"));
        for (int i = 0; i < 8; i++) {
            help[i] = buff.readLine();
        }
        State s = new State();
        char[][] layer = new char[8][8];


        while (!s.gameFinihed) {
            for (int i = 0; i < 3; ++i) out.println();

            int[] coorsFrom = null, coorsTo = null;
            while (coorsFrom==null || coorsTo ==null) {
                layer = new char[8][8];
                reprintField(s, layer);
                do {

                    out.print("Select your checker: ");
                    out.flush();
                    String nxt = in.next();
                    coorsFrom = parseCoors(nxt);

                } while (coorsFrom==null || s.contentAt(coorsFrom) != 'W');
                layer[coorsFrom[0]][coorsFrom[1]] = '@';

                for (int i = 0; i < 2; i++) {
                    coorsFrom[0]+=dirs[i][0];
                    coorsFrom[1]+=dirs[i][1];
                    if(s.contentAt(coorsFrom)=='#'){
                        layer[coorsFrom[0]][coorsFrom[1]] = 'O';
                        layer[coorsFrom[0]][coorsFrom[1]] = 'O';

                    }
                    coorsFrom[0]-=dirs[i][0];
                    coorsFrom[1]-=dirs[i][1];
                }
                ArrayDeque<int[]> eat = new ArrayDeque<>();
                eat.add(coorsFrom);
                while (!eat.isEmpty()){
                    int[] cur = eat.pop();
                    for (int i = 0; i < 4; i++) {
                        cur[0]+=dirs[i][0];
                        cur[1]+=dirs[i][1];
                        if(s.contentAt(coorsFrom)=='B'){
                            cur[0]+=dirs[i][0];
                            cur[1]+=dirs[i][1];
                            if(s.contentAt(coorsFrom)=='#'){
                                layer[coorsFrom[0]][coorsFrom[1]] = 'O';
                                eat.add(coorsFrom.clone());
                            }
                        }
                    }

                }



                reprintField(s, layer);
                out.print("OK. Choose target (U for unselect): ");
                out.flush();
                do {
                    String nxt = in.next();
                    if (nxt.equals("U")) {
                        break;
                    }
                    coorsTo=parseCoors(nxt);
                } while (coorsTo==null  || layer[coorsTo[0]][coorsTo[1]] != 'O');
            }
            Turn t = new Turn(true, coorsFrom, coorsTo);
            s.applyTurn(t);



        }


    }


    public static void main(String[] argc) throws IOException {
        Main m = new Main();
        m.startGame();

    }
}
