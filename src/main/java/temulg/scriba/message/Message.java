/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.message;

import java.nio.ByteBuffer;

public interface Message {
	interface Fragment {
		Fragment append(Fragment other);
	}

	interface FragmentBuilder {
		void beginMap();
		void endMap();
		void beginList();
		void endList();

		void addLabel(String label);

		void addNull();
		void addBool(boolean v);
		void addInt32(int v);
		void addInt64(long v);
		void addFloat32(float v);
		void addFloat64(double v);

		<Cs extends CharSequence> void addString(Cs v);

		Fragment build();
	}

	ByteBuffer make(Fragment f0, Fragment f1);
	ByteBuffer make(Fragment f0, Fragment f1, Fragment f2);

	FragmentBuilder fragmentBuilder();
}
