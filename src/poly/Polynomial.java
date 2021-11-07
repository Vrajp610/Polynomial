package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node polynomial1Node = poly1;
		Node polynomail2Node = poly2;
		Node polynomial = null;

		while (polynomial1Node != null || polynomail2Node != null) {     
			if (polynomial1Node == null) {
				polynomial = addLastNum(polynomial, polynomail2Node.term.coeff, polynomail2Node.term.degree);
				polynomail2Node = polynomail2Node.next;
				continue;
			}
			
			if (polynomail2Node == null) {
				polynomial = addLastNum(polynomial, polynomial1Node.term.coeff, polynomial1Node.term.degree);
				polynomial1Node = polynomial1Node.next;
				continue;
			}

			Term oneT = polynomial1Node.term;
			Term twoT = polynomail2Node.term;
			
			if (oneT.degree != twoT.degree) {
				if (oneT.degree < twoT.degree) {
					polynomial = addLastNum(polynomial, oneT.coeff, oneT.degree);
					polynomial1Node = polynomial1Node.next;
					continue;
				} else {
					polynomial = addLastNum(polynomial, twoT.coeff, twoT.degree);
					polynomail2Node = polynomail2Node.next;
					continue;
				}
			}

			float coeff = oneT.coeff + twoT.coeff;

			if (coeff != 0) {
				polynomial = addLastNum(polynomial, coeff, oneT.degree);
			}

			polynomial1Node = polynomial1Node.next;
			polynomail2Node = polynomail2Node.next;

		}
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return polynomial;
	}



	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node polynomialP1 = poly1;
		Node polynomialP2 = poly2;
		Node polynomialFront = null;
	
		while (polynomialP1 != null) {	
				if (polynomialFront != null) {
					polynomialFront = add(polynomialFront, mPolynomialNode(polynomialP1,poly2));		
				} else {
					polynomialFront = mPolynomialNode(polynomialP1 , polynomialP2);
				}
				
				polynomialP1 = polynomialP1.next;
		}

		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return polynomialFront;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float total = 0;

		Node list = poly;

		while (list != null){
			float coeff = list.term.coeff;

			int degree = list.term.degree;

			total += coeff * Math.pow(x, degree);

			list = list.next;
		}

		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return total;
	}

	private static Node mPolynomialNode(Node polynomialOne, Node polynomialTwo){
		Node firstNumber = null;
		Node lastNumber = null;
		Node listTwo = polynomialTwo;

		float coeffOne = polynomialOne.term.coeff;
		int higherDegree = polynomialOne.term.degree;

		float coeffTwo;
		int lowerDegree;
		while (listTwo != null){
			coeffTwo = coeffOne * listTwo.term.coeff;
			lowerDegree = higherDegree + listTwo.term.degree;

			Node newNode = new Node(coeffTwo, lowerDegree, null);

			if (lastNumber != null){
				lastNumber.next = newNode;
			} else {
				firstNumber = newNode;
			}

			lastNumber = newNode;
			listTwo = listTwo.next;

		}

		return firstNumber;
	}

	private static Node getLastNum(Node polynomial){
		Node list = polynomial;
		Node lastNumber = list;

		while (list != null){
			lastNumber = list;
			list = list.next;
		}

		return lastNumber;
		
	}


	private static Node addLastNum(Node polynomial, float coeff, int degree){
		Node newT = new Node(coeff, degree, null);
		
		if (polynomial == null){
			return newT;
		}
		
		Node lastNumber = getLastNum(polynomial);

		lastNumber.next = newT;
		
		return polynomial;
	}



	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
