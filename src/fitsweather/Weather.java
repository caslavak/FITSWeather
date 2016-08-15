package fitsweather;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.io.InputStream;
import java.net.URL;

/**
 * Class that receives and parses weather data.
 */
public class Weather {
    private URL sourceUrl;
    private String stationCode;
    private final String baseUrl = "http://www.findu.com/cgi-bin/wxxml.cgi?call=";
    private Document doc;


    /**
     * Acquire current weather information for the station.
     * @param stationCode Station code
     */
    public Weather(String stationCode) {
        this.stationCode = stationCode;
        try {
            sourceUrl = new URL(baseUrl + stationCode);
            InputStream xml = sourceUrl.openStream();
            SAXBuilder saxBuilder = new SAXBuilder();
            doc = saxBuilder.build(xml);
            xml.close();
        }catch(Exception ex){
            System.err.println(ex);
        }
    }

    /**
     * Get weather report data
     * @return Object with all weather data.
     */
    public WeatherReport getWeather(){
        Element report = doc.getRootElement().getChild("weatherReport");
        return new WeatherReport(report);
    }
}
