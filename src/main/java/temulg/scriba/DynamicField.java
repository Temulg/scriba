/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import java.time.Instant;

import temulg.scriba.message.Message;

public interface DynamicField {
	void visit(Message.FragmentBuilder mbld);

	public static class Timestamp implements DynamicField {
		public Timestamp(String label_) {
			label = label_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			var now = Instant.now();

			mbld.addLabel(label);
			mbld.addInt64(now.getEpochSecond());
			mbld.addLabel(label + "_fractional");
			mbld.addInt32(now.getNano());
		}

		final String label;
	}
}
