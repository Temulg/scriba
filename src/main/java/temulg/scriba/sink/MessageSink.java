/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.sink;

import java.nio.ByteBuffer;

import temulg.scriba.message.Message;

public interface MessageSink extends AutoCloseable {
	default void deliver(
		Message msg, Message.Fragment f0, Message.Fragment f1
	) {
		deliver(msg.make(f0, f1));
	}

	default void deliver(
		Message msg,
		Message.Fragment f0,
		Message.Fragment f1,
		Message.Fragment f2
	) {
		deliver(msg.make(f0, f1, f2));
	}

	void deliver(ByteBuffer b);
}
