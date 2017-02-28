import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class rProjection extends JPanel{
	Cube cube;
	//Shuffle, Timed Step shuffle
	//AI STEP SOLVE, AI QUICK SOLVE
	private JButton shuffle, stepShuffle;
	private boolean st_on = false;	//Step Shuffle Timer
	private boolean at_on = false;	//AI Step Solve Timer
	//Timer is associated with the step-solving of the AI
	private EventListener el = new EventListener();
	private JButton solve, stepSolve;
	private int solnPath[];
	private int aiSolnIndex = 0;
	
	private rubiksAgent ra;
	Timer shTimer = new Timer(100, el);
	Timer aiTimer = new Timer(400, el);
	
	public rProjection(Cube c){
		cube = c;
		shuffle = new JButton("SHUFFLE");
		shuffle.addActionListener(el);
		add(shuffle);
		stepShuffle = new JButton("STEP SHUFFLE");
		stepShuffle.addActionListener(el);
		add(stepShuffle);
		solve = new JButton("SOLVE");
		solve.addActionListener(el);
		add(solve);
		stepSolve = new JButton("STEP SOLVE");
		stepSolve.addActionListener(el);
		add(stepSolve);
		setBackground(Color.gray);
		setPreferredSize(new Dimension(500, 400));
		
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		page.setColor(Color.darkGray);
		//The variable fSize is the width of squares for front
		//The padding is for additional cushion of left/top edges
		int fSize = 75;
		int padding = fSize + 40;
		//FILLING THE SQUARES
		fillFrontGrid(page, fSize, padding);
		fillTopGrid(page, fSize, padding);
		fillRightGrid(page, fSize, padding);
		//DRAWING THE GRID
		page.setColor(Color.darkGray);
		drawFrontGrid(page, fSize, padding);
		drawTopGrid(page, fSize, padding);
		drawRightGrid(page, fSize, padding);
		boldOuterLine(page, fSize, padding);

		
		//page.drawString("FRONT", (fSize*3+50)/2, fSize*3+75);
	}
	
	//Methods for drawing each of the faces of the cube
	private void drawFrontGrid(Graphics page, int fSize, int pad){
		//The variables ox & oy mean 'offset x/y'
		for(int ox = 0; ox < 3; ox++){
			for(int oy = 0; oy < 3; oy++)
				page.drawRect( (pad + ox*fSize),  (pad + oy*fSize), fSize, fSize);
		}
	}
	private void drawTopGrid(Graphics page, int fSize, int pad){
		//For the oblique lines
		for(int ox = 0; ox < 4; ox++){
			page.drawLine( (pad + ox*fSize),  pad,
					(int)(pad + (ox+1.5)*fSize), (int)(pad - (.75)*fSize));
		}
		//For the horizontal lines
		for(int oy = 0; oy < 4; oy++){
			page.drawLine( (int)(pad + .5*oy*fSize),  (int)(pad - (.25*oy*fSize)),
					(int)(pad + (.5*oy+3)*fSize), (int)(pad - (.25*oy*fSize)));
		}
		
	}
	private void drawRightGrid(Graphics page, int fSize, int pad){
		//For the oblique lines
		for(int oy = 0; oy < 4; oy++){
			page.drawLine( (pad + 3*fSize),  pad + oy*fSize,
					(int)(pad + 4.5*fSize), (int)(pad + (oy-.75)*fSize));
		}
		//For the vertical lines
		for(int ox = 0; ox < 4; ox++){
			page.drawLine( (int)(pad + (.5*ox+3)*fSize),  (int)(pad - (.25*ox*fSize)),
					(int)(pad + (.5*ox+3)*fSize), (int)(pad + ((3-.25*ox)*fSize)));
		}
	}
	
	//Methods for filling out the squares of the layout cube
	private void fillFrontGrid(Graphics page, int fSize, int pad){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				colorSelect(page, i, j, 1);
				page.fillRect( (pad + i*fSize), (pad + j*fSize), fSize, fSize);
			}
		}
	}
	private void fillRightGrid(Graphics page, int fSize, int pad){
		int x[] = { 0, 0, 0, 0};
		int y[] = { 0, 0, 0, 0};
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				colorSelect(page, j, i, 2);
				//REFERENCE: Corners go from:
				//up-left, bot-left, bot-right, top-right
				x[0] = (int)(pad + (3+(.5*j))*fSize);
				x[1] = x[0];
				x[2] = x[0] + (int)(.5*fSize+.5);
				x[3] = x[2];
				y[0] = (pad + i*fSize) - (int)(.25*j*fSize+.9);
				y[1] = y[0] + fSize;
				y[2] = y[0] + (int)(.75*fSize+.9);
				y[3] = y[0] - (int)(.25*fSize+.9);
				page.fillPolygon(x, y, 4);
			}
		}
	}
	private void fillTopGrid(Graphics page, int fSize, int pad){
		int x[] = { 0, 0, 0, 0};
		int y[] = { 0, 0, 0, 0};
		double tx = 3;
		//These variables go from bot-left -> top-right
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				//REFERENCE: Corners go from:
				//up-left, bot-left, bot-right, top-right
				colorSelect(page, j, i, 5);
				x[0] = pad + j*fSize + (int)(tx*.5*fSize);
				x[1] = x[0] - (int)(.5*fSize) ;
				x[2] = x[1] + fSize;
				x[3] = x[0] + fSize;
				y[0] = pad - (int)((.25*(3-i))*fSize+.9);
				y[1] = y[0] + (int)(.25*fSize+.9);
				y[2] = y[1];
				y[3] = y[0];
				page.fillPolygon(x, y, 4);
			}
			tx--;
		}
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
	private void boldOuterLine(Graphics page, int fSize, int pad){ // ~~**~~ A E S T H E T I C ~~**~~
		//This deals with the black inner edge lines
		page.setColor(Color.black);
		page.drawLine(pad, pad, (pad + fSize*3), pad);
		page.drawLine((pad + fSize*3), pad, (pad + fSize*3), (pad + fSize*3));
		page.drawLine((pad + fSize*3), pad, (int)(pad + fSize*4.5), (int)(pad - fSize*.75));
		//This is the BOLD OUTLINE
		Graphics2D g2 = (Graphics2D) page;
		g2.setStroke(new BasicStroke( (fSize/20)));
		g2.drawLine(pad, pad, pad, (pad + 3*fSize));
		g2.drawLine(pad, (pad + 3*fSize), (pad + 3*fSize), (pad + 3*fSize));
		g2.drawLine((pad + 3*fSize), (pad + 3*fSize),
				(int)(pad + 4.5*fSize), (int)(pad + 2.25*fSize));
		g2.drawLine((int)(pad + 4.5*fSize), (int)(pad + 2.25*fSize),
				(int)(pad + 4.5*fSize), (int)(pad - .75*fSize));
		g2.drawLine((int)(pad + 4.5*fSize), (int)(pad - .75*fSize),
				(int)(pad + 1.5*fSize), (int)(pad - .75*fSize));
		g2.drawLine((int)(pad + 1.5*fSize), (int)(pad - .75*fSize), pad, pad);
	}
	
	//For the shuffle and solve buttons
	private class EventListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == shuffle)
				cube.shuffle(4);
			
			if(event.getSource() == stepShuffle){
				st_on = !st_on;
				if(st_on)
					shTimer.start();
				else
					shTimer.stop();
				cube.shuffle();
			}
			if(event.getSource() == solve){
				ra = new rubiksAgent(cube);
				System.out.println("mADE_________it --" + cube.faceColor(0, 0, 1));
				System.out.println("mADE_________it --" + ra);
				solnPath = ra.getSolution(cube);
				for(int i = 0; i < solnPath.length; i++){
					System.out.print(solnPath[i] + " ");
				}
				System.out.println("<-- rProjection");
				if(!at_on)
					aiTimer.start();
			}
			if(event.getSource() == stepSolve){
				if(!at_on)
					aiTimer.start();
				//else NOT QUITE READY TO TURN IT OFF :/
				//	aiTimer.stop();
			}//For handling the events of the Step Shuffle Timer
			if(event.getSource() == shTimer)
				cube.shuffle();
			//For handling the events of the AI Step Solve Timer
			if(event.getSource() == aiTimer){
				if(aiSolnIndex < solnPath.length){
					System.out.println("solnPath[" + aiSolnIndex + "]: " + solnPath[aiSolnIndex]);
					cube.performRotation(solnPath[aiSolnIndex]);
					aiSolnIndex++;
				}else{
					System.out.println("rProjection AI Step Solver has finished :)");
					aiSolnIndex = 0;
					aiTimer.stop();
				}
			}
			
			repaint();
		}
	}
}
