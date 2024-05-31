import java.io.File;
import java.io.IOException;

public class Driver {
    public static void test_normalPolynomial() {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {1, 4};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9, 10};
        int [] e2 = {1, 3, 5};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(-1))
            System.out.println("-1 is a root of s");
        else
            System.out.println("-1 is not a root of s");
    }

    public static void testFunctions() throws IOException {
        Polynomial p = new Polynomial();
        // evaluate 0 polynomial
        System.out.println(p.evaluate(3));

        /* poly1.txt & poly2.txt interpretation test includes:
         * coefficient + exponent (normal): 10x2, 9x9, 5x4
         * coefficient + exponent 1 (empty exponent): 2x
         * coefficient 1 + exponent (empty coefficient): x3
         * coefficient 0 + exponent (trailing 0s): 0x10, 0x0, 0x2
         * coefficient + exponent 0 (constant): 10x0
         * normal constant (no coef & exponent): 4
         */
        File poly1 = new File("poly1.txt");
        File poly2 = new File("poly2.txt");
        Polynomial p1 = new Polynomial(poly1);
        Polynomial p2 = new Polynomial(poly2);

        // Test add function, if p1 + p2 the same as p2 + p1
        Polynomial s = p1.add(p2);
        Polynomial s2 = p2.add(p1);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println("s(4) = " + s.evaluate(4));
        System.out.println("s2(0.1) = " + s2.evaluate(0.1));
        System.out.println("s2(4) = " + s2.evaluate(4));

        // Test multiply function
        Polynomial mul = p1.multiply(p2);
        System.out.println("mul(0.1) = " + mul.evaluate(0.1));
        System.out.println("mul(3) = " + mul.evaluate(3));
        if(mul.hasRoot(1))
            System.out.println("1 is a root of mul");
        else
            System.out.println("1 is not a root of mul");

        // test saveToFile() function
        mul.saveToFile("result.txt");

        // After running saveToFile(), load it back again see if the constructor's work right
        File poly3 = new File("result.txt");
        Polynomial p3 = new Polynomial(poly3);
        System.out.println("p3(0.1) = " + p3.evaluate(0.1));
        System.out.println("p3(3) = " + p3.evaluate(3));
        if(p3.hasRoot(1))
            System.out.println("1 is a root of mul");
        else
            System.out.println("1 is not a root of mul");
    }

    public static void test_edgePolynomial() throws IOException {
        // double [] coef = { 0, 1, 0, -4, 0, 5 };
        // int [] expo = { 1, 3, 5, 4, 6, 9 };
        // Polynomial p1 = new Polynomial(coef, expo);
        // p1.saveToFile("result.txt");

        double [] coef1 = { 0, 0, 0, 0, 0, 0 };
        int [] expo1 = { 1, 3, 5, 4, 6, 9 };
        Polynomial p2 = new Polynomial(coef1, expo1);
        p2.saveToFile("result.txt");


    }

    public static void test_sum() {
        double [] c1 = {0.1};
        double [] c2 = {0.2};
        double [] c3 = {-0.3};
        int [] e1 = {0};

        Polynomial p1 = new Polynomial(c1, e1);
        Polynomial p2 = new Polynomial(c2, e1);
        Polynomial p3 = new Polynomial(c3, e1);

        Polynomial p4 = p1.add(p2);

        Polynomial p5 = p4.add(p3);

        if (p5.hasRoot(69)) {
            System.out.println("balls");
        } else {
            System.out.println("sac");
        }

        System.out.println(p5.evaluate(69));
    }

    public static void main(String [] args) throws IOException {
        test_normalPolynomial();
        testFunctions();
        // test_edgePolynomial();
        // test_sum();
        // File f = new File("poly3.txt");
        // Polynomial p = new Polynomial(f);
        // p.saveToFile("result.txt");
    }
}