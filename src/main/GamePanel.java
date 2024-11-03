package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	final int origTileSize=16;
	final int scale=3;
	public final int FPS=30;
	public final int tileSize=origTileSize*scale; //48x48
	public final int maxScreenCol=16;
	public final int maxScreenRow=12;
	public int screenWidth=tileSize*maxScreenCol;
	public int screenHeight=tileSize*maxScreenRow;
	
	
	public final int maxWorldCol=50;
	public final int maxWorldRow=50;
	
	
	
	TileManager tileM=new TileManager(this);
	KeyHandler keyH=new KeyHandler();
	Sound sound=new Sound();
	Sound se=new Sound();
	public CollisionCheck checker=new CollisionCheck(this);
	public AssetSetter as=new AssetSetter(this);
	Thread game;
	
	
	public Player player=new Player(this,keyH);
	public SuperObject obj[]=new SuperObject[10];
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.cyan);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupObj() {
		as.setObject();
		playMusic(0);
	}
	
	public void start() {
		game=new Thread(this);
		game.start();
		
		
	}
	@Override
	public void run() {
		double drawInitial=1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
//		long timer=0;
//		int drawcount=0;
		long currentTime;
	while(game!=null) {
		currentTime=System.nanoTime();
		delta+=(currentTime-lastTime)/drawInitial;
//		timer+=(currentTime-lastTime);
		lastTime=currentTime;
		if(delta>=1) {
		update();
		repaint();
		delta--;
//		drawcount++;
		}
		/*if(timer>=1000000000) {
			System.out.println("FPS is: "+drawcount);
			drawcount=0;
			timer=0;
		}*/
	}
	}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		tileM.draw(g2);
		
		for(int i=0;i<obj.length;i++) {
			if(obj[i]!=null) {
				obj[i].draw(g2,this);
			}
		}
		
		player.draw(g2);
		
		g2.dispose();
	}
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	public void stopMusic() {
		sound.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
