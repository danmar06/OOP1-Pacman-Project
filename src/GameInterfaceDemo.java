import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

// Mock PacMan class for demonstration
class PacMan {
    private int x, y;
    private static final int BLOCK_SIZE = 50;
    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public void moveUp() { y -= BLOCK_SIZE; }
    public void moveDown() { y += BLOCK_SIZE; }
    public void moveLeft() { x -= BLOCK_SIZE; }
    public void moveRight() { x += BLOCK_SIZE; }
}

public class GameInterfaceDemo extends JFrame {
    private PacMan pacMan;
    private ArrayList<Point> obstacles;
    private ArrayList<Point> pellets;
    private JPanel gamePanel;
    private JLabel scoreLabel;

    public GameInterfaceDemo() {
        setTitle("Game Interface Demo");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize game elements
        pacMan = new PacMan(100, 100);
        obstacles = new ArrayList<>(Arrays.asList(
            new Point(200, 200), new Point(250, 300), new Point(400, 400)
        ));
        pellets = new ArrayList<>(Arrays.asList(
            new Point(150, 150), new Point(300, 200), new Point(100, 350)
        ));

        gamePanel = createGamePanel();
        add(gamePanel, BorderLayout.CENTER);
        add(createScorePanel(), BorderLayout.NORTH);
        add(createControlPanel(), BorderLayout.SOUTH);

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> pacMan.moveUp();
                    case KeyEvent.VK_DOWN -> pacMan.moveDown();
                    case KeyEvent.VK_LEFT -> pacMan.moveLeft();
                    case KeyEvent.VK_RIGHT -> pacMan.moveRight();
                }
                gamePanel.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        setVisible(true);
        requestFocusInWindow();
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                int blockSize = 50;
                // Draw grid
                g.setColor(Color.LIGHT_GRAY);
                for (int i = 0; i < getWidth(); i += blockSize) {
                    for (int j = 0; j < getHeight(); j += blockSize) {
                        g.drawRect(i, j, blockSize, blockSize);
                    }
                }
                // Draw Pac-Man centered in block
                g.setColor(Color.YELLOW);
                int pacmanSize = 30;
                int pacmanX = pacMan.getX() + (blockSize - pacmanSize) / 2;
                int pacmanY = pacMan.getY() + (blockSize - pacmanSize) / 2;
                g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 45, 270);
                // Draw obstacles
                g.setColor(Color.BLUE);
                for (Point obstacle : obstacles) {
                    g.fillRect(obstacle.x, obstacle.y, blockSize, blockSize);
                }
                // Draw pellets
                g.setColor(Color.WHITE);
                int pelletSize = 10;
                for (Point pellet : pellets) {
                    int pelletX = pellet.x + (blockSize - pelletSize) / 2;
                    int pelletY = pellet.y + (blockSize - pelletSize) / 2;
                    g.fillOval(pelletX, pelletY, pelletSize, pelletSize);
                }
            }
        };
        panel.setPreferredSize(new Dimension(500, 500));
        return panel;
    }

    private JPanel createScorePanel() {
        JPanel scorePanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel);
        scorePanel.setBackground(Color.GRAY);
        return scorePanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton resetButton = new JButton("Reset");
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.setBackground(Color.LIGHT_GRAY);
        return controlPanel;
    }

    public static void main(String[] args) {
        new GameInterfaceDemo();
    }
}
