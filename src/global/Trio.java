package global;

/**
 * Class to return a triple of values
 * @author USER
 *
 * @param <A>: class of value a
 * @param <B>: class of value b
 * @param <C>: class of value c
 */
public class Trio<A,B,C> {
	public A a;
	public B b;
	public C c;
	
	public Trio (A newA, B newB, C newC) {
		a = newA;
		b = newB;
		c = newC;
	}
}
