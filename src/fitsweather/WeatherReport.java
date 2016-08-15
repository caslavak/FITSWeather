package fitsweather;

import org.jdom2.Element;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;

/*  Sample input
<timeReceived>20160720193102</timeReceived>
<temperature>82</temperature>
<windSpeed>0</windSpeed>
<windDirection>271</windDirection>
<windGust>9</windGust>
<rain1h>0</rain1h>
<humidity>45</humidity>
<barometer>1022.7</barometer>
</weatherReport>
*/

/**
 * Container for a weather report.
 */
public class WeatherReport {
    private Date timestamp;
    private Integer tempF;
    private Integer windSpeedMph;
    private Integer windDirection;
    private Integer humidity;
    private Double pressure;

    public WeatherReport(Element report) {
        timestamp = new SimpleDateFormat("yyyyMMddHHmmss").parse(report.getChild("timeReceived").getText(), new ParsePosition(0));
        tempF = Integer.parseInt(report.getChild("temperature").getText());
        windSpeedMph = Integer.parseInt(report.getChild("windSpeed").getText());
        windDirection = Integer.parseInt(report.getChild("windDirection").getText());
        humidity = Integer.parseInt(report.getChild("humidity").getText());
        pressure = Double.parseDouble(report.getChildText("barometer"));
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getTempF() {
        return tempF;
    }

    public Integer getTempC() {
        return round(((tempF - 32)*5)/9);
    }

    public Integer getWindSpeedMph() {
        return windSpeedMph;
    }

    public Integer getWindSpeedKph() {
        return round((windSpeedMph*1.609f));
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return "Time (UTC): " + DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(timestamp) + "\n" +
                "Temperature: " + tempF + " °F\n" +
                "Wind Speed: " + windSpeedMph + " MPH\n" +
                "Wind Direction: " + windDirection + "°\n" +
                "Humidity: " + humidity + "%\n" +
                "Pressure: " + pressure + " mbar\n";
    }
}
