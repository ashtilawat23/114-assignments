package edu.njit.cs114;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Author: Ashalesh Tilawat
 * Date created: 4/29/23
 */
public class OrderStatistics {

    private static Random random = new Random();

    private static void swap(int [] arr, int k1, int k2) {
        int temp = arr[k1];
        arr[k1] = arr[k2];
        arr[k2] = temp;
    }

    /**
     * Return the index pivIndex of the pivot element after partitioning sub array
     * After partitioning left sub array will be in dataArr[fromIndex..pivIndex-1]
     *  right sub array will be in dataArr[pivIndex...toIndex-1]
     * @param dataArr
     * @param fromIndex starting index of sub array
     * @param toIndex ending index (not including) of sub array i.e sub array is given
     *                by dataArr[fromIndex..toIndex-1]
     * @param pivotElementIdx index of pivot element before partitioning
     * @return
     */
    public static int partition(int[] dataArr, int fromIndex, int toIndex, int pivotElementIdx) {
        // Move pivot to the end of the array
        swap(dataArr, pivotElementIdx, toIndex - 1);
        int pivot = dataArr[toIndex - 1];
        int i = fromIndex - 1;

        for (int j = fromIndex; j < toIndex - 1; j++) {
            if (dataArr[j] <= pivot) {
                i++;
                swap(dataArr, i, j);
            }
        }

        // Move pivot to its correct position
        swap(dataArr, i + 1, toIndex - 1);
        return i + 1;
    }


    public static int randomPartition(int [] dataArr, int fromIndex, int toIndex) {
        int randomOffset = random.nextInt(toIndex-fromIndex);
        return partition(dataArr, fromIndex, toIndex, fromIndex+randomOffset);
    }


    /**
     * Find array index containing k-th smallest element of the subarray
     *   dataArr[fromIndex..toIndex-1] using a random pivot; note that elements of the subarray
     *   migh have changed positions when the function returns
     * @param dataArr
     * @param fromIndex
     * @param toIndex
     * @param k
     * @return
     */
    private static int kthSmallestRandomPivotIdx(int[] dataArr, int fromIndex, int toIndex, int k) {
        while (fromIndex < toIndex) {
            int pivotIdx = randomPartition(dataArr, fromIndex, toIndex);
            if (k == pivotIdx) {
                return k;
            } else if (k < pivotIdx) {
                toIndex = pivotIdx;
            } else {
                fromIndex = pivotIdx + 1;
            }
        }
        return fromIndex;
    }

    /**
     * Find k-th smallest element using a random pivot
     * @param dataArr
     * @param k
     * @return
     */
    public static int kthSmallestRandomPivot(int [] dataArr, int k) {
        return dataArr[kthSmallestRandomPivotIdx(dataArr, 0, dataArr.length, k)];
    }

    public static int [] readIncomeData(String fileName) throws Exception {
        BufferedReader rdr = null;
        ArrayList<Integer> lst = new ArrayList<>();
        try {
            rdr = new BufferedReader(new FileReader(fileName));
            String line = null;
            while (((line=rdr.readLine())) != null) {
                lst.add(Integer.parseInt(line.trim()));
            }
        } finally {
            rdr.close();
        }
        int [] arr = new int[lst.size()];
        for (int i=0; i < arr.length; i++) {
            arr[i] = lst.get(i);
        }
        return arr;
    }

    /**
     * Find k closest values on either side of the median
     * @param dataArr
     * @param k
     * @return a total of 2k+1 values
     */
    public static int[] findClosestToMedian(int[] dataArr, int k) {
        if (2 * k + 1 >= dataArr.length) {
            throw new IllegalArgumentException("k is too large");
        }
        int[] closestValues = new int[2 * k + 1];
        int medianIdx = (int) Math.ceil(dataArr.length / 2);

        int partitionIdx = kthSmallestRandomPivotIdx(dataArr, 0, dataArr.length, medianIdx - k);
        partition(dataArr, 0, dataArr.length, partitionIdx);

        partitionIdx = kthSmallestRandomPivotIdx(dataArr, partitionIdx, dataArr.length, k * 2);
        partition(dataArr, partitionIdx, dataArr.length, partitionIdx);

        System.arraycopy(dataArr, partitionIdx - k, closestValues, 0, 2 * k + 1);

        return closestValues;
    }

    public static void main(String [] args) throws Exception {
        int [] arr = new int [] {10,5,4,15,3,7,8,20,3,11,21,16};
        int medianVal = kthSmallestRandomPivot(arr,(int) Math.ceil(arr.length/2)-1);
        System.out.println("median value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + medianVal); // should be 8
        int eighthSmallest = kthSmallestRandomPivot(arr,8-1);
        System.out.println("eighthSmallest value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + eighthSmallest); // should be 11
        int fourthSmallest = kthSmallestRandomPivot(arr,4-1);
        System.out.println("fourthSmallest value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + fourthSmallest); // should be 5
        int [] incomes = readIncomeData("Incomes.txt");
        long startMillis = System.currentTimeMillis();
        int medianIncome = kthSmallestRandomPivot(incomes, (int) Math.ceil(incomes.length/2));
        long endMillis = System.currentTimeMillis();
        System.out.println("Median income (using selection) = " + medianIncome);
        System.out.println("Time(ms) for finding median using selection alg.:" + (endMillis-startMillis));
        startMillis = System.currentTimeMillis();
        int topOnePercentIncome = kthSmallestRandomPivot(incomes, (int) (0.99*incomes.length));
        endMillis = System.currentTimeMillis();
        System.out.println("Top one percent income (using selection) = " + topOnePercentIncome);
        System.out.println("Time(ms) for finding top one percent income using selection alg.:" + (endMillis-startMillis));
        startMillis = System.currentTimeMillis();
        int bottomQuarterIncome = kthSmallestRandomPivot(incomes, (int) (0.25*incomes.length));
        endMillis = System.currentTimeMillis();
        System.out.println("Bottom 25 percent income (using selection) = " + bottomQuarterIncome);
        System.out.println("Time(ms) for finding bottom 25 percent income using selection alg.:" + (endMillis-startMillis));
        int [] incomes1 = Arrays.copyOf(incomes, incomes.length);
        startMillis = System.currentTimeMillis();
        Arrays.sort(incomes1);
        medianIncome = incomes1[(int) Math.ceil(incomes1.length/2)-1];
        endMillis = System.currentTimeMillis();
        System.out.println("Median income (using sorting) = " + medianIncome);
        System.out.println("Top one percent income (using sorting) = " +
                incomes1[((int) (0.99*incomes1.length))-1]);
        System.out.println("Bottom 25 percent income (using sorting) = " +
                incomes1[((int) (0.25*incomes1.length))-1]);
        System.out.println("Time(ms) for finding median etc. using sorting :" + (endMillis-startMillis));
        int [] closest = findClosestToMedian(arr, 2);
        System.out.println("4 closest values to median = "+ Arrays.toString(closest));
        closest = findClosestToMedian(arr, 3);
        System.out.println("6 closest values to median = "+ Arrays.toString(closest));
    }
}

