import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class rInput extends JPanel{
	Cube cube;
	private JButton toClock, leClock, baClock, riClock, frClock, boClock;
	private JButton shuffle, facesIncorrect;
	private JLabel fiLabel;
	private int fIncorrect;
	private rubiksAgent ra;
	//private int solutionPath[];
	
	public rInput(Cube c, rubiksAgent r){
		cube = c;
		ra = r;
		JLabel l1 = new JLabel("Perform rotation of: ");
		fiLabel = new JLabel("Currently the number of faces in the incorrect position is: " + fIncorrect);
		fiLabel.setLocation(50, 150);
		toClock = new JButton("Top Face Clockwise");
		leClock = new JButton("Left Face Clockwise");
		baClock = new JButton("Back Face Clockwise");
		riClock = new JButton("Right Face Clockwise");
		frClock = new JButton("Front Face Clockwise");
		boClock = new JButton("Bottom Face Clockwise");
		shuffle = new JButton("SHUFFLE CUBE");
		facesIncorrect = new JButton("Faces Incorrect");
		
		ButtonListener listener = new ButtonListener();
		toClock.addActionListener(listener);
		leClock.addActionListener(listener);
		baClock.addActionListener(listener);
		riClock.addActionListener(listener);
		frClock.addActionListener(listener);
		boClock.addActionListener(listener);
		shuffle.addActionListener(listener);
		facesIncorrect.addActionListener(listener);
		
		add(l1);
		add(toClock);
		add(leClock);
		add(baClock);
		add(riClock);
		add(frClock);
		add(boClock);
		add(shuffle);
		add(facesIncorrect);
		
		setBackground(Color.gray);
		setPreferredSize(new Dimension(400, 350));
		
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		page.drawString(fiLabel.getText(), 50, 200);
		
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == toClock)
				cube.topClock();
			else if(event.getSource() == leClock)
				cube.leftClock();
			else if(event.getSource() == baClock)
				cube.backClock();
			else if(event.getSource() == riClock)
				cube.rightClock();
			else if(event.getSource() == frClock)
				cube.frontClock();
			else if(event.getSource() == boClock)
				cube.bottomClock();
			else if(event.getSource() == shuffle)
				cube.shuffle(12);
			else if(event.getSource() == facesIncorrect){}
			fIncorrect = cube.facesIncorrect(cube);
			fiLabel.setText("Currently the number of faces in the incorrect position is: " + fIncorrect);
			//solutionPath = ra.getSolution(cube);
			repaint();
		}
	}
}
