package global;

/**
 * Class to return a quadruple of values
 * @author USER
 *
 * @param <A>: class of value a
 * @param <B>: class of value b
 * @param <C>: class of value c
 * @param <D>: class of value d
 */
public class Quad<A,B,C,D> {
	public A a;
	public B b;
	public C c;
	public D d;
	
	public Quad(A newA, B newB, C newC, D newD) {
		a = newA;
		b = newB;
		c = newC;
		d = newD;
	}
	
}
