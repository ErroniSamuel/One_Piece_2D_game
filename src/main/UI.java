package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entity.Entity;
import object.*;


public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font Obelix,MaruMonica,Original;
	BufferedImage full_heart,half_heart,null_heart;
	public boolean messageOn=false;
	public String message="";
	public boolean gameFinished=false;
	public String currentDialogue;
	public int commandNum=0;

	public UI(GamePanel gp) {
		this.gp=gp;

		try {
			InputStream is=getClass().getResourceAsStream("/font/Obelix.ttf");
			Obelix=Font.createFont(Font.TRUETYPE_FONT, is);

			is=getClass().getResourceAsStream("/font/MaruMonica.ttf");
			MaruMonica=Font.createFont(Font.TRUETYPE_FONT, is);

			is=getClass().getResourceAsStream("/font/Original.ttf");
			Original=Font.createFont(Font.TRUETYPE_FONT, is);
		}catch(FontFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//create hud object
		Entity heart=new OBJ_Heart(gp);
		full_heart=heart.image;
		half_heart=heart.image2;
		null_heart=heart.image3;
	}
	
	public void showMessage(String text) {
		message=text;
		messageOn=true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2=g2;
		
		g2.setFont(MaruMonica);
		g2.setColor(Color.white);
		
		//titleState
		if(gp.gameState==gp.titleState) {
			drawTitleScreen();
		}
		
		//Playstate
		if(gp.gameState==gp.playState) {
			drawPlayerLife();
		}
		
		//pausestate
		if(gp.gameState==gp.pauseState) {
		drawPlayerLife();
		drawPauseScreen();	
		}
		//dialogue state
		if(gp.gameState==gp.dialogueState) {
			drawDialogueScreen();
		}
	
	}
	public void drawTitleScreen() {
		g2.setColor(Color.red);
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		g2.setFont(Original);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,100F));
		String text="One Piece Adventure";
		int x=getXforCenteredText(text);
		int y=gp.tileSize*2;
		
		// shadow
		g2.setColor(Color.black);
		g2.drawString(text,x+5,y+5);
		
		//main
		g2.setColor(Color.white);
		g2.drawString(text,x,y);
		
		//luffy
		
		x=gp.screenWidth/2;
		y+=gp.tileSize*2;
		g2.drawImage(gp.player.down1, x-40, y-25, gp.tileSize*2, gp.tileSize*2,null);
		
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text="New Game";
		x=getXforCenteredText(text);
		y+=gp.tileSize*3;
		g2.drawString(text, x, y);
		if(commandNum==0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text="Load Game";
		x=getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum==1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text="Quit";
		x=getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum==2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text="Paused";
		int x=getXforCenteredText(text);
		int y=gp.screenHeight/2;
		g2.drawString(text, x, y);
		
	}
	public void drawPlayerLife() {
		int x=gp.tileSize/2;
		int y=gp.tileSize/3;
		int i=0;
		
		//blank heart
		while(i<gp.player.maxLife/2) {
			g2.drawImage(null_heart, x, y,null);
			i++;
			x+=gp.tileSize;
		}
		 x=gp.tileSize/2;
	     y=gp.tileSize/3;
		 i=0;
		 
		 //draw current life
		 while(i<gp.player.life) {
			 g2.drawImage(half_heart, x, y,null);
			 i++;
			 if(i<gp.player.life) {
				 g2.drawImage(full_heart,x,y,null);
			 }
			 i++;
			 x+=gp.tileSize;
		 }
	}
	public void drawDialogueScreen() {
		int x=gp.tileSize*2;
		int y=gp.tileSize/3;
		int width=gp.screenWidth-(gp.tileSize*4);
		int height=gp.tileSize*4;
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		x+=gp.tileSize;
		y+=gp.tileSize+20;
		
		for(String line:currentDialogue.split("\n")) {
		g2.drawString(line, x, y-10);
		y+=40;
		}
	}
	public void drawSubWindow(int x,int y,int width,int height) {
		Color c=new Color(0,0,0,220);
		g2.setColor(c);
		
		g2.fillRoundRect(x, y, width, height, 35,35);
		
		c=new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
	}
	public int getXforCenteredText(String text) {
		int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x=gp.screenWidth/2-length/2;
		return x;
	}
}
