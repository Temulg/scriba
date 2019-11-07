/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg;

import static temulg.scriba.Field.*;
import temulg.scriba.RootScope;

public class Tac0 {
	public static void main(String... args) {
		var s = RootScope.builder().build();
		s.say("aaa", int32("zz", 15), float32("boo", 25f));
		s.say(str("msgx", "aaa"), int32("zz", 15), float32("boo", 25f));
	}
}
