/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import java.util.concurrent.atomic.AtomicBoolean;

import temulg.scriba.message.Message;

public interface Gate {
	boolean enabled();

	default void visit(
		Message.FragmentBuilder msg, FieldConfig cfg
	) {}

	public static Gate DEFAULT = new Gate() {
		@Override
		public boolean enabled() {
			return true;
		}
	};

	public static Gate DISABLED = new Gate() {
		@Override
		public boolean enabled() {
			return false;
		}
	};

	public static class Caller implements Gate {
		public Caller(
			String clsName_,
			String loc_,
			boolean enabled
		) {
			clsName = clsName_;
			loc = loc_;
			state = new AtomicBoolean(enabled);
		}

		@Override
		public boolean enabled() {
			return state.get();
		}

		@Override
		public void visit(
			Message.FragmentBuilder msg, FieldConfig cfg
		) {
			msg.addLabel(cfg.callerClass());
			msg.addString(clsName);
			msg.addLabel(cfg.caller());
			msg.addString(loc);
		}

		public void setState(boolean enabled) {
			state.set(enabled);
		}

		private final String clsName;
		private final String loc;
		private final AtomicBoolean state;
	}
}
