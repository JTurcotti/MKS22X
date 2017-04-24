import java.util.*;

public class StackCalc {
    static class EvalStack extends Stack<Double> {
	void push(String sym) {
	    try {
		push(Double.parseDouble(sym));
	    } catch (NumberFormatException e) {
		double two = pop(); double one = pop();
		switch (sym) {
		case "+":
		    push(one + two); break;
		case "-":
		    push(one - two); break;
		case "*":
		    push(one * two); break;
		case "/":
		    push(one / two); break;
		}}}}
    
    public static double eval(String s) {
	Scanner in = new Scanner(s);
	EvalStack es = new EvalStack();
	while (in.hasNext())
	    es.push(in.next());
	return es.pop();
    }
}
