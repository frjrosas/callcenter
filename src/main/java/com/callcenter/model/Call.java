package com.callcenter.model;

/**
 * The Call representation class.
 * 
 * @author Francisco Rosas <frjrosas@gmail.com>
 *
 */
public class Call {
	
	private int lenght;
	
	private boolean onWait;
	
	/**
	 * 
	 */
	public Call() {
		this.lenght = (int) (Math.random() * ((10 - 5) + 1) + 5);
		this.onWait = true;
	}

	/**
	 * 
	 * @return
	 */
	public int getLenght() {
		return this.lenght;
	}

	/**
	 * 
	 * @return
	 */
	public boolean onWait() {
		return this.onWait;
	}
	
	/**
	 * 
	 */
	public void attend() {
		this.onWait = false;
	}
}
