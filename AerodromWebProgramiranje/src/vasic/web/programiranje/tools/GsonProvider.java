package vasic.web.programiranje.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
	
	private static GsonProvider gsonProvider;
	private Gson gson;
	
	private GsonProvider() {
		this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
	}
	
	public static Gson getInstance() {
		if(gsonProvider == null) {
			gsonProvider =  new GsonProvider();
		}
		return gsonProvider.getGson();
	}
	
	public Gson getGson() {
		return this.gson;
	}

}
