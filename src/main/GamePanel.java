package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
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
	
	
	public  int maxWorldCol;
	public  int maxWorldRow;
	
	
	
	TileManager tileM=new TileManager(this);
	public KeyHandler keyH=new KeyHandler(this);
	Sound sound=new Sound();
	Sound se=new Sound();
	public CollisionCheck checker=new CollisionCheck(this);
	public AssetSetter as=new AssetSetter(this);
	public UI ui=new UI(this);
	public EventHandler eHandler=new EventHandler(this);
	Thread game;
	
	
	public Player player=new Player(this,keyH);
	public Entity obj[]=new Entity[10];
	public Entity npcs[]=new Entity[10];
	public Entity monster[]=new Entity[20];
	ArrayList<Entity> entityList=new ArrayList<>();
	
	public int currNPC=-1;
	public boolean firstTime=true;
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
	public final int pauseState=2;
	public final int dialogueState=3;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(new Color(41,175,255));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupObj() {
		as.setObject();
		as.setNPC();
		as.setMonster();
		//playMusic(0);
		gameState=titleState;
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
		if(gameState==playState) {
		//player
			player.update();
		//NPC
		for(int i=0;i<npcs.length;i++) {
			if(npcs[i]!=null) {
				npcs[i].update();
			}
		}
		for(int i=0;i<monster.length;i++) {
			if(monster[i]!=null) {
				monster[i].update();
			}
		}
		}
		if(gameState==pauseState) {
			
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		//Title screen
		if(gameState==titleState) {
			ui.draw(g2);
		}
		//other
		else {
		
		tileM.draw(g2);
		entityList.add(player);
		for(int i=0;i<npcs.length;i++) {
			if(npcs[i]!=null) {
				entityList.add(npcs[i]);
			}
		}
		for(int i=0;i<obj.length;i++) {
			if(obj[i]!=null) {
				entityList.add(obj[i]);
			}
		}
		for(int i=0;i<monster.length;i++) {
			if(monster[i]!=null) {
				entityList.add(monster[i]);
			}
		}
		
		Collections.sort(entityList,new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				// TODO Auto-generated method stub
				int result=Integer.compare(e1.worldY, e2.worldY);
				return result;
			}
		});
		
		for(int i=0;i<entityList.size();i++) {
			entityList.get(i).draw(g2);
		}
		entityList.clear();
		
		ui.draw(g2);
		}
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
