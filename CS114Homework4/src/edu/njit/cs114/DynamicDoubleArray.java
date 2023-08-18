package edu.njit.cs114;

/** Write Lab Part 2 Answers below  :
 *  (a) How does the time complexity of array append vary as n increases ?
 *  The time complexity increases linearly as n increases.
 *  (b) How does the number of element copies during array append vary as n increases ?
 *  The number of copies increases linearly as n increases as well.
 *  (c) Does it make much difference if inital size of the array is 5000 instead of 1 ?
 *  It makes a difference. Small inputs let the algorithm run faster and use less space.
 */


import java.util.Arrays;

/**
 * Author: Ashalesh Tilawat
 * Date created: 2/17/2023
 */
public class DynamicDoubleArray {

    private static final int DEFAULT_INITIAL_CAPACITY = 1;

    private Double [] arr;
    private int size;
    // keeps track of number of element copies made during array expansion or contraction
    private int nCopies;

    public DynamicDoubleArray(int initialCapacity) {
        arr = new Double[initialCapacity];
    }

    public DynamicDoubleArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Add element at specified index position shifting to right elements at positions higher than
     *    or equal to index
     * @param index
     * @param elem
     * @throws IndexOutOfBoundsException if index < 0 or index > size()
     */
    public void add(int index, double elem) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        /**
         * Complete code here for the lab
         */
        Double[] newArr;
        if (size < arr.length) {
            newArr = Arrays.copyOf(arr, arr.length);
            for (int i=0; i<size+1; i++) {
                if (i < index) {
                    newArr[i] = arr[i];
                } else if (i > index) {
                    newArr[i] = arr[i-1];
                }
            }
        } else {
            newArr = Arrays.copyOf(arr, 2 * arr.length);
            for (int i=0; i<size+1; i++) {
                if (i < index) {
                    newArr[i] = arr[i];
                    nCopies++;
                } else if (i > index) {
                    newArr[i] = arr[i-1];
                    nCopies++;
                }
            }
        }
        newArr[index] = elem;
        arr = newArr;
        size++;
    }

    /**
     * Append element to the end of the array
     * @param elem
     */
    public void add(double elem) {
        /**
         * Complete code here for the lab
         */
        if (size < arr.length) {
            arr[size] = elem;
            size++;
        } else {
            Double [] newArr = Arrays.copyOf(arr, 2*arr.length);
            for (int i=0; i<arr.length; i++) {
                nCopies++;
                newArr[i] = arr[i];
            }
            newArr[size] = elem;
            size++;
            arr = newArr;
        }
    }

    /**
     * Set the element at specified index position replacing any previous value
     * @param index
     * @param elem
     * @return previous value in the index position
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public double set(int index, double elem) throws IndexOutOfBoundsException {
        /**
         * Complete code here for the lab
         */
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        double oldVal = arr[index];
        arr[index] = elem;
        return oldVal;
    }

    /**
     * Get the element at the specified index position
     * @param index
     * @return
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public double get(int index) {
        /**
         * Complete code here for the lab
         */
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return arr[index];
    }

    /**
     * Remove and return the element at the specified index position. The elements with positions
     *  higher than index are shifted to left
     * @param index
     * @return
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public double remove(int index) throws IndexOutOfBoundsException {
        /**
         * Complete code here for homework
         */
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        double oldVal = arr[index];
        Double [] newArr;
        if (size <= .5*arr.length) {
            newArr = Arrays.copyOf(arr, (int)(.5*arr.length));
        } else {
            newArr = Arrays.copyOf(arr, arr.length);
        }
        for (int i=0; i<index; i++) {
            newArr[i] = arr[i];
            nCopies++;
        }
        for (int i=index; i<size; i++) {
            newArr[i] = arr[i+1];
            nCopies++;
        }
        arr = newArr;
        size--;
        return oldVal;
    }

    /**
     * Remove and return the element at the end of the array
     * @return
     * @throws Exception if size() == 0
     */
    public double remove() throws Exception {
        if (size == 0) {
            throw new Exception("Array is empty");
        }
        /**
         * Complete code here for homework
         */
        double oldVal = arr[size-1];
        arr[size-1] = null;
        size--;
        if (size <= .25*arr.length) {
            Double [] newArr = Arrays.copyOf(arr, (int) (arr.length*.5));
            for (int i=0; i<size; i++) {
                newArr[i] = arr[i];
                nCopies++;
            }
            arr = newArr;
        }
        return oldVal;
    }

    /**
     * Returns the number of elements in the array
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Returns the total number of copy operations done due to expansion of array
     * @return
     */
    public int nCopies() {
        return nCopies;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(" + arr.length + ")");
        builder.append("[");
        for (int i=0; i < size; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(arr[i] == null ? "" : arr[i]);
        }
        builder.append("]");
        return builder.toString();
    }

    public static void main(String [] args) throws Exception {
        DynamicDoubleArray arr = new DynamicDoubleArray();
        arr.add(8.5);
        arr.add(12.1);
        arr.add(-5.7);
        System.out.println("array of size " + arr.size()+ " : " + arr);
        assert arr.size() == 3;
        arr.add(1,4.9);
        arr.add(2,20.2);
        System.out.println("array of size " + arr.size()+ " : " + arr);
        assert arr.size() == 5;
        double oldVal = arr.set(2,25);
        System.out.println("old value at index 2 after replacing it with 25 = " + oldVal);
        assert oldVal == 20.2;
        System.out.println("Element at position 2 = "+arr.get(2));
        assert arr.get(2) == 25;
        System.out.println("array of size " + arr.size()+ " : " + arr);
        assert arr.size() == 5;
        /*     Uncomment the following for homework 4 */
//        double removedVal = arr.remove(0);
//        System.out.println("Removed element at position 0 = " + removedVal);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert removedVal == 8.5;
//        assert arr.size() == 4;
//        removedVal = arr.remove(2);
//        System.out.println("Removed element at position 2 = " + removedVal);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert removedVal == 12.1;
//        assert arr.size() == 3;
//        removedVal = arr.remove(2);
//        System.out.println("Removed element at position 2 = " + removedVal);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert removedVal == -5.7;
//        assert arr.size() == 2;
//        removedVal = arr.remove();
//        System.out.println("Removed element at end = " + removedVal);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert removedVal == 25;
//        assert arr.size() == 1;
//        removedVal = arr.remove();
//        System.out.println("Removed element at end = " + removedVal);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert removedVal == 4.9;
//        assert arr.size() == 0;
//        arr.add(67);
//        arr.add(-14);
//        arr.add(15);
//        System.out.println("array of size " + arr.size()+ " : " + arr);
//        assert arr.size == 3;
        int [] nItemsArr = new int [] {0, 100000, 200000, 400000, 800000, 1600000, 3200000};
        DynamicDoubleArray arr1 = new DynamicDoubleArray();
        long totalTime = 0;
        for (int k=1; k < nItemsArr.length; k++) {
            for (int i = 0; i < nItemsArr[k]-nItemsArr[k-1]; i++) {
                long startTime = System.currentTimeMillis();
                arr1.add(i + 1);
                long stopTime = System.currentTimeMillis();
                totalTime += (stopTime - startTime);
            }
            System.out.println("copy cost for inserting " + nItemsArr[k] + " items = " +
                    + arr1.nCopies());
            System.out.println("total time(ms) for inserting " + nItemsArr[k] + " items = " +
                    + totalTime);
        }
        DynamicDoubleArray arr2 = new DynamicDoubleArray(5000);
        totalTime = 0;
        for (int k = 1; k < nItemsArr.length; k++) {
            for (int i = 0; i < nItemsArr[k] - nItemsArr[k - 1]; i++) {
                long startTime = System.currentTimeMillis();
                arr2.add(i + 1);
                long stopTime = System.currentTimeMillis();
                totalTime += (stopTime - startTime);
            }
            System.out.println("copy cost for inserting " + nItemsArr[k] + " items = " +
                    +arr2.nCopies());
            System.out.println("total time(ms) for inserting " + nItemsArr[k] + " items = " +
                    +totalTime);
        }
        /* Uncomment the following for homework 4 */
//        totalTime = 0;
//        for (int k=1; k < nItemsArr.length; k++) {
//            for (int i = 0; i < nItemsArr[k]-nItemsArr[k-1]; i++) {
//                long startTime = System.currentTimeMillis();
//                arr1.remove();
//                long stopTime = System.currentTimeMillis();
//                totalTime += (stopTime - startTime);
//            }
//            System.out.println("total time(ms) for deleting " + nItemsArr[k] + " items = " +
//                    + totalTime);
//        }

    }

}

