package com.callcenter.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.callcenter.model.Call;

public class DispatcherTest {

	@Test
	public void dispatchCallTest() {
		int callNo = 100;
		Dispatcher.get().buildOperators(4);
		Dispatcher.get().buildSupervisor(3);
		Dispatcher.get().buildDirectors(2);
		List<Call> calls = new ArrayList<>();
		for(int i = 0; i < callNo; i++) {
			calls.add(new Call());
			System.out.println("Tiempo: " + calls.get(i).getLenght());
		}
		
		calls.parallelStream().forEach(call -> {
			Dispatcher.get().dispatchCall(call);
		});
		
		try {
			Thread.sleep(100000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dispatchCall10CallsTest() {
		int callNo = 10;
		Dispatcher.get().buildOperators(4);
		Dispatcher.get().buildSupervisor(3);
		Dispatcher.get().buildDirectors(2);
		List<Call> calls = new ArrayList<>();
		for(int i = 0; i < callNo; i++) {
			calls.add(new Call());
			System.out.println("Tiempo: " + calls.get(i).getLenght());
		}
		
		calls.parallelStream().forEach(call -> {
			Dispatcher.get().dispatchCall(call);
		});
		
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
