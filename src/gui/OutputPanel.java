package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.TextToImageMain;

public class OutputPanel extends JPanel {

	public OutputPanel() {

		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		this.setPreferredSize(new Dimension(500, TextToImageMain.HEIGHT));
		this.setMinimumSize(new Dimension(500, TextToImageMain.HEIGHT));

	}

}
