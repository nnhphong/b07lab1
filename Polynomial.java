import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
class Polynomial {
    double [] coef;
    int [] expo;

    public Polynomial() {
        coef = new double[1];
		expo = new int[1];
    }
	
    public Polynomial(double [] in_coef, int [] in_expo) {
        coef = new double[in_coef.length];   
		expo = new int[in_expo.length];	
        for (int i = 0; i < in_coef.length; i++) {
            coef[i] = in_coef[i];
	        expo[i] = in_expo[i];
        }
    }

	public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String equation = scanner.nextLine();
        String [] terms = equation.split("[+-]");

        int start_index = 0;
        if (terms[0] == "") start_index = 1;

        coef = new double[terms.length - start_index];   
		expo = new int[terms.length - start_index];	

        for (int i = start_index; i < terms.length; i++) {
            int j = i - start_index;
            String [] e = terms[i].split("x");

            // 1x^1 case
            if (e.length == 0) {
                coef[j] = expo[j] = 1;
                continue;
            }   
            if (e[0].equals("")) coef[j] = 1;
            else coef[j] = Double.parseDouble(e[0]);

            if (e.length == 1) {
                // constant or ^1 exponent
                System.out.println(terms[i]);
                if (terms[i].charAt(terms[i].length() - 1) == 'x') {
                    expo[j] = 1;
                }
                else expo[j] = 0;
            }
            else expo[j] = Integer.parseInt(e[1]);
        }

        // change sign for the coeficient

        if (equation.charAt(0) == '-') {
            coef[0] *= -1;
        }
        for (int i = 1, j = 1; i < equation.length(); i++) {
            if (equation.charAt(i) == '-') {
                coef[j] *= -1;
                j++;
            }
            else if (equation.charAt(i) == '+') {
                j++;
            }
        }
	}

    public int countDistinctExpo(Polynomial want_add) {
        boolean [] cnt_expo = new boolean[100];
        int len = 0;
        for (int k = 0; k < want_add.expo.length; k++) {
            cnt_expo[want_add.expo[k]] = true;
        }
        for (int k = 0; k < this.expo.length; k++) {
            cnt_expo[this.expo[k]] = true;
        }
        for (int i = 0; i < 100; i++) {
            if (cnt_expo[i]) len++;
        }
        return len;
    }

    public Polynomial add(Polynomial want_add) {
        int new_len = countDistinctExpo(want_add);
        double [] new_coef = new double[new_len];
        int [] new_expo = new int[new_len];
        int res_len = 0;
        for (int i = 0; i < 100; i++) {
            double x = -1e9, y = -1e9;
            for (int j = 0; j < want_add.expo.length; j++) {
                if (want_add.expo[j] == i) {
                    x = want_add.coef[j];
                    break;
                }
            }
            for (int j = 0; j < this.expo.length; j++) {
                if (this.expo[j] == i) {
                    y = this.coef[j];
                    break;
                }
            }
            if (x != -1e9 || y != -1e9) {
                if (x == -1e9) x = 0;
                if (y == -1e9) y = 0;
                new_expo[res_len] = i;
                new_coef[res_len] = x + y;
                res_len++;
            }
        }
        // System.out.println("Res_len: " + res_len);
        return new Polynomial(new_coef, new_expo);
    }

    public Polynomial multiply(Polynomial want_mul) {
		int new_len = want_mul.coef.length * this.coef.length;
		double [] tmp_coef = new double[new_len];
		int [] tmp_expo = new int[new_len];

		int idx = 0;
		for (int i = 0; i < this.coef.length; i++) {
			for (int j = 0; j < want_mul.coef.length; j++) {
				tmp_expo[idx] = this.expo[i] + want_mul.expo[j];
				tmp_coef[idx] = this.coef[i] * want_mul.coef[j];
				idx++;
			}	
	    }

        new_len = countDistinctExpo(new Polynomial(tmp_coef, tmp_expo));
        double [] new_coef = new double[new_len];
        int [] new_expo = new int[new_len];
        idx = 0;
        for (int i = 0; i < 100; i++) {
            int cur_coef = 0;
            for (int j = 0; j < tmp_expo.length; j++) {
                if (tmp_expo[j] == i) cur_coef += tmp_coef[j];
            }
            // ASSUMING: we eliminate term with coeficient 0
            if (cur_coef != 0) {
                new_expo[idx] = i;
                new_coef[idx] = cur_coef;
                idx++;
            }
        }
		return new Polynomial(new_coef, new_expo);
	}

    public double evaluate(double x_val) {
        double res = 0;
        for (int i = 0; i < this.coef.length; i++) {
            res += Math.pow(x_val, this.expo[i]) * this.coef[i];
        }
        return res;
    }

    
    public boolean hasRoot(double x_val) {
        double eps = 1e-9;
        return (Math.abs(evaluate(x_val)) < eps);
    }

    public String compressPolynomial(Polynomial x) {
        String res = "";
        boolean leadingTerm = true;
        for (int i = 0; i < x.coef.length; i++) {
            if (coef[i] == 0) continue;
            if (!leadingTerm && coef[i] > 0) res += "+";
            
            if (expo[i] == 0) res += Double.toString(coef[i]);
            else res += Double.toString(coef[i]) + "x" + Integer.toString(expo[i]);
            leadingTerm = false;
        }
        if (res == "") res = "0";
        return res;
    }

	public void saveToFile(String file_name) throws IOException {
        FileWriter out = new FileWriter(file_name, false);
        out.write(compressPolynomial(this));
        out.close();
	}

    public void printPolynomial() {
        System.out.println("Expo: ");
        for (int i = 0; i < this.expo.length; i++) {
            System.out.println(this.expo[i]);
        }

        System.out.println("Coef: ");
        for (int i = 0; i < this.coef.length; i++) {
            System.out.println(this.coef[i]);
        }
    }
}
