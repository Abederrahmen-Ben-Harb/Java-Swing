package com.parlonscode;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainFrame mainFrame = new MainFrame("Weatho");
			mainFrame.setResizable(false);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setVisible(true);
		});

	}

}
