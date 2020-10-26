package global;

/**
 * Class to return a pair of values
 * @author naffins
 *
 * @param <A>: class of value a
 * @param <B>: class of value b
 */
public class Pair<A,B> {
	public A a;
	public B b;
	
	public Pair(A newA, B newB) {
		a = newA;
		b = newB;
	}
}
