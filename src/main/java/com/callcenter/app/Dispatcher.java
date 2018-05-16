package com.callcenter.app;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.callcenter.model.Call;
import com.callcenter.model.CallCenterWorker;
import com.callcenter.model.Director;
import com.callcenter.model.EmployeeProcessor;
import com.callcenter.model.Operator;
import com.callcenter.model.Supervisor;

/**
 * This class represents the dispatcher class.
 * 
 * @author Francisco Rosas <frjrosas@gmail.com>
 *
 */
public class Dispatcher {

	private static Dispatcher instance;

	EmployeeProcessor<Director> directors;

	EmployeeProcessor<Supervisor> supervisors;

	EmployeeProcessor<Operator> operators;

	ConcurrentLinkedDeque<Call> waitingCall;

	/**
	 * 
	 */
	private Dispatcher() {
		waitingCall = new ConcurrentLinkedDeque<>();
	}

	/**
	 * 
	 * @return
	 */
	public static Dispatcher get() {
		if (instance == null)
			instance = new Dispatcher();
		return instance;
	}

	/**
	 * the operators builders.
	 * 
	 * @param qty
	 */
	public void buildOperators(int qty) {
		if (instance.operators == null)
			instance.operators = new EmployeeProcessor<>(qty);

		for (int i = 0; i < instance.operators.getDimension(); i++) {
			instance.operators.add(new Operator());
		}
	}

	/**
	 * the supervisor builders.
	 * 
	 * @param qty
	 */
	public void buildSupervisor(int qty) {
		if (instance.supervisors == null)
			instance.supervisors = new EmployeeProcessor<>(qty);

		for (int i = 0; i < instance.supervisors.getDimension(); i++) {
			instance.supervisors.add(new Supervisor());
		}
	}

	/**
	 * the directors builders.
	 * 
	 * @param qty
	 */
	public void buildDirectors(int qty) {
		if (instance.directors == null)
			instance.directors = new EmployeeProcessor<>(qty);

		for (int i = 0; i < instance.directors.getDimension(); i++) {
			instance.directors.add(new Director());
		}
	}

	/**
	 * This method is responsible of handling the call reception process, if the
	 * call cannot be processed at the instant, the call is put on hold.
	 * 
	 * @param call
	 *            the call to be processed.
	 */
	public void dispatchCall(Call call) {
		synchronized (instance) {
			if (instance.operators.canAssign()) {
				processCall(instance.operators, call);
			} else if (instance.supervisors.canAssign()) {
				processCall(instance.supervisors, call);
			} else if (instance.directors.canAssign()) {
				processCall(instance.directors, call);
			} else {
				waitingCall.addLast(call);
			}
		}
	}

	/**
	 * This method is responsible of the call processing.
	 * 
	 * @param employees the employees available 
	 * @param call a call.
	 */
	private <T extends CallCenterWorker> void processCall(final EmployeeProcessor<T> employees, Call call) {
		if (!call.onWait())
			return;

		final Optional<T> employee = employees.getNextEmployee();
		if (employee.isPresent()) {
			employee.get().receiveCall(call);
			int length = call.getLenght();
			TimerTask task = new TimerTask() {
				public void run() {
					synchronized (Dispatcher.get()) {
						employee.get().processCall();
						employees.add(employee.get());
						if (!waitingCall.isEmpty()) {
							processCall(employees, waitingCall.pollFirst());
						}
					}
				}
			};

			Timer timer = new Timer();
			timer.schedule(task, (length * 1000L));
		}
	}
}