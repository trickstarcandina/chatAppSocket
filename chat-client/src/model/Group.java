package model;

public class Group {

	private int id;
	private String name;
	private date createdTime;

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

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public date getCreatedTime() {
		return this.createdTime;
	}

	/**
	 * 
	 * @param createdTime
	 */
	public void setCreatedTime(date createdTime) {
		this.createdTime = createdTime;
	}

}