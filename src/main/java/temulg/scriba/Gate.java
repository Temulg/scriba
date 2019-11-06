/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import temulg.scriba.message.Message;

public interface Gate {
	boolean isOpen();

	default void visit(Message.FragmentBuilder msg) {
	}

	public static Gate DEFAULT = new Gate() {
		@Override
		public boolean isOpen() {
			return true;
		}
	};
}
