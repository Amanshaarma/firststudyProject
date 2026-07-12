package com.study.Main.Expection;

public class CompanyNofFound extends RuntimeException {
	public CompanyNofFound(String message) {
		super(message);
	}
}
