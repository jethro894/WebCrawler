package web.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;

public class Query1Parser {
	Document doc;

	public Query1Parser(Document doc) {
		super();
		this.doc = doc;
	}

	public void parse(){
		if(doc == null){
			System.out.println("Error: failed on fetching query result.");
			return;
		}
		
		Elements scripts = doc.select("script");
		String keyScript = null;
		for(Element script : scripts){
			if(script.html().contains("totalResults"))
				keyScript = script.html().substring(script.html().indexOf('\n') + 1).trim();
		}

		if(keyScript == null){
			System.out.println("Error: cannot find total number of results.");
			return;
		}
		keyScript = keyScript.substring(keyScript.indexOf('{'));
		JSONObject json = new JSONObject(keyScript);
		
		Elements results = doc.select("h1");
		System.out.print("Number of " + results.get(0).text() + ": ");
		System.out.println(json.get("totalResults"));
	}
}
