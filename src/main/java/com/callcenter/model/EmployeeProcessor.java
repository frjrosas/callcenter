package com.callcenter.model;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * This class is responsible for the employee management.
 * 
 * @author Francisco Rosas <frjrosas@gmail.com>
 *
 * @param <T>
 */
public class EmployeeProcessor<T extends CallCenterWorker> {

	private int qty;
	
	private ConcurrentLinkedDeque<T> elements;

	/**
	 * 
	 * @param qty
	 */
	public EmployeeProcessor(int qty) {
		this.qty = qty;
		this.elements = new ConcurrentLinkedDeque<>();
	}
	
	/**
	 * 
	 * @param element
	 */
	public void add(T element) {
		elements.addLast(element);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canAssign() {
		return !elements.isEmpty();
	}
	
	/**
	 * 
	 * @return
	 */
	public Optional<T> getNextEmployee() {
		return Optional.ofNullable(elements.pollFirst());
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDimension() {
		return this.qty;
	}
}
