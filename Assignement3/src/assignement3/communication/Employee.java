package assignement3.communication;

/**
 * 
 * The class {@code Employee} is used to represents employee entity.
 * This class inherit from People class.  
 *
 */

public class Employee extends People {

	/**
	 * Class fields.
	 * 
	 * @param id It is the employee id.
	 * @param name It is the employee's name.
	 * @param surname It is the employee's surname.
	 * @param email It is the employee's email.
	 * @param password It is the employee's password.
	 * @param address Employee doesn't have address field. It will be null.
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Class constructor used by unregistered user to make request for registration to the server.
	 * 
	 * @param id It is the employee id.
	 * @param name It is the employee name.
	 * @param surname It is the employee surname.
	 * @param email It is the employee email.
	 * @param password It is the employee password.
	 * @param address It is the employee address.
	 */
	
	public Employee(final int id, final String name, final String surname, final String email, final String password, final String address) {
		super(name, surname, email, password, address);
	}
	
									// VIEW EMPLOYEES -> REQUEST //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/** {@inheritDoc} **/
	
	public Employee() {}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
