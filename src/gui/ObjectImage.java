package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectImage {

	public String name;
	public BufferedImage image;
	
	public ObjectImage(String name) {
		this.name = name;
	}

}
