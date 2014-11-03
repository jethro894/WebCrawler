package web.crawler;

import java.util.StringTokenizer;

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
		
		String query = constructQuery(keyword, page);
		
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
