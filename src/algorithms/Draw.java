package algorithms;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import gui.ObjectImage;
import gui.OutputPanel;

public class Draw {

	private final static String[] COLOR_TEXT = {"red", "blue", "green", "yellow", "pink", "black", "gray", "orange",
			"cyan"};
	private final static Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.BLACK,
			Color.GRAY, Color.ORANGE, Color.CYAN};
	private final static String[] COMMANDS = {"draw", "paint"};
	private final static String[] OBJECTS = {"square", "circle", "triangle", "rectangle"};
	private ObjectImage[] OBJECT_IMAGES;

	public Draw(){
		initImages();
	}

	private void initImages() {
		try {

			ObjectImage SQUARE_IMAGE = new ObjectImage("square");
			ObjectImage CIRCLE_IMAGE = new ObjectImage("circle");
			ObjectImage TRIANGLE_IMAGE = new ObjectImage("triangle");
			ObjectImage RECTANGLE_IMAGE = new ObjectImage("rectangle");

			SQUARE_IMAGE.image = ImageIO.read(new FileInputStream("src/res/" + SQUARE_IMAGE.name + ".png"));
			CIRCLE_IMAGE.image = ImageIO.read(new FileInputStream("src/res/" + CIRCLE_IMAGE.name + ".png"));
			TRIANGLE_IMAGE.image = ImageIO.read(new FileInputStream("src/res/" + TRIANGLE_IMAGE.name + ".png"));
			RECTANGLE_IMAGE.image = ImageIO.read(new FileInputStream("src/res/" + RECTANGLE_IMAGE.name + ".png"));

			OBJECT_IMAGES = new ObjectImage[]{SQUARE_IMAGE, CIRCLE_IMAGE, TRIANGLE_IMAGE, RECTANGLE_IMAGE};

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(String objectInfo, OutputPanel panel) {
		int number = -1;

		Graphics g = panel.getGraphics();

		Graphics2D g2D = (Graphics2D) g;

		panel.repaint();

		objectInfo = objectInfo.toLowerCase();

		String text = objectInfo;

		if(Stream.of(COMMANDS).anyMatch(objectInfo::startsWith)){
			String commands = returnString(objectInfo, COMMANDS);
			objectInfo = objectInfo.replace(commands, "");
			text = text.replace(commands, "");
			if(Stream.of(OBJECTS).anyMatch(objectInfo::contains)){
				String object = returnString(objectInfo, OBJECTS);
				objectInfo = objectInfo.replace(object, "");
				if(objectInfo.matches(".*\\d.*")){
					String numberString = objectInfo.replaceAll("[^0-9]", "");
					try{
						number = Integer.parseInt(numberString);
					}catch (NumberFormatException e) {
						number = -1;
					}
				}
				if(isDataValid(text)){
					Color currentColor;
					if(Stream.of(COLOR_TEXT).anyMatch(objectInfo::contains)){
						String color = returnString(objectInfo, COLOR_TEXT);
						currentColor = stringToColor(color);
					}else{
						currentColor = Color.BLACK;
					}
					int x = 0;
					int y = 0;
					int width = 100;
					int height = 100;
					int j = 0;
					BufferedImage currentObject = dye(resize(OBJECT_IMAGES[stringToIndex(object)].image, width, height), currentColor);
					if(number != -1){
						for(int i = 0;i < number;i++){
							j++;
							if(j * width >= panel.getWidth() - width){
								j = 0;
								x = 0;
								y += height;
							}else{
								x = j * width;
							}
							g2D.drawImage(currentObject, null, x, y);
						}
					}else{
						g2D.drawImage(currentObject, null, 0, 0);
					}
				}
			}
		}
	}

	private boolean isDataValid(String data){
		boolean containsNum = false;
		boolean containsColor = false;
		String[] dataArray = data.split(" ");
		dataArray = removeAtIndex(dataArray, 0);
		if(data.matches(".*\\d.*")){
			containsNum = true;
		}
		if(	Stream.of(COLOR_TEXT).anyMatch(data::contains)){
			containsColor = true;
		}
		if(containsNum && containsColor && dataArray[0].matches(".*\\d.*") && Stream.of(COLOR_TEXT).anyMatch(dataArray[1]::contains) && Stream.of(OBJECTS).anyMatch(dataArray[2]::contains)){
			return true;
		}else if(containsNum && !containsColor && dataArray[0].matches(".*\\d.*") && Stream.of(OBJECTS).anyMatch(dataArray[1]::contains)){
			return true;
		}else if(!containsNum && containsColor && Stream.of(COLOR_TEXT).anyMatch(dataArray[0]::contains) && Stream.of(OBJECTS).anyMatch(dataArray[1]::contains)){
			return true;
		}
		return false;
	}

	private String[] removeAtIndex(String[] array, int index){
		// Remove Part of Array at Index
		String[] newArray = new String[array.length - 1];
		for(int i = 0;i < array.length;i++){
			if(i < index){
				newArray[i] = array[i];
			}else if(i > index){
				newArray[i - 1] = array[i];
			}
		}
		return newArray;
	}

	private Color stringToColor(String color){
		switch(color){
			case "red" ->{
				return Color.RED;
			}
			case "blue" ->{
				return Color.BLUE;
			}
			case "green" ->{
				return Color.GREEN;
			}
			case "yellow" ->{
				return Color.YELLOW;
			}
			case "pink" ->{
				return Color.PINK;
			}
			case "black" ->{
				return Color.BLACK;
			}
			case "gray" ->{
				return Color.GRAY;
			}
			case "orange" ->{
				return Color.ORANGE;
			}
			case "cyan" ->{
				return Color.CYAN;
			}
		}
		return null;
	}

	private int stringToIndex(String object){
		switch (object){
			case "square" -> {
				return 0;
			}
			case "circle" -> {
				return 1;
			}
			case "triangle" ->{
				return 2;
			}
			case "rectangle" ->{
				return 3;
			}
		}
		return 0;
	}

	private String returnString(String s, String[] array){
		for(int i = 0;i < array.length;i++){
			if(s.contains(array[i])){
				return array[i];
			}
		}
		return null;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage image = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return image;
	}

	// https://stackoverflow.com/questions/21382966/colorize-a-picture-in-java
	private BufferedImage dye(BufferedImage image, Color color) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}

	private boolean stringEndsWithElement(String s, String[] extn) {
		return Arrays.stream(extn).anyMatch(entry -> s.endsWith(entry));
	}

	private boolean stringContainsItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).anyMatch(inputStr::contains);
	}

	private int indexOfString(String[] items, String inputString) {
		return Arrays.asList(items).indexOf(inputString);
	}

}