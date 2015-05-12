package View;

import java.awt.Image;

public class ImageObject {

	private double xPosition;
	private double yPosition;
	private Image image;
	
	public ImageObject (double xPosition, double yPosition, Image image) {
		
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.image = image;
	}
	
	public double getXPosition() {
		return xPosition;
	}
	public double getYPosition() {
		return yPosition;
	}
	public double getImageWidth() {
		return image.getWidth(null);
	}
	public double getImageHeight() {
		return image.getHeight(null);
	}
}
