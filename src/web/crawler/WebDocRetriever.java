package web.crawler;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebDocRetriever {
	String query = null;

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
		int max_count = 3;
		while(true){
			try {
				doc = Jsoup.parse(Jsoup.connect(query).get().html());
				break;
			} catch(SocketTimeoutException e){
				if (++count == max_count) {
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
