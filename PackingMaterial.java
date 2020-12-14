
public class PackingMaterial {
	
	//PackingMaterial class.
	private double d_p;
	private double coefficient_d_p;
	private double f_p;

	public PackingMaterial clone() {
		return new PackingMaterial(this);
	}

	public PackingMaterial() {
		this.d_p = 0.0;
		this.coefficient_d_p = 0.0;
		this.f_p = 0.0;

	}

	public PackingMaterial(double d_p, double coefficient_d_p, double f_p) {
		if (d_p < 0.0 || coefficient_d_p < 0.0 || f_p < 0.0) {
			System.exit(0);
			// call exit function;
		} else {
			this.d_p = d_p;
			this.coefficient_d_p = coefficient_d_p;
			this.f_p = f_p;

		}
	}

	public PackingMaterial(PackingMaterial source) {
		this.d_p = source.d_p;
		this.coefficient_d_p = source.coefficient_d_p;
		this.f_p = source.f_p;

	}

	public double getD_p() {
		return d_p;
	}

	public boolean setD_p(double d_p) {
		if (d_p <= 0) {
			return false;
		}
		this.d_p = d_p;
		return true;
	}

	public double getcoefficient_D_p() {
		return coefficient_d_p;
	}

	public boolean setcoefficientD_p(double coefficient_d_p) {
		if (coefficient_d_p <= 0) {
			return false;
		}
		this.coefficient_d_p = coefficient_d_p;
		return true;
	}

	public double getF_p() {
		return f_p;
	}

	public boolean setF_p(double f_p) {
		if (f_p <= 0) {
			return false;
		}
		this.f_p = f_p;
		return true;
	}

	// class for cross-sectional area;
	public double calculateS() {
		double d_c = this.coefficient_d_p * this.d_p;
		return Math.pow(d_c, 2) * Math.PI/4;
	}

}
