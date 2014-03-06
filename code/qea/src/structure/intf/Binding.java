package structure.intf;

import exceptions.BindingException;

public abstract class Binding {

	/**
	 * Returns the value of the variable with the specified name
	 * 
	 * @param variableName
	 *            Variable name
	 * @return Value of the variable
	 */
	public abstract Object getValue(int variableName);

	/**
	 * Sets the value of the variable with the specified name
	 * 
	 * @param variableName
	 *            Variable name
	 * @param value
	 *            Value of the variable
	 */
	public abstract void setValue(int variableName, Object value);

	public abstract Binding copy();

	/**
	 * Removes the values for the current binding, leaving it as an empty
	 * binding
	 */
	public abstract void setEmpty();

	/**
	 * Helper method for getting the value of a variable and throwing an
	 * exception if the value is not there
	 * 
	 * @param var
	 *            Name of the variable
	 * @return Value of var in binding
	 */
	public Object getForced(int var) {
		Object val = getValue(var);
		if (val == null) {
			throw new BindingException("Variable x_" + var + " expected in "
					+ this);
		}
		return val;
	}

	/**
	 * Helper method for getting the value of a variable as an integer and
	 * throwing an exception if the value is not there or is not an integer
	 * 
	 * @param var
	 *            Name of the variable
	 * @return the value of var in binding
	 */
	public Integer getForcedAsInteger(int var) {
		Object val = getValue(var);
		if (val == null) {
			throw new BindingException("Variable x_" + var + " expected in "
					+ this);
		}
		if (!(val instanceof Integer)) {
			throw new BindingException("Variable x_" + var
					+ " expected as integer in " + this);
		}

		return (Integer) val;
	}

}
