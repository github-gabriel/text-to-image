package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algorithms.Draw;
import main.TextToImageMain;

public class InputPanel extends JPanel implements ActionListener{

	Window window;

	Draw draw = new Draw();
	
	private static final Font LABEL_FONT = new Font("Bahnschrift", Font.BOLD, 80);
	private static final Font TEXTFIELD_FONT = new Font("Arial", Font.BOLD, 45);

	private JLabel inputText;
	private JTextArea inputArea;
	private JButton refresh;

	private int inputAreaWidth = 500, inputAreaHeight = 175;

	public InputPanel(Window window) {

		this.window = window;

		this.setPreferredSize(new Dimension(550, TextToImageMain.HEIGHT));
		this.setMinimumSize(new Dimension(550, TextToImageMain.HEIGHT));
		this.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;

		inputText = new JLabel("Input");
		inputText.setFont(LABEL_FONT);
		inputText.setForeground(Color.BLACK);
		this.add(inputText, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(30, 0, 30, 0);
		
		refresh = new JButton("Refresh");
		refresh.setFont(TEXTFIELD_FONT);
		refresh.setForeground(Color.BLACK);
		refresh.setBackground(Color.WHITE);
		refresh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		refresh.addActionListener(this);
		this.add(refresh, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		inputArea = new JTextArea();
		inputArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				new Thread(() -> draw.paint(inputArea.getText(), window.getOutputPanel())).start();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				new Thread(() -> draw.paint(inputArea.getText(), window.getOutputPanel())).start();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				new Thread(() -> draw.paint(inputArea.getText(), window.getOutputPanel())).start();
			}
		});
		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				new Thread(() -> draw.paint(inputArea.getText(), window.getOutputPanel())).start();
			}
		});
		inputArea.setFont(TEXTFIELD_FONT);
		inputArea.setForeground(Color.BLACK);
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		inputArea.setPreferredSize(new Dimension(inputAreaWidth, inputAreaHeight));
		this.add(inputArea, constraints);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == refresh) {
			new Thread(() -> draw.paint(inputArea.getText(), window.getOutputPanel())).start();
		}
		
	}

}
