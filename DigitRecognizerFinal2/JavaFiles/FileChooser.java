import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class FileChooser {

	/**
	   * Method to get the full path for the passed file name
	   * @param fileName the name of a file
	   * @return the full path for the file
	   */
	  public static String getMediaPath(String fileName) 
	  {
	    String path = null;
	    String directory = getMediaDirectory();
	    boolean done = true;
	    
	    // get the full path
	    path = directory + fileName;
	    return path;
	  }
	  
	  /**
	   * Method to get the directory for the media
	   * @return the media directory
	   */
	  public static String getMediaDirectory() 
	  {
	    String directory = null;
	    boolean done = false;
	    File dirFile = null;
	    
	    // try to find the images directory
	      try {
	        // get the URL for where we loaded this class 
	        Class currClass = Class.forName("FileChooser");
	        URL classURL = currClass.getResource("FileChooser.class");
	        URL fileURL = new URL(classURL,"../images/");
	        directory = fileURL.getPath();
	        directory = URLDecoder.decode(directory, "UTF-8");
	        dirFile = new File(directory);
	        if (dirFile.exists()) {
	          //setMediaPath(directory);
	          return directory;
	        }
	      } catch (Exception ex) {
	      }
	      
	      return directory;
	  }

}
