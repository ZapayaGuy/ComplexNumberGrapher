import java.util.Objects;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import com.singularsys.jep.standard.Complex;

public class ComplexNumber {
    public double re;
    public double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber() {
        this(0, 0);
    }
    
    public ComplexNumber add(ComplexNumber z) {
        return new ComplexNumber(this.re + z.re, this.im + z.im);
    }

    public ComplexNumber add(double x) {
        return new ComplexNumber(this.re + x, this.im);
    }

    public ComplexNumber subtract(ComplexNumber z) {
        return new ComplexNumber(this.re - z.re, this.im - z.im);
    }

    public ComplexNumber subtract(double x) {
        return new ComplexNumber(this.re - x, this.im);
    }

    public ComplexNumber multiply(ComplexNumber z) {
        return new ComplexNumber(this.re * z.re - this.im * z.im, this.re * z.im + this.im * z.re);
    }

    public ComplexNumber multiply(double x) {
        return new ComplexNumber(this.re * x, this.im * x);
    }

    public ComplexNumber divide(ComplexNumber z) {
        double denominator = z.re * z.re + z.im * z.im;
        return new ComplexNumber((this.re * z.re + this.im * z.im) / denominator, (this.im * z.re - this.re * z.im) / denominator);
    }

    public ComplexNumber divide(double x) {
        return new ComplexNumber(this.re / x, this.im / x);
    }

    public double getAngle() {
        return Math.atan2(im, re);
    }

    public double getModulus() {
        return Math.sqrt(re * re + im * im);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComplexNumber that = (ComplexNumber) obj;
        return Double.compare(that.re, re) == 0 && Double.compare(that.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public String toString() {
        return re + " + " + im + "i | " + getModulus() + "e^" + getAngle() + "i";
    }

    public ComplexNumber getConjugate() {
        return new ComplexNumber(re, -im);
    }

    @Override
    public ComplexNumber clone() {
        return new ComplexNumber(this.re, this.im);
    }
    
    public static ComplexNumber evaluateExpression(String expression, double z) {
        Jep jep = new Jep();
        
        jep.addVariable("z");
        jep.setVariable("z", z);
		jep.tryAddConstant("i", new Complex(0, 1));


        try {
			jep.parse(expression);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        Complex ans = null;
		try {
			ans = (Complex) jep.evaluate();
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
        return new ComplexNumber(ans.re(), ans.im());
    }


}
