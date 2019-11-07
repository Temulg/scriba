/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.agent;

import java.lang.instrument.Instrumentation;

import temulg.scriba.instrument.GateInjector;

public class Agent {
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.format("=a0= premain %s\n", agentArgs);
		inst.addTransformer(new GateInjector(), true);
	}

	public static void agentmain(String agentArgs, Instrumentation inst) {
		System.out.format("=b0= premain %s\n", agentArgs);
		inst.addTransformer(new GateInjector(), true);
	}
}
