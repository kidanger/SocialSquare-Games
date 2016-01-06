package core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServerUtils {

	static private Gson gson = new Gson();

	static private InputStream sendRequest(URL url) throws Exception {
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return urlConnection.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static public List<Score> getScoresOfGame(String game) {
		String url = "http://localhost:8000/get_scores?game=" + game;

		try {
			InputStream inputStream = sendRequest(new URL(url));
			if(inputStream != null) {
				InputStreamReader reader = new InputStreamReader(inputStream);
				return gson.fromJson(reader, new TypeToken<List<Score>>() {}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static public void updateTerminal(int id, boolean used, String gameName) {
		String url = "http://localhost:8000/update_terminal?used=" + used;
		if (gameName != null) {
			url += "&game=" + gameName;
		} else {
			url += "&game=NULL";
		}
		url += "&id=" + id;

		try {
			sendRequest(new URL(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public void postScore(String game, String username, int score) {
		String url = "http://localhost:8000/post_score?game=" + game + "&user=" + username + "&score=" + score;
		try {
			sendRequest(new URL(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
