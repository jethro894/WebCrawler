package web.crawler;

import java.util.StringTokenizer;

/*
 * Scrapper is the entrance of the actual program
 * Construct the query URL, by looking at the page number
 * If page number is negative, do not include page number in query
 * Retrieve the webpage using WebDocRetriever
 * Then call certain query parser according to query type
 */

public class Scrapper {

	private String keyword = null;
	private int page;
	
	public Scrapper(String keyword, int page) {
		super();
		this.keyword = keyword;
		this.page = page;
	}
	
	public void scrap(){
		if(keyword == null){
			System.out.println("Error: failed on passing parameters to Scrapper.");
			return;
		}
		System.out.print("Constructing query url...");
		String query = constructQuery(keyword, page);
		System.out.println("Done");
		System.out.println("Query Url: " + query);
		System.out.println();
		
		if(page < 0)
			new Query1Parser(new WebDocRetriever(query).retrieve()).parse();
		else
			new Query2Parser(new WebDocRetriever(query).retrieve()).parse();
	}
	
	private String constructQuery(String keyword, int page){
		StringTokenizer st = new StringTokenizer(keyword);
		StringBuilder sb = new StringBuilder();
		while(st.hasMoreTokens()){
			sb.append(st.nextToken());
			sb.append(" ");
		}
		keyword = sb.toString().trim().replace(" ", "%20");
		
		String searchBase = "http://www.walmart.com/search/?";
		sb = new StringBuilder(searchBase);
		sb.append("query=");
		sb.append(keyword);
		
		if(page > 0){
			sb.append("&page=");
			sb.append(page);
		}
		
		return sb.toString();
	}
	
}
