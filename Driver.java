class Polynomial {
    double [] coef;
    public Polynomial() {
        coef = new double[1];
    }
    public Polynomial(double [] in_coef) {
        coef = new double[in_coef.length];   
        for (int i = 0; i < in_coef.length; i++) {
            coef[i] = in_coef[i];
        }
    }
    public Polynomial add(Polynomial want_add) {
        int mx_len = Math.max(want_add.coef.length, this.coef.length);
        int mn_len = Math.min(want_add.coef.length, this.coef.length);
        double [] new_coef = new double[mx_len];
        for (int i = 0; i < mn_len; i++) {
            new_coef[i] = this.coef[i] + want_add.coef[i];
        }
        for (int i = mn_len; i < mx_len; i++) {
            if (i < want_add.coef.length) new_coef[i] = want_add.coef[i];
            else new_coef[i] = this.coef[i];
        }
        return new Polynomial(new_coef);
    }

    public double evaluate(double x_val) {
        double x = 1;
        double res = 0;
        for (int i = 0; i < this.coef.length; i++) {
            res += x * this.coef[i];
            x *= x_val;
        }
        return res;
    }

    public boolean hasRoot(double x_val) {
        return (evaluate(x_val) == 0);
    }
}

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        Polynomial p1 = new Polynomial(c1);
        double [] c2 = {0,-2,0,0,-9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
    }
}