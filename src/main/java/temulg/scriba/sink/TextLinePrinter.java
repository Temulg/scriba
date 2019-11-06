/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.sink;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TextLinePrinter implements MessageSink {
	public TextLinePrinter(PrintStream out_) {
		out = out_;
	}

	@Override
	public void close() {}

	@Override
	public void deliver(ByteBuffer b) {
		var cb = StandardCharsets.UTF_8.decode(b.rewind());
		out.append(cb);
		out.println();
	}

	private final PrintStream out;
}
