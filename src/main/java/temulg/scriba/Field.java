/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import temulg.scriba.message.Message;

public interface Field {
	void visit(Message.FragmentBuilder mbld);

	public static class Int32 implements Field {
		public Int32(String label_, int value_) {
			label = label_;
			value = value_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			mbld.addLabel(label);
			mbld.addInt32(value);
		}

		final String label;
		final int value;
	}

	public static Field int32(String key, int value) {
		return new Int32(key, value);
	}

	public static class Int64 implements Field {
		public Int64(String label_, long value_) {
			label = label_;
			value = value_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			mbld.addLabel(label);
			mbld.addInt64(value);
		}

		final String label;
		final long value;
	}

	public static Field int64(String key, long value) {
		return new Int64(key, value);
	}

	public static class Float32 implements Field {
		public Float32(String label_, float value_) {
			label = label_;
			value = value_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			mbld.addLabel(label);
			mbld.addFloat32(value);
		}

		final String label;
		final float value;
	}

	public static Field float32(String key, float value) {
		return new Float32(key, value);
	}

	public static class Float64 implements Field {
		public Float64(String label_, double value_) {
			label = label_;
			value = value_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			mbld.addLabel(label);
			mbld.addFloat64(value);
		}

		final String label;
		final double value;
	}

	public static Field float64(String key, double value) {
		return new Float64(key, value);
	}

	public static class Str implements Field {
		public Str(String label_, String value_) {
			label = label_;
			value = value_;
		}

		@Override
		public void visit(Message.FragmentBuilder mbld) {
			mbld.addLabel(label);
			mbld.addString(value);
		}

		final String label;
		final String value;
	}

	public static Field str(String key, String value) {
		return new Str(key, value);
	}

	public static final Field EMPTY = new Field() {
		@Override
		public void visit(Message.FragmentBuilder mbld) {}
	};
}
