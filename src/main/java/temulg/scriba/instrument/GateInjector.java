/*
 * Copyright (c) 2019 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.scriba.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class GateInjector implements ClassFileTransformer {
	@Override
	public byte[] transform(
		ClassLoader loader,
		String className,
		Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain,
		byte[] classfileBuffer
	) throws IllegalClassFormatException {
		System.out.format("=a1= transform %s\n", className);
		return null;
	}

	public  byte[] transform(
		Module module,
		ClassLoader loader,
		String className,
		Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain,
		byte[] classfileBuffer
	) throws IllegalClassFormatException {
		System.out.format("=b1= transform %s\n", className);
		return null;
	}
}
