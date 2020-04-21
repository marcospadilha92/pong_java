import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {	
	
	/**
	 * padrão de serialização de objeto Java
	 */
	private static final long serialVersionUID = 1L;
	
	// tamanho da tela
	public static int WIDTH = 160;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	
	// static por conta de ser acessado pela classe ball
	public static Player player; 

	// static por conta de ser acessado pela classe ball
	public static Enemy enemy;
	
	// static por conta de ser acessado pela classe enemy
	public static Ball ball;

	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public Game() {		
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE ));
		
		//a própria classe criará um Key Listener
		this.addKeyListener(this);
		
		// subtraindo -10 porque é a altura do jogador
		player = new Player(100, HEIGHT - 5);
		
		// zero porque estará na posição oposta eixo y zero
		enemy = new Enemy(100, 0);
		
		ball = new Ball(100, HEIGHT/2 - 1);
	}	
	
	public static void main(String[] args) {
		Game game = new Game();

		// Janela do jogo
		JFrame frame = new JFrame("Pong");
		
		// Proibir que o usuário possa redimensionar a janela
		frame.setResizable(false);
		
		// Fechar a janela em caso clicado no X
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();
	}	

	public void tick() {
		player.tick();
		enemy.tick();
		ball.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		
		// limpa o fundo para que o player não fique infinito
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//renderiza o player
		player.render(g);
		
		//renderiza o inimigo
		enemy.render(g);
		
		//renderiza a bolinha
		ball.render(g);
		
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
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
