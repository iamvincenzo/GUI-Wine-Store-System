package assignement3.communication;

import java.io.Serializable;

/**
 * 
 * The class {@code RequestClose} represents a request made by client to close 
 * connection with the server.
 *
 */

public class RequestClose implements Serializable {
	
	/**
	 * Class fields.
	 * 
	 * v - It is the value of the request
	 */
	
	private static final long serialVersionUID = 1L;
	
	private boolean v;
	
	
	/**
	 * Class constructor used by people to make a request to the server for close socket.
	 * 
	 * @param v It is the value of the client request.
	 */

	public RequestClose(final boolean v) { this.setV(v); }
	
	
	/**
	 * This method is used to get the value of the client request.
	 * 
	 * @return It returns the value of the client request.
	 */

	public boolean getV() {	return v; }
	
	
	/**
	 * This method is used to set the value of the client request.
	 * 
	 * @param v It is the value of the client request.
	 */

	public void setV(boolean v) { this.v = v; }
}
