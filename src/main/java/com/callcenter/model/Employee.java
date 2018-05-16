package com.callcenter.model;

public interface Employee {

	/**
	 * 
	 * @return
	 */
	void processCall();
	
	/**
	 * 
	 * @param call
	 */
	void receiveCall(Call call);

	/**
	 * This method allows to determine if a worker is busy or not.
	 * 
	 * @return <b>True</b> if the worker is busy, <b>false</b> otherwise.
	 */
	boolean isBusy();
}
