package edu.njit.cs114;

import java.util.Scanner;

/**
 * Author: Ashalesh Tilawat
 * Date created: 2/9/2023
 */
public class AtoY {

    public static void printTable(char[][] t) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j)
                System.out.print(t[i][j]);
            System.out.println();
        }
    }

    /**
     * Check if we can fill characters in the grid starting from ch in the cell (row,col)
     * The grid may already have some characters which should not be changed
     * @param t grid
     * @param row
     * @param col
     * @param ch
     * @return true if characters can be filled in else false
     */
    private static boolean solve(char[][] t, int row, int col, char ch) {

        if (ch == 'y')
            return true;

        char nextCh = (char)(ch+1);

        int newRow = -1;
        int newCol = -1;
        boolean valid = false;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: newRow = row-1; newCol = col;   valid = row>0; break;
                case 1: newRow = row;   newCol = col+1; valid = col<4; break;
                case 2: newRow = row+1; newCol = col;   valid = row<4; break;
                case 3: newRow = row;   newCol = col-1; valid = col>0; break;
            }

            if (valid && (t[newRow][newCol]=='z' || t[newRow][newCol]==nextCh)) {
                char oldCh = t[newRow][newCol];
                t[newRow][newCol] = nextCh;

                if (solve(t, newRow, newCol, nextCh))
                    return true;
                else
                    t[newRow][newCol] = oldCh;
            }
        }

        return false;
    }


    public static boolean solve(char [] [] t) {
        int row = -1, col = -1;
        for (int i=0; i < t.length; i++) {
            for (int j=0; j < t[i].length; j++) {
                if (t[i][j] == 'a') {
                    row = i;
                    col = j;
                }
            }
        }
        if (row >= 0 && col >= 0) {
            return solve(t, row, col, 'a');
        } else {
            // try every free position for starting with 'a'
            /**
             * Complete code here
             */
            for (int i=0; i < t.length; i++) {
                for (int j=0; j < t[i].length; j++) {
                    if (t[i][j] == 'z') {
                        if (solve(t, i, j, 'a')) {
                            t[i][j] = 'a';
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Enter 5 rows of lower-case letters a to z below. Note z indicates empty cell");
        Scanner sc = new Scanner(System.in);
        char[][] tbl = new char[5][5];
        String inp;
        for (int i = 0; i < 5; ++i) {
            inp = sc.next();
            for (int j = 0; j < 5; ++j) {
                tbl[i][j] = inp.charAt(j);
            }
        }
        if (solve(tbl)) {
            System.out.println("Printing the solution...");
            printTable(tbl);
        } else {
            System.out.println("There is no solution");
        }
    }
}
