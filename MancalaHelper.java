import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import javax.swing.JOptionPane;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.util.StringTokenizer;


 
public class MancalaHelper
{
    static Mancala game = new Mancala();
   
	public static void main(String[] args)
	{
	    // default port
	    int port = 4040;
	   
	    
	    // parse command line arguments to override defaults
	    if (args.length > 0)
		{
		    try
			{
			    port = Integer.parseInt(args[0]);
			    
			}
		    catch (NumberFormatException ex)
			{
			    System.err.println("USAGE: java KaylesService [port]");
			    System.exit(1);
			}
		}
	    
	    // set up an HTTP server to listen on the selected port
	    try
		{
		    InetSocketAddress addr = new InetSocketAddress(port);
		    HttpServer server = HttpServer.create(addr, 1);
		    
		    server.createContext("/computer.html", new MoveHandler());
        
		    server.start();
		}
	    catch (IOException ex)
		{
		    ex.printStackTrace(System.err);
		    System.err.println("Could not start server");
		}
	}
    
   
    public static class MoveHandler implements HttpHandler
    {
        @Override
	    public void handle(HttpExchange ex) throws IOException
        {
            System.err.println(ex.getRequestURI());
            String q = ex.getRequestURI().getQuery();
      
	    
	    Map<String, String> response  = new HashMap<String, String>();
	    // response.put(q, "hello");
	    
     
	    String move = game.compMove(q);
	    response.put("move", move);
	    sendResponse(ex, response);
	}

	
    }

    /*  *
     * Returns a map containing key-value pairs for the given state and message.
     *
     * @param state a string
     * @param message a string
     * 
     * @return a map containing key-value pairs for state and message*/
    private static void sendResponse(HttpExchange ex, Map<String,String> info) throws IOException
    {
	// write the response as JSON
		StringBuilder response = new StringBuilder("{");
		for (Map.Entry<String, String> e : info.entrySet())
	    {
		response.append("\"").append(e.getKey()).append("\":")
		    .append("\"").append(e.getValue()).append("\",");
		    }
       
		response.deleteCharAt(response.length() - 1); // remove last ,
		response.append("}"); // close JSON;
	
	ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
	byte[] responseBytes = response.toString().getBytes();
	ex.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseBytes.length);
	ex.getResponseBody().write(responseBytes);
	ex.close();
    }
    
}
