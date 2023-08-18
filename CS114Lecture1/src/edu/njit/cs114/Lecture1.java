package edu.njit.cs114;

public class Lecture1 {
    /**
     * Recursive function
     * Called by binarySearch to get solution recursively.
     * @param a
     * @param leftPos
     * @param rightPos
     * @param target
     * @return
     */
    private static int binarySearch(int [] a, int leftPos, int rightPos, int target) {
        if (leftPos > rightPos) {
            return -1;
        }
        int middlePos = (leftPos + rightPos)/2;
        if (target == a[middlePos]) {
            return middlePos;
        }
        if (target < a[middlePos]) {
            binarySearch(a, leftPos, middlePos-1, target);
        }
        if (target > a[middlePos]) {
            binarySearch(a, leftPos+1, rightPos, target);
        }
        return 0;
    }
    /**
     * Search for position in [a] which contains target, -1 if it does not exist.
     * @param a
     * @param target
     * @return
     */
    public static int binarySearch(int[] a, int target) {
        return binarySearch(a, 0, a.length - 1, target);
    }

    /**
     * Recursive function
     * @param n
     * @return
     */
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n*factorial(n-1);
    }

    /**
     * Recurvie function
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n == 0) return 0;
        else if (n == 1) return 1;
        else if (n > 1) return fib(n-1)+fib(n-2);
        return -1;
    }
}