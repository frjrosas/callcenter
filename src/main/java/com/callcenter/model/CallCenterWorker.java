package com.callcenter.model;

import java.util.Date;

/**
 * This class represents a call center worker.
 * 
 * @author Francisco Rosas <frjrosas@gmail.com>
 *
 */
public class CallCenterWorker implements Employee {

	private Call call;

	private String name;
	
	public CallCenterWorker(String name) {
		this.call = null;
		this.name = name;
	}

	/**
	 * This method allows a call center employee to receive a call.
	 * 
	 * @param call the call to be attended
	 */
	@Override
	public void receiveCall(Call call) {
		this.call = call;
		call.attend();
	}
	
	/**
	 * This method allows to know if this employee is busy attending calls.
	 * 
	 * @return <b>True</b> if this employee is busy attending calls, <b>False</b>
	 *         otherwise.
	 */
	public boolean isBusy() {
		return this.call != null;
	}
	
	/**
	 * This method allows to process a call.
	 */
	public void processCall() {
		if (call == null)
			return;

		String msg = new StringBuffer("Call processed on: ").append(new Date()).append(" by: ")
				.append(CallCenterWorker.this.name).append(" call length: ")
				.append(CallCenterWorker.this.call.getLenght()).toString();
		System.out.println(msg);
		CallCenterWorker.this.call = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCallLenght() {
		return this.call.getLenght();
	}
}
