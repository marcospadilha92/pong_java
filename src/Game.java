import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {	
	
	/**
	 * padr�o de serializa��o de objeto Java
	 */
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 240;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	
	public Player player; 

	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public static void main(String[] args) {
		Game game = new Game();
		// Janela do jogo
		JFrame frame = new JFrame("Pong");
		// Proibir que o usu�rio possa redimensionar a janela
		frame.setResizable(false);
		// Fechar a janela em caso clicado no X
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE ));
		//a pr�pria classe criar� um Key Listener
		this.addKeyListener(this);
		// subtraindo -10 porque � a altura do jogador
		player = new Player(100, HEIGHT - 10);
	}
	
	public void tick() {
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		
		//renderiza o player
		player.render(g);
		
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		// mostra o jogador
		bs.show();
	}

	@Override
	public void run() {
		while(true){
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
