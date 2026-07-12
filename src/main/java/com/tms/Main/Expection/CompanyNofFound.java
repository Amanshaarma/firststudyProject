package com.tms.Main.Expection;

public class CompanyNofFound extends RuntimeException {
	public CompanyNofFound(String message) {
		super(message);
	}
}
