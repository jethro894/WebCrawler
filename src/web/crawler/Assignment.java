package web.crawler;

import java.io.IOException;

/*
 * This is the entrance of the crawler
 * It checks the basic input arguments and types
 * page number is by default -1
 * If page number is negative after parsing the argument, then it is type 1 query (total results)
 * Otherwise it is type 2.
 */

public class Assignment {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String keyword;
		int page = -1;
		
		if(args.length < 1 || args.length > 2){
			//wrong number of arguments
			System.out.println("Invalid command. Please see instructions below.");
			System.out.println("Assignment <keyword>");
			System.out.println("or");
			System.out.println("Assignment <keyword> <page number>");
			System.out.println();
			System.out.println("Options:");
			System.out.println("<keyword>: a String about catagory or item to query. DOUBLE QUOTE it if you wish to use a phrase.");
			System.out.println("<page number>: a positive integer to show a page of items");
			return;
		}
		
		keyword = args[0];
		
		if(args.length == 2){
			if(!isInteger(args[1])){
				//cannot parse page number
				System.out.println("Invalid command. Please see instructions below.");
				System.out.println("Options:");
				System.out.println("<keyword>: a String about catagory or item to query. DOUBLE QUOTE it if you wish to use a phrase.");
				System.out.println("<page number>: a positive integer to show a page of items");
				return;
			}
			page = Integer.parseInt(args[1]);
			if(page < 1){
				System.out.println("Invalid command. Please see instructions below.");
				System.out.println("<page number>: a positive integer to show a page of items");
				return;
			}
		}

		System.out.println("Your keyword: " + keyword);
		if(page > 0)
			System.out.println("Your page no: " + page);
		System.out.println();
		System.out.println("Starting Walmart Scrapper.");
		new Scrapper(keyword, page).scrap();
	}
	
	//check whether integer argument is a valid integer
	private static boolean isInteger(String s){
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
		
	}

}
