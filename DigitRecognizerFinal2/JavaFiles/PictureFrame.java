import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PictureFrame {

	/** Main window used as the frame */
	JFrame frame = new JFrame();

	/** ImageIcon used to display the picture in the label*/
	ImageIcon imageIcon = new ImageIcon();

	/** Label used to display the picture */
	private JLabel label = new JLabel(imageIcon);

	/** Digital Picture to display */
	private Picture picture;

	/**
	   * A constructor that takes a picture to display
	   * @param picture  the digital picture to display in the 
	   * picture frame
	   */
	  public PictureFrame(Picture picture)
	  {
	    // set the current object's picture to the passed in picture
	    this.picture = picture;
	    
	    // set up the frame
	    initFrame();
	  }
	  
	  /**
	   * A method to initialize the picture frame
	   */
	  private void initFrame()
	  {
	    
	    // set the image for the picture frame
	    updateImage();
	      
	    // add the label to the frame
	    frame.getContentPane().add(label);
	    
	    // pack the frame (set the size to as big as it needs to be)
	    frame.pack();
	    
	    // make the frame visible
	    frame.setVisible(true);
	  }
	
	/**
	 * A method to update the picture frame image with the image in 
	 * the picture and show it
	 */
	public void updateImageAndShowIt()
	{
		// first update the image
		updateImage();

		// now make sure it is shown
		frame.setVisible(true);
	}

	public void setVisible(boolean flag) 
	{ 
		frame.setVisible(flag);
	}

	public void updateImage()
	{
		// only do this if there is a picture
		if (picture != null)
		{
			// set the image for the image icon from the picture
			imageIcon.setImage(picture.getImage());
		}
	}
	

}
