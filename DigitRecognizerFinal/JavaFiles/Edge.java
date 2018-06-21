import java.awt.Color;

/**
 * This is the class for all edgeDetection for digit recognizer. Given different pictures as inputs, 
 * the edge class can give different edges for the neural network to guess a number.
 * To make it easier to visualize, we will be using a neural net visual program for our final values.
 * 
 * @author Aditya Prasad and Robert Smithers
 * @date 06/01/18
 * @version 0.1
 *
 */
public class Edge {

	//The Picture object
	private Picture pic;
	public Picture pic2;
	public Picture picEdgeH;
	public Picture picEdgeV;

	//Pixels array
	Pixel[][] pixels;
	
	/**
	 * These are the filters used for edgeDetection.
	 * They can be used to determine where in the picture a rapid color shift occurs.
	 */
	private int[][] horizontalFilter = {
			{1,  1,  1},
			{0,  0,  0},
			{-1, -1, -1}
	};

	private int[][] verticalFilter = {
			{0, 1, -1},
			{0, 1, -1},
			{0, 1, -1}
	};


	public Edge() {

	}

	public Edge(String fileName) {
		setPicture(fileName);
		pixels = pic.getPixels2D();
	}

	/**
	 * Creates a new picture based on the input one. The new picture is resized and focuses on
	 * edge detection in order to classify where in our matrix the picture quickly shifts to another color.
	 * It uses a horizontal focus
	 */
	public void edgeDetectorHorizontal(String rgb) {
		if (rgb.equalsIgnoreCase("rgb")) picEdgeH = generateEdgePicRGB(horizontalFilter);
		else picEdgeH = generateEdgePicBW(horizontalFilter);
	}
	
	/**
	 * Creates a new picture based on the input one. The new picture is resized and focuses on
	 * edge detection in order to classify where in our matrix the picture quickly shifts to another color.
	 * It uses a vertical focus
	 */
	public void edgeDetectorVertical(String rgb) {
		if (rgb.equalsIgnoreCase("rgb")) picEdgeV = generateEdgePicBW(verticalFilter);
		else picEdgeV = generateEdgePicBW(verticalFilter);
	}
	
	public void combineEdgePics() {
		if (picEdgeH == null) edgeDetectorHorizontal("BW");		//Black and white horizontal edge pic generation
		if (picEdgeV == null) edgeDetectorVertical("BW");
		
		Pixel[][] pixelsH = picEdgeH.getPixels2D();
		Pixel[][] pixelsV = picEdgeV.getPixels2D();
		
		Picture combinedPics = new Picture(pixelsH.length, pixelsH[0].length);
		combinedPics.setAllPixelsToAColor(Color.white);
		
		Color[][] colors = new Color[pixelsH.length][pixelsH[0].length];
		
		for (int row = 0; row < pixelsH.length; row++) {
			for (int col = 0; col < pixelsH[0].length; col++) {
				int rgbVal = (int) (pixelsH[row][col].getAverage() + pixelsV[row][col].getAverage());
				if (rgbVal > 255) rgbVal = 255;
				colors[row][col] = new Color(rgbVal, rgbVal, rgbVal);
			}
		}
		System.out.println("colors : " +colors.length + " " + colors[0].length+"\n");
		System.out.println("combinedPics : " +pixelsH.length + " " + pixelsH[0].length+"\n");
		combinedPics.setPixelColors(combinedPics, colors);
		pic = combinedPics;
		
	}

	public int[][] generateEdgeColorRGB(int[][] filter, String rgb) {
		int color = 0;
		
		int newHeight = pixels.length - filter.length;			//Will make new picture up to the OG rows - filter rows because of matrix multiplication
		int newWidth = pixels[0].length - filter[0].length;		//Will make new picture up to the OG cols - filter cols because of matrix multiplication

		int[][] colorArr = new int[newHeight][newWidth];

		//Cycle through the pixels in the picture that the filter will fit
		for (int row = 0; row < newHeight; row++)
		{
			for (int col = 0; col < newWidth; col++)
			{
				//Cycle through the picture with the filter matrix to get a single new value
				int newVal = 0;
				for (int row2 = 0; row2 < filter.length; row2++) {
					for (int col2 = 0; col2 < filter[0].length; col2++) {
						//Matrix multiplication with filter to see color changes separated by RGB
						if (rgb.equalsIgnoreCase("r")) color = pixels[row+row2][col+col2].getRed();
						else if (rgb.equalsIgnoreCase("g")) color = pixels[row+row2][col+col2].getGreen();
						else color = pixels[row+row2][col+col2].getBlue();
						newVal += filter[row2][col2]*color;
						if (newVal > 255) newVal = 255;
						else if (newVal <= 0) newVal = 0;
					}
				}

				colorArr[row][col] = newVal;
			}
		}
		//Build new pic with the new rgb vals
		return colorArr;
	}
	
	public int[][] generateEdgeColorBW(int[][] filter) {
		int color = 0;
		
		int newHeight = pixels.length - filter.length;			//Will make new picture up to the OG rows - filter rows because of matrix multiplication
		int newWidth = pixels[0].length - filter[0].length;		//Will make new picture up to the OG cols - filter cols because of matrix multiplication

		int[][] colorArr = new int[newHeight][newWidth];

		//Cycle through the pixels in the picture that the filter will fit
		for (int row = 0; row < newHeight; row++)
		{
			for (int col = 0; col < newWidth; col++)
			{
				//Cycle through the picture with the filter matrix to get a single new value
				int newVal = 0;
				for (int row2 = 0; row2 < filter.length; row2++) {
					for (int col2 = 0; col2 < filter[0].length; col2++) {
						//Matrix multiplication with filter to see color changes separated by RGB
						color = (pixels[row+row2][col+col2].getRed() + pixels[row+row2][col+col2].getGreen() + pixels[row+row2][col+col2].getBlue())/3;
						newVal += filter[row2][col2]*color;
						if (newVal > 100) newVal = 255;
						else if (newVal <= 100) newVal = 0;
					}
				}

				colorArr[row][col] = newVal;
			}
		}
		//Build new pic with the new rgb vals
		return colorArr;
	}
	
	public Picture generateEdgePicBW(int[][] filter) {
		int[][] pixArr = generateEdgeColorBW(filter);
		Picture newPic = buildBWPic(pixArr);
		return newPic;
	}
	
	public Picture generateEdgePicRGB(int[][] filter) {
		int[][] arrRed = generateEdgeColorRGB(filter, "r");
		int[][] arrGreen = generateEdgeColorRGB(filter, "g");
		int[][] arrBlue = generateEdgeColorRGB(filter, "b");
		Picture newPic = buildRGBPic(arrRed, arrGreen, arrBlue);
		return newPic;
	}
	
	
	public Picture buildBWPic(int[][] rgb) {
		//To do, combine the 3 together.
		Picture combined = new Picture(rgb[0].length, rgb.length);
		combined.setAllPixelsToAColor(Color.white);
		Color[][] colors = new Color[rgb.length][rgb[0].length];
		for (int row = 0; row < rgb.length; row++) {
			for (int col = 0; col < rgb[0].length; col++) {;
				colors[row][col] = new Color(rgb[row][col], rgb[row][col], rgb[row][col]);
			}
		}
		combined.setPixelColors(combined, colors);
		return combined;
	}
	
	public Picture buildRGBPic(int[][] red, int[][] green, int[][] blue) {
		//To do, combine the 3 together.
		Picture combined = new Picture(red[0].length, red.length);
		combined.setAllPixelsToAColor(Color.white);
		Color[][] colors = new Color[red.length][red[0].length];
		for (int row = 0; row < red.length; row++) {
			for (int col = 0; col < red[0].length; col++) {
				Color color = new Color(red[row][col], green[row][col], blue[row][col]);
				colors[row][col] = color;	
			}
		}
		combined.setPixelColors(combined, colors);
		return combined;
		
	}

	public Vector[][] assignVectorArray() {
		Pixel[][] pixels = pic.getPixels2D();
		
		Vector[][] vectArr = new Vector[pixels.length][pixels[0].length];
				
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				double[] param = { pixels[row][col].getRed(), pixels[row][col].getGreen(), pixels[row][col].getBlue() };
				vectArr[row][col] = new Vector(param);
			}
		}
		
		return vectArr;
	}
	
	public Pixel[][] getPixels() {
		return pic.getPixels2D();
	}
	
	public void setPicture(String fileName) {
		pic = new Picture("pictures/"+fileName);
	}

	public void show() {
		pic.show();
	}

}
