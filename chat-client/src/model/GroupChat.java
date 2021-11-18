package model;

public class GroupChat {

	private int id;
	private int totalMember;
	private String role;
	private User user;
	private Group group;

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

	public int getTotalMember() {
		return this.totalMember;
	}

	/**
	 * 
	 * @param totalMember
	 */
	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

	public String getRole() {
		return this.role;
	}

	/**
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return this.group;
	}

	/**
	 * 
	 * @param group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

}