package hello;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/date")
    public String date() {
        return LocalDateTime.now().toString();
    }

    @RequestMapping("/rss")
    //@Produces("text/html")
    public String rss(){

        try {
            URL feedUrl = new URL("http://monUrl");
            HttpURLConnection httpcon = (HttpURLConnection)feedUrl.openConnection();
            String encoding = new sun.misc.BASE64Encoder().
                    encode("user:password".getBytes());
            httpcon.setRequestProperty ("Authorization", "Basic " + encoding);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));
            StringBuilder sb = new StringBuilder();
            List<SyndEntry> entries = feed.getEntries();
            for(SyndEntry entry : entries) {
                sb.append("<h2>");
                sb.append(entry.getPublishedDate().toString());
                sb.append(" - ");
                sb.append(entry.getTitle());
                sb.append("</h2>");
            }
            return sb.toString();
//            SyndFeedInput input = new SyndFeedInput();
//            SyndFeed feed = input.build(new XmlReader(feedSource));
            //return "SUCCESS";
        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (FeedException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}