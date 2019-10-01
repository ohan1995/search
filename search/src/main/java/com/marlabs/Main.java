package com.marlabs;

import com.marlabs.exception.BusinessException;
import com.marlabs.service.SearchService;
import com.marlabs.service.SearchServiceImpl;
import lombok.extern.log4j.Log4j2;
import java.util.Map;
import java.util.Scanner;

@Log4j2
public class Main {
	
	
	public static void main(String[] args) throws BusinessException {
		
		log.info("Main() Method Initialized");
		
		SearchService service = new SearchServiceImpl();
		
		// Calling openDirectory Method to display the list of books and store the file list in Map
		Map<Integer, String> fileList = service.openDirectory();

		System.out.println("Enter the Book Number: ");
		Scanner input = new Scanner(System.in);
		
		//Taking Book Number from the User
		int bookNumber = input.nextInt();
		input.nextLine();
		
		System.out.println("Enter the word: ");
		
		while (input.hasNextLine()) {
			try {
				//Passing all the required values 
				service.searchKeyword(bookNumber, input.nextLine(), fileList);
				System.out.println("Enter the word: ");

			} catch (BusinessException ex) {
				log.error(ex.getErrorMsg());
			}
		}
		input.close();
	}
}
