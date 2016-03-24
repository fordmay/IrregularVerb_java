package irregular;

import irregular.graphic.EngineProgram;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Play {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				EngineProgram engProg = new EngineProgram();
				engProg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				engProg.setVisible(true);
			}
		});
	}
}
