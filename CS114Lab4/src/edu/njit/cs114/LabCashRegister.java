package edu.njit.cs114;

import java.util.Arrays;

/**
 * Author: Ashalesh Tilawat
 * Date created: 2/6/2023
 */
public class LabCashRegister {

    private static final int INFINITY = Integer.MAX_VALUE;

    private int [] denominations;

    /**
     * Constructor
     * @param denominations values of coin types with no particular order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public LabCashRegister(int [] denominations) throws Exception {
        /**
         * Complete code here
         */
        boolean pennyExists = false;
        this.denominations = Arrays.copyOf(denominations,denominations.length);
        for (int i=0; i<denominations.length; i++) {
            if (denominations[i] == 1) {
                pennyExists = true;
            }
        }
        if (pennyExists == false) {
            throw new Exception("Coin of unit denomination does not exist");
        }

    }

    /**
     * Recursive function that finds the minimum number of coins to make change for the given
     * value using only denominations that are in indices
     * startDenomIndex, startDenomIndex+1,.....denomonations.length-1 of the denominations array
     * @param startDenomIndex
     * @param remainingValue
     * @return
     */
    private int minimumCoinsForChange(int startDenomIndex, int remainingValue) {
        /**
         * Complete code
         */
        // base case
        if (startDenomIndex == denominations.length) {
            if (remainingValue == 0) {
                return 0;
            }
            else {
                return INFINITY;
            }
        }
        // recursive case
        int maxCoinsForStartDenom = remainingValue / denominations[startDenomIndex];
        int minCoinsFromStartDenomIndex = INFINITY;
        for (int i=0; i<=maxCoinsForStartDenom; i++) {
            int remainingValueForSubseqDenom = remainingValue - i*denominations[startDenomIndex];
            int minCoinsForSubseqDenom = minimumCoinsForChange(startDenomIndex+1, remainingValueForSubseqDenom);
            if (minCoinsForSubseqDenom == INFINITY) continue;
            int nCoinsFromStartDenomIndex = i + minCoinsForSubseqDenom;
            if (nCoinsFromStartDenomIndex < minCoinsFromStartDenomIndex) {
                minCoinsFromStartDenomIndex = nCoinsFromStartDenomIndex;
            }
        }
        return minCoinsFromStartDenomIndex;
    }

    /**
     * Wrapper function that finds the minimum number of coins to make change for the given value
     * @param value value for which to make change
     * @return
     */
    public int minimumCoinsForChange(int value) {
        /**
         * Complete code here
         */
        return minimumCoinsForChange(0, value);
    }

    public static void main(String [] args) throws Exception {
        LabCashRegister reg = new LabCashRegister(new int [] {50, 25, 10, 5, 1});
        // should have a total of 6 coins
        int nCoins = reg.minimumCoinsForChange(48);
        System.out.println("Minimum coins to make change for " + 48
                + " from {50,25,10,5,1} = "+ nCoins);
        assert nCoins == 6;
        // should have a total of 3 coins
        nCoins = reg.minimumCoinsForChange(56);
        System.out.println("Minimum coins to make change for " + 56
                + " from {50,25,10,5,1} = "+ nCoins);
        assert nCoins == 3;
        reg = new LabCashRegister(new int [] {25, 10, 1});
        // should have a total of 6 coins
        nCoins = reg.minimumCoinsForChange(33);
        System.out.println("Minimum coins to make change for " + 33
                + " from {25,10,1} = "+ nCoins);
        assert nCoins == 6;
        reg = new LabCashRegister(new int [] {1, 7, 24, 42});
        // should have a total of 2 coins
        nCoins = reg.minimumCoinsForChange(48);
        System.out.println("Minimum coins to make change for " + 48
                + " from {1,7,24,42} = "+ nCoins);
        assert nCoins == 2;
        reg = new LabCashRegister(new int [] {50, 1, 3, 16, 30});
        // should have a total of 3 coins
        nCoins = reg.minimumCoinsForChange(35);
        System.out.println("Minimum coins to make change for " + 35
                + " from {50,1,3,16,30} = "+ nCoins);
        assert nCoins == 3;
    }
}

