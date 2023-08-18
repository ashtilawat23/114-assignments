package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/24/2023
 */
public class GraphException extends Exception {

    public GraphException(String msg) {
        super(msg);
    }

    public GraphException(String msg, Throwable t) {
        super(msg, t);
    }
}
