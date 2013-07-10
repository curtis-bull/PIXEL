
package org.onebeartoe.pixel.plugins.weather;

import ioio.lib.api.RgbLedMatrix.Matrix;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.onebeartoe.pixel.plugins.swing.SingleThreadedPixelPanel;

/**
 *
 */
public class WeatherByWoeid extends SingleThreadedPixelPanel
{
    private ActionListener worker = new WeatherWorker();
    
    public WeatherByWoeid(Matrix m)
    {
	super(m);
	
//	timer.setDelay(5000);
	
	worker = new WeatherWorker();
	
	setLayout( new BorderLayout() );
	
	JLabel label = new JLabel("Weather Panel");
	
	JEditorPane webView = new JEditorPane();
	webView.setContentType("text/html");
	String uri = "http://weather.yahooapis.com/forecastrss?w=615702";
	int start = -1;
	int end = -1;
	try 
	{
            WeatherService weatherService = new WeatherService();
	    InputStream dataIn = weatherService.retrieve( uri );
	    
	    // Parse Data
	    Weather weather = weatherService.parse( dataIn );
	    
	    String description = weatherService.format( weather );
            
	    webView.setText(description);
	} 
	catch (Exception ex) 
	{
	    String message = "start: " + start + "  -   end: " + end;
	    Logger.getLogger(WeatherByWoeid.class.getName()).log(Level.SEVERE, message, ex);
	}
        JScrollPane webviewScroller = new JScrollPane(webView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	add(label, BorderLayout.NORTH);
	add(webviewScroller, BorderLayout.CENTER);
    }
    
    @Override
    public ActionListener getActionListener() 
    {        
        return worker;
    }
    
    @Override
    public ImageIcon getTabIcon()
    {
	System.out.println("\n\n\nusing a custom tab\n");
	String path = "tab-icon.png";
	URL url = getClass().getResource(path);
        ImageIcon imagesTabIcon = new ImageIcon(url);
	
	return imagesTabIcon;
    }
    
    public class WeatherWorker implements ActionListener
    {
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	    timer.setDelay(5000);
	    System.out.println("weather works great");
	}
    }
    
}
