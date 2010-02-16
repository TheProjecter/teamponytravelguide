package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;
import json.JSONException;
import json.JSONObject;



public class locationService extends Thread{

 // **** Set the developer key here ****
 private final static String API_KEY = "qaU5H4yExSxoBMDeBt6pKcea1YNE7EGgAcY8Qwkd";

 private boolean paused;
 private Cell cell;
 private Form form;
 private boolean temporary =false;
 private StringItem itemLongitude, itemLatitude;
 private StringItem itemAccuracy, itemName;
 public double longitude =0, latitude =0, accuracy;
 private String cellName;

 public locationService(boolean Temporary)  throws IOException{
  paused=false;
  temporary = Temporary;
  form = new Form(null);

  itemLongitude = new StringItem("longitude: ",
		  Double.toString(longitude));
  itemLatitude = new StringItem("latitude: ",
		  Double.toString(latitude));
  itemAccuracy = new StringItem("accuracy: ",
		  Double.toString(accuracy));
  itemName = new StringItem("name: ",cellName);
  this.start();
 }

 public void run() {
     if(paused){
            try {
                this.sleep((long) 100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
     }else{
            try {
                updatePosition();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
     }
 }
 public void pause(){
     paused=true;
 }
 public void resume(){
     paused=false;
 }
 public double getX(){
     return latitude;
 }
 public double getY(){
     return longitude;
 }
 private void updateForm() {
  itemLongitude.setText(Double.toString(longitude));
  itemLatitude.setText(Double.toString(latitude));
  itemAccuracy.setText(Double.toString(accuracy));
  itemName.setText(cellName);
 }


 /**
  * updatePosition
  * The format of the position data is either xml or json.
  * In this example we are using json
  */
 public void updatePosition()  throws IOException{
  StringBuffer url = new StringBuffer();
  url.append("http://cellid.labs.ericsson.net/json/lookup");
  url.append("?cellid=").append(cell.getCellId());
  url.append("&mnc=").append(cell.getMnc());
  url.append("&mcc=").append(cell.getMcc());
  url.append("&lac=").append(cell.getLac());
  url.append("&key=").append(API_KEY);

  try {
   byte[] data = getHttp(url.toString());
   if(data!=null) {
                try {
                    parseJSON(new String(data));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
	updateForm();
        if(temporary){
            
        }
   }
  } catch (IOException e) {
   e.printStackTrace();
  }
 }


 public void parseJSON(String jsonString) throws JSONException {

   JSONObject o = new JSONObject(jsonString);
   JSONObject pos = o.getJSONObject("position");
   this.longitude = pos.getDouble("longitude");
   this.latitude = pos.getDouble("latitude");
   this.accuracy = pos.getDouble("accuracy");
   this.cellName = pos.optString("name");

 }

 static public byte[] getHttp(String url)
  throws IOException {

  HttpConnection c = null;
  InputStream is = null;
  byte[] data = null;

  try {
   c = (HttpConnection)Connector.open(url);
   int rc = c.getResponseCode();

   // handle the HTTP response codes used by the API
   if(rc!=HttpConnection.HTTP_OK) {
	switch(rc) {
	case HttpConnection.HTTP_NO_CONTENT:
	 throw new IOException("The cell could not be "+
		"found in the database");
	case HttpConnection.HTTP_BAD_REQUEST:
	 throw new IOException("Check if some parameter "+
		"is missing or misspelled");
	case HttpConnection.HTTP_UNAUTHORIZED:
	 throw new IOException("Make sure the API key is "+
		"present and valid");
	case HttpConnection.HTTP_FORBIDDEN:
	 throw new IOException("You have reached the limit "+
		"for the number of requests per day. The maximum "+
		"number of requests per day is currently 500.");
	case HttpConnection.HTTP_NOT_FOUND:
	 throw new IOException("The cell could not be found "+
			 "in the database");
	default:
	 throw new IOException("HTTP response code: " + rc);
	}
   }

   is = c.openInputStream();

   int actual = 0;
   int len = (int)c.getLength();
   if(len>0) {
	int bytesread = 0 ;
	data = new byte[len];
	while((bytesread != len) && (actual != -1)) {
	 actual = is.read(data, bytesread, len - bytesread);
	 bytesread += actual;
	}
   } else {
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	byte buf[] = new byte[256];
	while((actual = is.read(buf, 0, 256))!=-1) {
	 bos.write(buf, 0, actual);
	}
	bos.flush();
	data = bos.toByteArray();
   }
  } catch (ClassCastException e) {
   throw new IllegalArgumentException("Not an HTTP URL");
  } finally {
   try { if (c != null) c.close(); } catch(IOException e) {};
   try { if (is != null) is.close(); } catch(IOException e) {};
  }
  return data;
 }

 
 public class Cell {

  private String id;
  private String lac;
  private String mcc;
  private String mnc;

  public Cell() {
   update();
  }

  public void update() {
   try {
	id = System.getProperty("com.sonyericsson.net.cellid");
	lac = System.getProperty("com.sonyericsson.net.lac");
	mcc = System.getProperty("com.sonyericsson.net.cmcc");
	mnc = System.getProperty("com.sonyericsson.net.cmnc");
   } catch(Exception e) {
	e.printStackTrace();
   }
  }

  public String getCellId() { return id; }
  public String getLac() { return lac; }
  public String getMcc() { return mcc; }
  public String getMnc() { return mnc; }
 }
}