package model;

public class MessageIndividual {

	private int id;
	private User userSend;
	private User userRecevie;

	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public User getUserSend() {
		return this.userSend;
	}

	/**
	 * 
	 * @param userSend
	 */
	public void setUserSend(User userSend) {
		this.userSend = userSend;
	}

	public User getUserRecevie() {
		return this.userRecevie;
	}

	/**
	 * 
	 * @param userRecevie
	 */
	public void setUserRecevie(User userRecevie) {
		this.userRecevie = userRecevie;
	}

}