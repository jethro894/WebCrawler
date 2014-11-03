package web.crawler;

import java.io.IOException;

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

		new Scrapper(keyword, page).scrap();
	}
	
	private static boolean isInteger(String s){
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
		
	}

}
