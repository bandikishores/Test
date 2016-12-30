package com.test;

public enum FileType {
	DIRECTORY("D"), FILE("F");

	String value;

	FileType(String value) {
		this.value = value;
	}

}
