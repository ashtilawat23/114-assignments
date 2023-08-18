package edu.njit.cs114;


/**
 * Author: Ravi Varadarajan
 * Date created: 1/22/2023
 */
public interface Polynomial {

    /**
     * Returns coefficient of the term with 'power'
     *
     * @param power
     * @return
     */
    public double coefficient(int power);

    /**
     * Returns the degree of polynomial (i.e. largest power of a non-zero term)
     *
     * @return
     */
    public int degree();

    /**
     * Add a term to the polynomial
     *
     * @param power
     * @param coefficient
     * @throws Exception when the power is invalid (e.g. negative)
     */
    public void addTerm(int power, double coefficient) throws Exception;

    /**
     * Remove and return the coefficient for the specified power,
     * @param power
     * @return coefficient of removed term if it exists else 0
     */
    public double removeTerm(int power);

    /**
     * Returns evaluation of the polynomial at 'point'
     *
     * @param point
     * @return
     */
    public double evaluate(double point);

    /**
     * Returns a new polynomial that is the result of addition of polynomial p to this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial add(Polynomial p) throws Exception;

    /**
     * Returns a new polynomial that is the result of subtraction of polynomial p to this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial subtract(Polynomial p) throws Exception;

    /**
     * Returns a new polynomial that is the result of multiplication of polynomial p with this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial multiply(Polynomial p) throws Exception;

    /**
     * Returns a new polynomial that is the quotient of the polynomial when this polynomial is divided by
     * the polynomial p
     * @throws Exception when p is zero
     * @param p
     * @return
     */
    public Polynomial divide(Polynomial p) throws Exception;

    /**
     * Returns composition of polynomial p with this polynomial q (i.e. p(q(x)))
     * @param p
     * @return
     */
    public Polynomial compose(Polynomial p);


}

