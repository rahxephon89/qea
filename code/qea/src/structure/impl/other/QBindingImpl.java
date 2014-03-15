package structure.impl.other;


/*
 * A binding structure to represent bindings of quantified variables
 * one of the key things is that we use -variableName instead of variableName
 * to index the array
 */
public class QBindingImpl extends BindingImpl {

	public QBindingImpl(int variablesCount) {
		super(variablesCount);
	}

	/**
	 * Returns the value of the quantified variable with the specified name
	 * 
	 * @param variableName
	 *            Variable name
	 * @return Value of the variable
	 */
	@Override
	public Object getValue(int variableName) {
		return super.getValue(-variableName);
	}	
	

}