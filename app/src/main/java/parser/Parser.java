package parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.net.HttpURLConnection;
import Entities.Season;
import Entities.Seria;
import Entities.Serial;

public class Parser {
	private static final String URL = "http://starserial.ru/";
	private static final String IMAGE_URL_PART = "http://img.starserial.ru/img/thumb/";
	private static final String[] IMAGE_EXTEN = {".jpg", ".png", ".jpeg"};
	private static final String SITE_MAIN_DIV = "div#text-17";
	private static final String DIV_CONTEYNER = "div.textwidget";
	private static final String SERIAL_LIST = ".textwidget > ul > li > a[href]";
	private static final String SERIALS_SEPERATOR = "a:containsOwn(| Сезон)";
	private static final String SERIALS_SEPERATOR_ = "a:containsOwn(| Сезон ";
			
	public ArrayList<Serial> getAll() throws IOException {
		ArrayList<Serial> serials = null;
		ArrayList<Season> seasons;
		Document doc  = Jsoup.connect(URL).get();
		 Elements info = doc.select(SITE_MAIN_DIV).select(DIV_CONTEYNER).select(SERIAL_LIST);
		 serials = new ArrayList<>(info.size());
		 String name;
		 String url;
		 int i = 0;
		 for (Element element : info) {
			 url = element.absUrl("href");
			 name = element.text();
			 seasons = getSeasons(url);
			 serials.add(new Serial(name, url, getPoster(url), seasons));
			 i++;
			 if (i == 8) {
				 break;
			 }
		 }
		return serials;
	}
	
	private String getPoster(String name) {
		String poster = null;
		String curPoster = null;
		for (String extension : IMAGE_EXTEN) {
			curPoster = IMAGE_URL_PART + name.substring(name.lastIndexOf("/") + 1, name.length()) + extension;
			try {
			    URL url = new URL(curPoster);
			    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			    huc.setRequestMethod("HEAD");
			    poster = curPoster;
			    break;
			} 
			catch (Exception e) {  
				e.printStackTrace();
			}
		}
		return poster;
	}
	
	private ArrayList<Season> getSeasons(String url) throws IOException {
		ArrayList<Season> seasons = null;
		ArrayList<Seria> serias = null;
		Document doc  = Jsoup.connect(url).get();
		Elements allSerials = doc.select(SERIALS_SEPERATOR);
		Elements serials;
		Season season;
		String text;
		if (!allSerials.isEmpty()) {
			seasons = new ArrayList<>();
			for (int i = 1; i < 1000; i++) {
				serials = allSerials.select(SERIALS_SEPERATOR_ + Integer.valueOf(i) + ")");
				if (serials.isEmpty()) {
					break;
				}
				text = serials.get(0).text();
				season = new Season(text.substring(text.indexOf("|") + 1, text.lastIndexOf("|")), null);
				serias = new ArrayList<>(serials.size());
				for (Element seria: serials) {
					text = seria.text();
					serias.add(new Seria(text.substring(text.lastIndexOf("|") + 1, text.length()),
							seria.absUrl("href")));
				}
				season.setSerias(serias);
				seasons.add(season);
			}
		}
		return seasons;
	}
	
	public Serial getSerial(Serial serial) throws IOException {
		String url = serial.getUrl();
		ArrayList<Season> currSeasons = getSeasons(url);
		ArrayList<Season> seasons = serial.getSeasons();
		ArrayList<Seria> serias;
		ArrayList<Seria> currSerias;
		if (seasons.size() == currSeasons.size()) {
			serias = seasons.get(seasons.size() - 1).getSerias();
			currSerias = currSeasons.get(seasons.size() - 1).getSerias();
			serias.addAll(currSerias.subList(serias.size() - 1, currSerias.size() - 1));
		} else {
			seasons.addAll(currSeasons.subList(seasons.size() - 1, currSeasons.size() - 1));
		}
		return serial;
	}

	public ArrayList<Serial> getFollowSerials() {
		ArrayList<Serial> serials = new ArrayList<>();

		return serials;
	}
}
