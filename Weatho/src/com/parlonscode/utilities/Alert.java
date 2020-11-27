package com.parlonscode.utilities;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Alert {
	public static void error(Component parentComponent,String title, String message) {
		error(parentComponent,"Erreur",message);
	}
	
	
	public static void error(Component parentComponent, String message) {
		JOptionPane.showConfirmDialog(parentComponent,message,"Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	
	public static void error(Component parentComponent) {
		error(parentComponent,"Oooops une erreur est survenue, Veuillez SVP réssayer.");
	}
	
	
	public static void info(Component parentComponent,String title, String message) {
		JOptionPane.showConfirmDialog(parentComponent,message,title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void info(Component parentComponent , String message) {
		info(parentComponent,"Information", message);
	}
}
