package net.cme.engine;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DebugFrame extends JPanel {
	private static final long serialVersionUID = 971617805264000728L;

	private DebugStats stats;

	private JFrame frame;

	private JLabel fps;
	private JLabel delta;
	private JLabel frameTime;

	public DebugFrame(DebugStats stats) {
		this.stats = stats;

		frame = new JFrame("Debug Stats");
		frame.setSize(200, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.add(this);

		fps = new JLabel("FPS: " + stats.fps);
		delta = new JLabel("Delta: " + stats.delta);
		frameTime = new JLabel("Frame Time: " + stats.frameTime);

		add(fps);
		add(delta);
		add(frameTime);
	}

	public void update() {
		fps.setText("FPS: " + stats.fps);
		delta.setText("Delta: " + stats.delta);
		frameTime.setText("Frame Time: " + stats.frameTime);
	}
}
