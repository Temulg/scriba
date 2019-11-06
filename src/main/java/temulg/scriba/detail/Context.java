/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.detail;

import java.util.ArrayList;

import temulg.scriba.FieldConfig;
import temulg.scriba.DynamicField;
import temulg.scriba.message.JsonLine;
import temulg.scriba.message.Message;
import temulg.scriba.sink.MessageSink;
import temulg.scriba.sink.TextLinePrinter;

public class Context {
	public Context() {
		config = new FieldConfig() {};
		message = new JsonLine();
		sink = new TextLinePrinter(System.out);

		common.add(new DynamicField.Timestamp(config.timestamp()));
	}

	public Message.FragmentBuilder fragmentBuilder() {
		return message.fragmentBuilder();
	}

	public FieldConfig fieldConfig() {
		return config;
	}

	public void deliver(Message.Fragment f0, Message.Fragment f1) {
		if (common.isEmpty())
			sink.deliver(message, f0, f1);
		else {
			var mbld = message.fragmentBuilder();
			for (var f: common)
				f.visit(mbld);

			sink.deliver(message, f0, f1, mbld.build());
		}
	}

	private final FieldConfig config;
	private final Message message;
	private final MessageSink sink;
	private final ArrayList<DynamicField> common = new ArrayList<>();
}
