public class Polynomial {
    double [] coefficients;
    public Polynomial() {
        coefficients = new double[] {0.0};
    }
    public Polynomial(double [] coefficients) {
        this.coefficients = coefficients;
    }
    public Polynomial add(Polynomial p) {
        Polynomial p2;
    
        if (p.coefficients.length > this.coefficients.length) {
            p2 = new Polynomial(p.coefficients);
            for (int i  = 0; i < this.coefficients.length; i++) {
                p2.coefficients[i] += this.coefficients[i];
            }
        }
        else {
            p2 = new Polynomial(this.coefficients);
            for (int i  = 0; i < p.coefficients.length; i++) {
                p2.coefficients[i] += p.coefficients[i];
            }
	    }
	  
	    return p2;
    }

    public double evaluate(double x) {
        double sum = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            sum += this.coefficients[i] * Math.pow(x, i);
        }
        return sum; 
    }

    public boolean hasRoot(double x) {
	    return evaluate(x) == 0.0; 
    }
}