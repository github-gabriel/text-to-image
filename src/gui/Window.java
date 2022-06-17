package gui;

import java.awt.BorderLayout;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.TextToImageMain;

public class Window extends JFrame {

	InputPanel inputPanel;
	OutputPanel outputPanel;

	public Window(int width, int height) {
		
		inputPanel = new InputPanel(this);
		outputPanel = new OutputPanel();
	
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(TextToImageMain.WIDTH, TextToImageMain.HEIGHT));
		this.add(inputPanel, BorderLayout.WEST);
		this.add(outputPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

	}

	public InputPanel getInputPanel() {
		return inputPanel;
	}

	public OutputPanel getOutputPanel() {
		return outputPanel;
	}

}
