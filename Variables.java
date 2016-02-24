
public class Variables {
	IndependentVariables i;
	DependentVariables d;
	ControlVariables c;
	
	public Variables(IndependentVariables i, DependentVariables d){
		this.i = i;
		this.d = d;
		
	}

	public IndependentVariables getI() {
		return i;
	}

	public void setI(IndependentVariables i) {
		this.i = i;
	}

	public DependentVariables getD() {
		return d;
	}

	public void setD(DependentVariables d) {
		this.d = d;
	}

	public ControlVariables getC() {
		return c;
	}

	public void setC(ControlVariables c) {
		this.c = c;
	}

}
