import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import com.singularsys.jep.standard.Complex;

public class ComplexFunction {
	private Jep jep = new Jep();
	
	public ComplexFunction(String expression) {
		jep.tryAddConstant("i", new Complex(0, 1));
		jep.addVariable("z");
		
		try {
			jep.parse(expression);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public ComplexNumber evaluate(double z) {
		jep.setVariable("z", z);
		
	    try {
	        Object result = jep.evaluate();
	        
	        if (result instanceof Complex) {
	            Complex complexResult = (Complex) result;
	            return new ComplexNumber(complexResult.re(), complexResult.im());
	        } else if (result instanceof Number) {
	            double doubleResult = ((Number) result).doubleValue();
	            return new ComplexNumber(doubleResult, 0); // Assuming ComplexNumber constructor handles real numbers
	        } else {
	            // Handle other result types as needed
	            throw new EvaluationException("Unexpected result type: " + result.getClass().getName());
	        }
	    } catch (EvaluationException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}
}
