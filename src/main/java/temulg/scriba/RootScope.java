/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

import java.util.ArrayList;

import temulg.scriba.detail.Context;
import temulg.scriba.message.Message;

public class RootScope extends Scope {
	public static class Builder {
		public RootScope build() {
			var mbld = ctx.fragmentBuilder();
			for (var f: common)
				f.visit(mbld);

			return new RootScope(ctx, mbld.build());
		}

		private Context ctx = new Context();
		private final ArrayList<Field> common = new ArrayList<>();
	}

	public static Builder builder() {
		return new Builder();
	}

	private RootScope(Context ctx_, Message.Fragment common_) {
		super(
			Field.EMPTY,
			null,
			ctx_,
			common_
		);
	}
}
