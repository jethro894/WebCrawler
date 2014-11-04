package web.crawler;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * Given a query, retrieve a result webpage using Jsoup
 * There is a high probability that HTTPConnection is timed out
 * In this case we retry and reach for the webpage again
 * Max number of re-trial is by default 3
 */

public class WebDocRetriever {
	String query = null;
	final int max_trial = 3;
	
	public WebDocRetriever(String query) {
		super();
		this.query = query;
	}
	
	public Document retrieve(){
		if(query == null){
			System.out.println("Error: failed on passing parameters to Retriever.");
			return null;
		}
		
		Document doc = null;
		int count = 0;
		while(true){
			try {
				doc = Jsoup.parse(Jsoup.connect(query).get().html());
				break;
			} catch(SocketTimeoutException e){
				if (++count == max_trial) {
					e.printStackTrace();
					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		return doc;
	}
}
