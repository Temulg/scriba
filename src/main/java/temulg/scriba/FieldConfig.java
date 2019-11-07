/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba;

public interface FieldConfig {
	default String caller() {
		return "caller";
	}

	default String callerClass() {
		return "class";
	}

	default String message() {
		return "msg";
	}

	default String scope() {
		return "scope";
	}

	default String timestamp() {
		return "ts";
	}

	default String childScopeName(String parent, String name) {
		return parent + '.' + name;
	}
}
