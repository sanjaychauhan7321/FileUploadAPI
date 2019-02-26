package com.nokia.sanjay.test;

import java.io.File;

import com.nokia.sanjay.FileUploadApi.FileUploadApiApplication;
import com.nokia.sanjay.apis.RegexpMatchCalculator;
import com.nokia.sanjay.exceptions.RegexpException;
import com.nokia.sanjay.impl.RegexpMatchCalculatorImpl;

public class Tester {

	public static void main(String[] args) {

		RegexpMatchCalculator regexpCalculator = new RegexpMatchCalculatorImpl();

		try {

			String sourceString = "";

			System.out.println("length of the String is : " + sourceString.length() + " and it is "
					+ ((sourceString.length() > 2147483647) ? "Greater" : "Shorter") + " than 2147483647");

			regexpCalculator.findRegexpMatch(sourceString);
			
			FileUploadApiApplication api = new FileUploadApiApplication();
			
			

		} catch (RegexpException e) {

			System.out.println(e.getErrorMessage());
			System.out.println(e.getErrorCode());

		} catch (Throwable ex) {

			System.out.println("Unexpected error occured! Reason : " + ex.getMessage());

		}

	}

}
