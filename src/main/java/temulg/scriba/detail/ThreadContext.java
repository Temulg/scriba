/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.detail;

import temulg.scriba.Gate;

public class ThreadContext {
	public static ThreadContext get() {
		return INSTANCE.get();
	}

	public void setGate(Gate g) {
		gate = g;
	}

	public Gate resetGate() {
		var g = gate;
		gate = Gate.DEFAULT;
		return g;
	}

	private ThreadContext() {}

	private static final ThreadLocal<
		ThreadContext
	> INSTANCE = ThreadLocal.withInitial(ThreadContext::new);

	private Gate gate = Gate.DEFAULT;
}
