import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CrawlerThread extends Thread {

	MainCrawler core;
	long TAG;
	BufferedWriter writer;

	public CrawlerThread(MainCrawler core) {
		this.core = core;
		TAG = this.getId();
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(
							"C:\\Users\\henry\\Desktop\\Crawler output\\output"
									+ TAG + "_1.txt")), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (core.continueRunning) {
			String urlString = core.next();
			System.out.println(TAG+" reading "+urlString+"  "+core.getListSize());
			if (urlString != null) {
				String page = getWebsight(urlString);
				List<String> results = parsePage(page);
				filter(results);
				write(urlString, results);
				for (String site : results)
					core.add(site);
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void filter(List<String> results)
	{
		for(int a = 0; a < results.size();)
		{
			if(MainCrawler.isSerchableURL(results.get(a)))
				a++;
			else
				results.remove(a);
		}
	}

	private void write(String urlString, List<String> results)
	{
		try {
			writer.write(urlString);
			writer.write("=[");
			for(String url:results)
			{
				writer.write(url);
				writer.write(",");
			}
			writer.write("]");
			writer.newLine();
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> parsePage(String page) {
		List<String> links = new ArrayList<>();
		String[] starting_with_ref = page.split("<a href=");
		for (String str : starting_with_ref) {
			String linkText = str.split("\"")[1];
			//System.out.println(linkText+","+str);
			String newLink=linkText;
			if(!newLink.startsWith("https://en.wikipedia.org"))
				newLink="https://en.wikipedia.org"+newLink;
			links.add(newLink);
		}
		return links;
	}

	public String getWebsight(String urlString) {
		InputStream is = null;

		try {
			URL url = new URL(urlString);
			is = url.openStream(); // throws an IOException

			String string = readInputStream(is);
			return string;
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				// nothing to see here
			}
		}
		return "";
	}

	public String readInputStream(InputStream is) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line).append("\n");
		}
		return total.toString();
	}
}
