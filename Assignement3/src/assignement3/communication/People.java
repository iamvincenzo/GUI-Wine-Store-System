package assignement3.communication;

import java.io.Serializable;

/**
 * 
 * The class {@code People} is used to represents user/employee entity. 
 * It contains property and methods used to manage users and employees.
 *
 */

public class People implements Serializable {
	
	/**
	 * Class fields.
	 * 
	 * name - It is the name of the customer or employee or administrator.
	 * surname - It is the surname of the customer or employee or administrator.
	 * email - It is the email of the customer or employee or administrator.
	 * password - It is the password of the customer or employee or administrator.
	 * role - It is the role of the customer or employee or administrator.
	 * 
	 * request - It is the type of request.
	 */
	
	static final long serialVersionUID = 1L;   
	
	private int id;     
	private String name;
	private String surname;
	private String email;
	private String password;
	private String address;
	private String role;
	
	private String request;
	

											// LOGIN -> REQUEST //
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
   /**
	* Class constructor used by person to make a request for login to the server.
	* 
	* @param email - It is the people email.
	* @param password - It is the people password.
	* @param role - It is the people role.
	*/
	
	public People(final String email, final String password, final String role) {
		this.setEmail(email);
		this.setPassword(password);
		this.setRole(role);
	}
	
	
	/**
	 * This method is used to get the people role.
	 * 
	 * @return It returns the people role.
	 */

	public String getRole() { return role; }
	
	
	/**
	 * This method is used to set the people role.
	 * 
	 * @param role It is the role to set.
	 */

	public void setRole(String role) { this.role = role; }
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// VIEW PEOPLE -> RESPONSE //
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor. It is used by Users.
	 * 
	 * Class constructor used by customer to make a request for modify its data request stored into database to the server.
	 * 
	 * @param id It is the people id.
	 * @param name It is the name of the user
	 * @param surname It is the surname of the user
	 * @param email It is the email of the user
	 * @param password It is the password of the user
	 * @param address It is the user's address
	 */
	
	public People(final int id, final String name, final String surname, final String email, final String password, final String address) {
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setPassword(password);
		this.setAddress(address);
	}
	
	
	/**
	 * This method is used to get the people id.
	 * 
	 * @return It returns the people id.
	 */
	
	public int getId() { return id; }
	
	
	/**
	 * This method is used to set the people id.
	 * 
	 * @param id It is the id to set.
	 */

	public void setId(int id) {	this.id = id; }
	
	
	/**
	 * This method is used to return name.
	 * 
	 * @return Name of the instance of the class.
	 */
		
	public String getName() { return this.name; }
	
	
	/**
	 * This method is used to return surname.
	 * 
	 * @return Surname of the instance of the class.
	 */
	
	public String getSurname() { return this.surname; }
	
	
	/**
	 * This method is used to return email.
	 * 
	 * @return Email of the instance of the class.
	 */
	
	public String getEmail() { return this.email; }
	
	
	/**
	 * This method is used to return password.
	 * 
	 * @return Password of the instance of the class.
	 */
	
	public String getPassword() { return this.password;	}
	
	
	/**
	 * This method is used to return address.
	 * 
	 * @return Address of the instance of the class.
	 */
	
	public String getAddress() { return address; }
	
	
	/**
	 * This method is used to set name of the instance of the class.
	 * 
	 * @param name New name.
	 */
	
	public void setName(final String name) { this.name = name; }
	
	
	/**
	 * This method is used to set surname of the instance of the class.	
	 * 
	 * @param surname New surname.
	 */
	
	public void setSurname(final String surname) { this.surname = surname; }
	
	
	/**
	 * This method is used to set email of the instance of the class.
	 * 
	 * @param  email New email.
	 */
	
	public void setEmail(final String email) { this.email = email; }
	
	
	/**
	 * This method is used to set password of the instance of the class.
	 * 
	 * @param password New password.
	 */
	
	public void setPassword(final String password) { this.password = password; }
	
	
	/**
	 * This method is used to set address of the instance of the class.
	 * 
	 * @param address New address.
	 */
	
	public void setAddress(String address) { this.address = address; }
	
	
									// REGISTRATION | CREDENTIAL ASSIGNMENT-> REQUEST //
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by administrator to make a request for register a customer or employee to the server.
	 * 
	 * Class constructor used by administrator to make a request for delete a customer or employee to the server.
	 * 
	 * Class constructor used by administrator to make a request for modify customer or employee data to the server.
	 * 
	 * @param name It is the name of the employee.
	 * @param surname It is the surname of the employee.
	 * @param email It is the email of the employee.
	 * @param password It is the password of the employee.
	 * @param address It is the people address.
	 */
	
	public People(final String name, final String surname, final String email, final String password, final String address) {
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setPassword(password);
		this.setAddress(address);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// PEOPLE VIEW -> REQUEST // 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 *
	 * Class constructor used by administrator to make a view customers list request to the server.
	 * 
	 * Class constructor used by administrator to make a view employees list request to the server.
	 * 
	 * Class constructor used by customer to make a request to view its data stored into database to the server.
	 *
	 */
	
	public People() {}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

							// Methods used to get - set the type of request //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * This method is used to get the type of request.
	 * 
	 * @return It returns the type of request.
	 */
	
	public String getRequest() { return request; }
	
	
	/**
	 * This method is used to set the type of request.
	 * 
	 * @param request It is the request to set.
	 */

	public void setRequest(String request) { this.request = request; }
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
