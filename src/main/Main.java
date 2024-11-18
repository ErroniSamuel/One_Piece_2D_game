package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) {
JFrame window=new JFrame();
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
window.setTitle("One Piece");
window.setResizable(false);

GamePanel gp=new GamePanel();
window.add(gp);
window.pack();

window.setLocationRelativeTo(null);
window.setVisible(true);

gp.setupObj();
gp.start();

	}
}
