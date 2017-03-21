public class Recursion {
    static double sqrt(double n, double guess) {
	if (Math.abs(guess*guess-n)<.000001) return guess;
	return sqrt(n, (n/guess + guess)/2);
    }

    public static double sqrt(double n) {
	return sqrt(n, 1);
    }

    public static String name() {return "Turcotti,Joshua";}
    
    public static void main(String[] args) {
	for (int i=0; i<20; i++) {
	    double sq = sqrt(i);
	    System.out.println(sq*sq);
	}
    }
}
    
