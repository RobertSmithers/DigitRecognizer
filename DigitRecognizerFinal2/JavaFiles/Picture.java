import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture {

	private String fileName;
	private BufferedImage bufferedImage;
	private String extension;
	private String title;
	private PictureFrame pictureFrame;

	public Picture(String filename) {
		this.fileName = filename;
		load(filename);
	}

	/**
	 * A constructor that takes the width and height desired for a picture and
	 * creates a buffered image of that size.  This constructor doesn't 
	 * show the picture.  The pixels will all be white.
	 * @param width the desired width
	 * @param height the desired height
	 */
	public Picture(int width, int height)
	{
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		title = "None";
		fileName = "None";
		extension = "jpg";
		setAllPixelsToAColor(Color.white);
	}

	/**
	 * Method to read the contents of the picture from a filename  
	 * without throwing errors
	 * @param fileName the name of the file to write the picture to
	 * @return true if success else false
	 */
	public boolean load(String fileName)
	{
		try {
			loadOrFail(fileName);
			return true;

		} catch (Exception ex) {
			System.out.println("There was an error trying to open " + fileName);
			bufferedImage = new BufferedImage(600,200,
					BufferedImage.TYPE_INT_RGB);
			addMessage("Couldn't load " + fileName,5,100);
			return false;
		}

	}

	/**
	 * Method to load the picture from the passed file name
	 * @param fileName the file name to use to load the picture from
	 * @throws IOException if the picture isn't found
	 */
	public void loadOrFail(String fileName) throws IOException
	{
		// set the current picture's file name
		this.fileName = fileName;

		// set the extension
		int posDot = fileName.indexOf('.');
		if (posDot >= 0)
			this.extension = fileName.substring(posDot + 1);

		// if the current title is null use the file name
		if (title == null)
			title = fileName;

		File file = new File(this.fileName);

		if (!file.canRead()) 
		{
			// try adding the media path 
			file = new File(FileChooser.getMediaPath(this.fileName));
			if (!file.canRead())
			{
				throw new IOException(this.fileName +
						" could not be opened. Check that you specified the path");
			}
		}

		bufferedImage = ImageIO.read(file);
	}

	/**
	 * Method to draw a message as a string on the buffered image 
	 * @param message the message to draw on the buffered image
	 * @param xPos  the leftmost point of the string in x 
	 * @param yPos  the bottom of the string in y
	 */
	public void addMessage(String message, int xPos, int yPos)
	{
		// get a graphics context to use to draw on the buffered image
		Graphics2D graphics2d = bufferedImage.createGraphics();

		// set the color to white
		graphics2d.setPaint(Color.white);

		// set the font to Helvetica bold style and size 16
		graphics2d.setFont(new Font("Helvetica",Font.BOLD,16));

		// draw the message
		graphics2d.drawString(message,xPos,yPos);

	}

	/**
	 * Creates a new picture based on the input one. The new picture is resized and focuses on
	 * edge detection in order to classify where in our matrix the picture quickly shifts to another color.
	 * 
	 */
	public void edgeDetector2(int edgeDist) {
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; 
					col < pixels[0].length-1; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col+1];
				rightColor = rightPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > 
				edgeDist)
					leftPixel.setColor(Color.BLACK);
				else
					leftPixel.setColor(Color.WHITE);
			}
		}
	}

	public void setPixelColors(Picture pic, Color[][] color) {
		Pixel pixel = null;
		Pixel[][] pixels = pic.getPixels2D();
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				pixel = pixels[row][col];
				pixel.setColor(color[row][col]);
			}
		}
	}


	/**
	 * Method to return the pixel value as an int for the given x and y location
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @return the pixel value as an integer (alpha, red, green, blue)
	 */
	public int getBasicPixel(int x, int y)
	{
		return bufferedImage.getRGB(x,y);
	}

	/**
	 * Method to get a two-dimensional array of Pixels for this simple picture
	 * @return a two-dimensional array of Pixel objects in row-major order.
	 */
	public Pixel[][] getPixels2D()
	{
		int width = getWidth();
		int height = getHeight();
		Pixel[][] pixelArray = new Pixel[height][width];

		// loop through height rows from top to bottom
		for (int row = 0; row < height; row++) 
			for (int col = 0; col < width; col++) 
				pixelArray[row][col] = new Pixel(this,col,row);

		return pixelArray;
	}

	/**
	 * Method to get the width of the picture in pixels
	 * @return the width of the picture in pixels
	 */
	public int getWidth() { return bufferedImage.getWidth(); }

	/**
	 * Method to get the height of the picture in pixels
	 * @return  the height of the picture in pixels
	 */
	public int getHeight() { return bufferedImage.getHeight(); }

	/**
	 * Method to get the picture frame for the picture
	 * @return the picture frame associated with this picture
	 * (it may be null)
	 */

	/**
	 * Method to show the picture in a picture frame
	 */
	public void show()
	{
		// if there is a current picture frame then use it 
		if (pictureFrame != null)
			pictureFrame.updateImageAndShowIt();

		// else create a new picture frame with this picture 
		else
			pictureFrame = new PictureFrame(this);
	}

	/** 
	 * Method to set the value of a pixel in the picture from an int
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param rgb the new rgb value of the pixel (alpha, red, green, blue)
	 */     
	public void setBasicPixel(int x, int y, int rgb)
	{
		bufferedImage.setRGB(x,y,rgb);
	}

	/**
	 * Method to set the color in the picture to the passed color
	 * @param color the color to set to
	 */
	public void setAllPixelsToAColor(Color color)
	{
		// loop through all x
		for (int x = 0; x < this.getWidth(); x++)
		{
			// loop through all y
			for (int y = 0; y < this.getHeight(); y++)
			{
				getPixel(x,y).setColor(color);
			}
		}
	}

	/**
	 * Method to get a pixel object for the given x and y location
	 * @param x  the x location of the pixel in the picture
	 * @param y  the y location of the pixel in the picture
	 * @return a Pixel object for this location
	 */
	public Pixel getPixel(int x, int y)
	{
		// create the pixel object for this picture and the given x and y location
		Pixel pixel = new Pixel(this,x,y);
		return pixel;
	}

	/**
	 * Method to get an image from the picture
	 * @return  the buffered image since it is an image
	 */
	public Image getImage()
	{
		return bufferedImage;
	}



}
