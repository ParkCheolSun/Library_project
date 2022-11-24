package com.library.constant;

public enum LibCode {
	Code01(124004), Code02(124012), Code03(129224), Code04(129219), Code05(124007);

	private final int value;

	LibCode(int value) { this.value = value; }
	
	public int getValue() { return value; }
}
