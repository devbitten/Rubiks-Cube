import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class rLayout extends JPanel{
	Cube cube;
	public rLayout(Cube c){
		cube = c;
		setBackground(Color.gray);
		setPreferredSize(new Dimension(400, 600));
		
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		page.setColor(Color.black);
		//The variables ox & oy mean 'offset x/y'
		//The variable fSize is the width of squares for front
		int fSize = 35;
		int padding = fSize + 10;
		drawFace(page, padding+fSize*4, padding, fSize, 5);
		drawFace(page, padding, padding+fSize*4, fSize, 4);
		drawFace(page, padding+fSize*4, padding+fSize*4, fSize, 1);
		drawFace(page, padding+fSize*4*2, padding+fSize*4, fSize, 2);
		drawFace(page, padding+fSize*4, padding+fSize*4*2, fSize, 0);
		drawFace(page, padding+fSize*4, padding+fSize*4*3, fSize, 3);/*
		for(int ox = 0; ox < 3; ox++){
			for(int oy = 0; oy < 3; oy++)
				page.drawRect( (50+(ox*fSize)),  (50+(oy*fSize)), fSize, fSize);
		}*/
		//page.drawString("FRONT", (fSize*3+50)/2, fSize*3+75);
		
		//page.drawString( cube.faceColor(1, 2, 3), (fSize*3+50)/2, fSize*3+90);
	}

	private void drawFace(Graphics page, int upX, int upY, int fSize, int faceNum) {
		//Filling the rectangles
		for(int ox = 0; ox < 3; ox++){
			for(int oy = 0; oy < 3; oy++){
				colorSelect(page, ox, oy, faceNum);
				page.fillRect(upX+fSize*ox, upY+fSize*oy, fSize, fSize);
			}
		}
		//Grid and label
		page.setColor(Color.black);
		for(int ox = 0; ox < 3; ox++){
			for(int oy = 0; oy < 3; oy++)
				page.drawRect(upX+fSize*ox, upY+fSize*oy, fSize, fSize);
		}
		if(faceNum == 5)
			page.drawString("TOP", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		else if(faceNum == 4)
			page.drawString("LEFT", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		else if(faceNum == 3)//THIS ONE LOOKS WEIRD, BECAUSE OF ODD DESIGN CHOICE (NO GOING 'BACK'!!)
			page.drawString("BACK ", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		else if(faceNum == 2)
			page.drawString("RIGHT", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		else if(faceNum == 1)
			page.drawString("FRONT", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		else if(faceNum == 0)
			page.drawString("BOTTOM", upX+(int)(fSize*1.2), upY+(int)(fSize*(3.35)));
		
	}
	
	private void colorSelect(Graphics page, int x, int y, int f){
		String col = cube.faceColor(x, y, f);
		if(col.equals("RED"))
			page.setColor(Color.red);
		if(col.equals("GREEN"))
			page.setColor(Color.green);
		if(col.equals("WHITE"))
			page.setColor(Color.white);
		if(col.equals("BLUE"))
			page.setColor(Color.blue);
		if(col.equals("YELLOW"))
			page.setColor(Color.yellow);
		if(col.equals("ORANGE"))
			page.setColor(Color.magenta);
	}
}
