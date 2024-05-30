class Polynomial {
    double [] coef;
    int [] expo;

    public Polynomial() {
        coef = new double[1];
		expo = new double[1];
    }
	
    public Polynomial(double [] in_coef, int [] in_expo) {
        coef = new double[in_coef.length];   
		expo = new int[in_expo.length];	
        for (int i = 0; i < in_coef.length; i++) {
            coef[i] = in_coef[i];
	    expo[i] = in_expo[i];
        }
    }

	public Polynomial(File content) {

	}

    public Polynomial add(Polynomial want_add) {
        int mx_len = Math.max(want_add.coef.length, this.coef.length);
        int mn_len = Math.min(want_add.coef.length, this.coef.length);
        double [] new_coef = new double[mx_len];
        for (int i = 0; i < mn_len; i++) {
            new_coef[i] = this.coef[i] + want_add.coef[i];
        }
        for (int i = mn_len; i < mx_len; i++) {
            if (i < want_add.coef.length) new_coef[i] = want_add.coef[
            else new_coef[i] = this.coef[i];
        }
        return new Polynomial(new_coef);
    }

    public Polynomial multiply(Polynomial want_mul) {
		int new_len = want_mul.coef.length * this.coef.length;
		double [] new_coef = new double[new_len];
		int [] new_expo = new int[new_len];

		int idx = 0;
		for (int i = 0; i < this.coef.length; i++) {
			for (int j = 0; j < want_mul.coef.length; i++) {
				new_expo[idx] = this.expo[i] + want_mul.expo[i];
				new_coef[idx] = this.coef[i] * want_mul.coef[i];
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
        return (evaluate(x_val) == 0);
    }

	public void saveToFile(String file_name) {
		
	}
}
