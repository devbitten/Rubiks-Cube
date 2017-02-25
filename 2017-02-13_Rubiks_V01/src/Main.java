import javax.swing.*;

public class Main {
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Still without an AI :(");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Cube cube = new Cube();
		rubiksAgent ra = new rubiksAgent(cube);
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Manual Input", new rInput(cube, ra));
		tp.addTab("3D Projection", new rProjection(cube));
		tp.addTab("Layout", new rLayout(cube));
		//JPanel gPanel = new JPanel();

		frame.getContentPane().add(tp);
		frame.pack();
		frame.setVisible(true);
	}
}
