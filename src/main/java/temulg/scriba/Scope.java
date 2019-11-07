/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import temulg.scriba.detail.Context;
import temulg.scriba.detail.ThreadContext;
import temulg.scriba.message.Message;

public class Scope {
	public Scope withName(String name_) {
		if (name_ != "") {
			return new Scope(
				name == Field.EMPTY
				? Field.str(ctx.fieldConfig().scope(), name_)
				: Field.str(
					ctx.fieldConfig().scope(),
					ctx.fieldConfig().childScopeName(
						((Field.Str)name).value, name_
					)
				), this, ctx, this.common
			);
		} else
			return this;
	}

	public Scope withFields(Field... fs) {
		if (fs.length != 0) {
			var mbld = ctx.fragmentBuilder();
			for (var f: fs)
				f.visit(mbld);

			return new Scope(
				name,
				this,
				ctx,
				common.append(mbld.build())
			);
		} else
			return this;
	}

	@Gated
	public void say(String msg) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(String msg, Field f0) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			f0.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(String msg, Field f0, Field f1) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(String msg, Field f0, Field f1, Field f2) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(String msg, Field f0, Field f1, Field f2, Field f3) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(String msg, Field... fs) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			mbld.addLabel(ctx.fieldConfig().message());
			mbld.addString(msg);
			name.visit(mbld);
			for (var f: fs)
				f.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field f0) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			f0.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field f0, Field f1) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field f0, Field f1, Field f2) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field f0, Field f1, Field f2, Field f3) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field f0, Field f1, Field f2, Field f3, Field f4) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);
			f4.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public void say(Field... fs) {
		var g = ThreadContext.get().resetGate();
		if (g.enabled()) {
			var mbld = ctx.fragmentBuilder();
			name.visit(mbld);
			for (var f: fs)
				f.visit(mbld);

			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	}

	@Gated
	public CheckedScope check() {
		var g = ThreadContext.get().resetGate();
		return g.enabled() ? new CheckedScope(g) : null;
	}

	public class CheckedScope {
		private CheckedScope(Gate g_) {
			g = g_;
		}

		public void say(String msg) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(String msg, Field f0) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);
			f0.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(String msg, Field f0, Field f1) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(String msg, Field f0, Field f1, Field f2) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(
			String msg, Field f0, Field f1, Field f2, Field f3
		) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(String msg, Field... fs) {
			var mbld = ctx.fragmentBuilder();
			Field.str(ctx.fieldConfig().message(), msg).visit(mbld);
			for (var f: fs)
				f.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(Field f0) {
			var mbld = ctx.fragmentBuilder();
			f0.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(Field f0, Field f1) {
			var mbld = ctx.fragmentBuilder();
			f0.visit(mbld);
			f1.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(Field f0, Field f1, Field f2) {
			var mbld = ctx.fragmentBuilder();
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(Field f0, Field f1, Field f2, Field f3) {
			var mbld = ctx.fragmentBuilder();
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(
			Field f0, Field f1, Field f2, Field f3, Field f4
		) {
			var mbld = ctx.fragmentBuilder();
			f0.visit(mbld);
			f1.visit(mbld);
			f2.visit(mbld);
			f3.visit(mbld);
			f4.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}
	
		public void say(Field... fs) {
			var mbld = ctx.fragmentBuilder();
			for (var f: fs)
				f.visit(mbld);

			name.visit(mbld);
			g.visit(mbld, ctx.fieldConfig());
			ctx.deliver(mbld.build(), common);
		}

		private final Gate g;
	}

	protected Scope(
		Field name_,
		Scope parent_,
		Context ctx_,
		Message.Fragment common_
	) {
		name = name_;
		parent = parent_;
		ctx = ctx_;
		common= common_;
	}

	private final Field name;
	private final Scope parent;
	private final Context ctx;
	private final Message.Fragment common;
}
