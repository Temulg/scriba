/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JsonLine implements Message {
	public static class Fragment implements Message.Fragment {
		@Override
		public Message.Fragment append(Message.Fragment other_) {
			var other = (Fragment)other_;
			var next = ByteBuffer.allocate(
				data.position()
				+ other.data.position()
				+ 1
			);

			next.put(data);
			next.put((byte)',');
			next.put(other.data);
			return new Fragment(next);
		}

		private Fragment(ByteBuffer data_) {
			data = data_;
		}

		private final ByteBuffer data;
	}

	public static class FragmentBuilder implements Message.FragmentBuilder {
		FragmentBuilder() {
			modeHeadSet(Mode.MAP_FIRST);
		}

		@Override
		public void beginMap() {
			switch (modeHead()) {
			case MAP_LABEL:
				sbld.append('{');
				modePush(Mode.MAP_FIRST);
				break;
			case LIST_FIRST:
				sbld.append('{');
				modePush(Mode.MAP_FIRST);
				break;
			case LIST_NEXT:
				sbld.append(",{");
				modePush(Mode.MAP_FIRST);
				break;
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void endMap() {
			switch (modeHead()) {
			case MAP_FIRST:
			case MAP_NEXT:
				sbld.append('}');
				modePop();
				break;
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void beginList() {
			switch (modeHead()) {
			case MAP_LABEL:
			case LIST_FIRST:
				sbld.append('[');
				modePush(Mode.LIST_FIRST);
				break;
			case LIST_NEXT:
				sbld.append(",[");
				modePush(Mode.LIST_FIRST);
				break;
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void endList() {
			switch (modeHead()) {
			case LIST_FIRST:
			case LIST_NEXT:
				sbld.append(']');
				modePop();
				break;
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void addLabel(String label) {
			switch (modeHead()) {
			case MAP_FIRST:
				sbld.append('"').append(label).append("\":");
				modeHeadSet(Mode.MAP_LABEL);
				break;
			case MAP_NEXT:
				sbld.append(
					",\""
				).append(
					label
				).append("\":");
				modeHeadSet(Mode.MAP_LABEL);
				break;
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void addNull() {
			addStringRaw("null");
		}

		@Override
		public void addBool(boolean v) {
			addStringRaw(v ? "true" : "false");
		}

		@Override
		public void addInt32(int v) {
			addStringRaw(Integer.toString(v, 10));
		}

		@Override
		public void addInt64(long v) {
			addStringRaw(Long.toString(v, 10));
		}

		@Override
		public void addFloat32(float v) {
			addStringRaw(Float.toString(v));
		}

		@Override
		public void addFloat64(double v) {
			addStringRaw(Double.toString(v));
		}
	
		@Override
		public <Cs extends CharSequence> void addString(Cs v) {
			switch (modeHead()) {
			case MAP_LABEL:
				sbld.append('"').append(v).append('"');
				modeHeadSet(Mode.MAP_NEXT);
				break;
			case LIST_FIRST:
				sbld.append('"').append(v).append('"');
				modeHeadSet(Mode.LIST_NEXT);
				break;
			case LIST_NEXT:
				sbld.append(",\"").append(v).append('"');
				break;
			default:
				throw new IllegalStateException();
			}
		}

		private void addStringRaw(String v) {
			switch (modeHead()) {
			case MAP_LABEL:
				sbld.append(v);
				modeHeadSet(Mode.MAP_NEXT);
				break;
			case LIST_FIRST:
				sbld.append(v);
				modeHeadSet(Mode.LIST_NEXT);
				break;
			case LIST_NEXT:
				sbld.append(',').append(v);
				break;
			default:
				throw new IllegalStateException();
			}	
		}

		@Override
		public Message.Fragment build() {
			return new Fragment(
				StandardCharsets.UTF_8.encode(sbld.toString())
			);
		}

		private static enum Mode {
			MAP_FIRST,
			MAP_NEXT,
			MAP_LABEL,
			LIST_FIRST,
			LIST_NEXT;
		}

		private Mode modeHead() {
			return modeStack[modeStackPos];
		}

		private void modeHeadSet(Mode m) {
			modeStack[modeStackPos] = m;
		}

		private void modePush(Mode m) {
			modeStackPos++;
			if (modeStackPos < modeStack.length)
				modeStack[modeStackPos] = m;
			else {
				var cur = modeStack.length;
				modeStack = Arrays.copyOf(
					modeStack,
					cur + modeStackLast
				);
				modeStackLast = cur;
				modeStack[modeStackPos] = m;
			}
		}

		private void modePop() {
			modeStackPos--;
		}

		private final StringBuilder sbld = new StringBuilder();
		private Mode[] modeStack = new Mode[3];
		private int modeStackLast = 2;
		private int modeStackPos = 0;
	}

	@Override
	public ByteBuffer make(Message.Fragment f0_, Message.Fragment f1_) {
		var f0 = (Fragment)f0_;
		var f1 = (Fragment)f1_;

		var next = ByteBuffer.allocate(
			f0.data.limit()
			+ f1.data.limit()
			+ 3
		);
		var flag = (f0.data.limit() > 0 ? 1 : 0)
			| (f1.data.limit() > 0 ? 2 : 0);

		next.put((byte)'{');
		switch (flag) {
		case 0:
			break;
		case 1:
			next.put(f0.data);
			break;
		case 2:
			next.put(f1.data);
			break;
		case 3:
			next.put(f0.data);
			next.put((byte)',');
			next.put(f1.data);
			break;
		}
		next.put((byte)'}');
		return next;
	}

	@Override
	public ByteBuffer make(
		Message.Fragment f0_, Message.Fragment f1_, Message.Fragment f2_
	) {
		var f0 = (Fragment)f0_;
		var f1 = (Fragment)f1_;
		var f2 = (Fragment)f2_;

		var next = ByteBuffer.allocate(
			f0.data.limit()
			+ f1.data.limit()
			+ f2.data.limit()
			+ 4
		);

		var flag = (f0.data.limit() > 0 ? 1 : 0)
			| (f1.data.limit() > 0 ? 2 : 0)
			| (f2.data.limit() > 0 ? 4 : 0);
		next.put((byte)'{');
		switch (flag) {
		case 0:
			break;
		case 1:
			next.put(f0.data);
			break;
		case 2:
			next.put(f1.data);
			break;
		case 3:
			next.put(f0.data);
			next.put((byte)',');
			next.put(f1.data);
			break;
		case 4:
			next.put(f2.data);
			break;
		case 5:
			next.put(f0.data);
			next.put((byte)',');
			next.put(f2.data);
			break;
		case 6:
			next.put(f1.data);
			next.put((byte)',');
			next.put(f2.data);
			break;
		case 7:
			next.put(f0.data);
			next.put((byte)',');
			next.put(f1.data);
			next.put((byte)',');
			next.put(f2.data);
			break;
		}
		next.put((byte)'}');
		return next;
	}

	@Override
	public Message.FragmentBuilder fragmentBuilder() {
		return new FragmentBuilder();
	}
}
