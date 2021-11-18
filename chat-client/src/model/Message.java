package model;

public class Message {

	private int id;
	private String content;
	private date createdTime;
	private MessageIndividual messageIndividual;
	private Groupchat groupChat;

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

	public String getContent() {
		return this.content;
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
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

	public MessageIndividual getMessageIndividual() {
		return this.messageIndividual;
	}

	/**
	 * 
	 * @param messageIndividual
	 */
	public void setMessageIndividual(MessageIndividual messageIndividual) {
		this.messageIndividual = messageIndividual;
	}

	public Groupchat getGroupChat() {
		return this.groupChat;
	}

	/**
	 * 
	 * @param groupChat
	 */
	public void setGroupChat(Groupchat groupChat) {
		this.groupChat = groupChat;
	}

}