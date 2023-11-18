import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.stream.events.Characters;

import Characters.CharacterFactory;
import Characters.GameCharacter;

public class ClientGamePlay extends JFrame implements KeyListener {
	
	private ImageIcon bg = new ImageIcon("./GamePlayImages/bg.png"); //배경화면
	
	private ImageIcon block1 = new ImageIcon("./GamePlayImages/tiles/block1.png");
	private ImageIcon block2 = new ImageIcon("./GamePlayImages/tiles/block2.png");
	private ImageIcon block3 = new ImageIcon("./GamePlayImages/tiles/block3");
	private ImageIcon block4 = new ImageIcon("./GamePlayImages/tiles/block4");
	private ImageIcon block5 = new ImageIcon("./GamePlayImages/tiles/block5");
	private ImageIcon block6 = new ImageIcon("./GamePlayImages/tiles/block6");
	private ImageIcon block7 = new ImageIcon("./GamePlayImages/tiles/block7");
	private ImageIcon block8 = new ImageIcon("./GamePlayImages/tiles/block8");
	private ImageIcon block9 = new ImageIcon("./GamePlayImages/tiles/block9");
	private ImageIcon block10 = new ImageIcon("./GamePlayImages/tiles/block10");
	private ImageIcon block11 = new ImageIcon("./GamePlayImages/tiles/block11");
	private ImageIcon block12 = new ImageIcon("./GamePlayImages/tiles/block12");
	
	private ImageIcon dizini = new ImageIcon("./GamePlayImages/Charactor/dizini_front.png");
	
	// 캐릭터 만든 후, 담는 배열
	private Vector<GameCharacter> characterVector = new Vector<GameCharacter>();
	
	
    int[][] MapArray = { //맵
		   {0, 3, 2, 3, 2, 8, 0, 0, 1, 8, 5, 2, 5, 0, 5}, 
		   {0, 4, 1, 4, 1, 7, 1, 0, 0, 7, 2, 3, 0, 0, 1}, 
		   {0, 0, 3, 2, 3, 8, 0, 1, 1, 8, 5, 1, 5, 1, 5},
		   {1, 4, 1, 4, 1, 7, 1, 0, 0, 7, 3, 2, 3, 2, 3},
		   {2, 3, 2, 3, 2, 8, 0, 0, 1, 8, 5, 1, 5, 1, 5},
		   {3, 4, 3, 4, 3, 7, 1, 1, 0, 0, 2, 3, 2, 3, 2},
		   {7, 8, 7, 8, 7, 8, 0, 0, 1, 8, 7, 8, 7, 8, 7},
		   {2, 3, 2, 3, 2, 0, 1, 0, 0, 7, 2, 4, 2, 4, 2},
		   {6, 1, 6, 1, 6, 8, 0, 1, 1, 8, 3, 2, 3, 2, 3},
		   {3, 2, 3, 2, 3, 7, 1, 0, 0, 7, 1, 4, 1, 4, 1},
		   {6, 0, 6, 1, 6, 8, 0, 0, 1, 8, 2, 3, 2, 3, 0},
		   {0, 0, 2, 3, 2, 7, 1, 1, 0, 7, 1, 4, 1, 4, 0},
		   {6, 0, 6, 2, 6, 8, 0, 0, 1, 8, 3, 2, 3, 0, 0},
	};
    
    // 캐릭터
    private JLabel characterLabel;


	private GameCharacter clientCharacter;
    
    
	public ClientGamePlay() {
		setTitle("크레이지아케이드 - 게임방");
		setLocation(0,0);
		setSize(bg.getIconWidth(),bg.getIconHeight());
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 키 입력 가능하게끔
        addKeyListener(this);
        setFocusable(true);
        
		// 배경
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setSize(bg.getIconWidth(),bg.getIconHeight());
		backgroundLabel.setLocation(0,0);
		add(backgroundLabel);
		
		
		// 캐릭터 생성
	    int x = 50;
	    int y = 50;
		String userName = ClientLobby.instance.username;
		int clientId = ClientLobby.instance.clientId;
		BufferedWriter out = ClientLobby.instance.out;
		GameCharacter c = CharacterFactory.getCharacter("Dao",x,y,clientId,userName,out);
		clientCharacter = c;
		characterVector.add(c);
		// 캐릭터 라벨
        characterLabel = new JLabel(dizini);
        characterLabel.setSize(c.x,c.y);
        characterLabel.setVisible(true);
        backgroundLabel.add(characterLabel);
        
        // 스레드를 시작하여 캐릭터를 움직이게 한다.
        Thread movementThread = new Thread(() -> {
            while (true) {
                characterLabel.setLocation(clientCharacter.x, clientCharacter.y);
                try {
                    Thread.sleep(50);  // 50밀리초마다 쉬면서 이동
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        movementThread.start();
        
        // 타일 및 블럭 배치
        for(int row=0;x<15;x++) {
        	for(int column=0;y<13;y++) {
        		JLabel block = new JLabel();
        		block.setLocation(block1.getIconWidth()*row,block1.getIconHeight()*column);
        		block.setSize(block1.getIconWidth(),block1.getIconHeight());
        		if(MapArray[column][row]==0) block.setIcon(block1);
        		else block.setIcon(block2);
        		backgroundLabel.add(block);
        	}
        }
        
        
        
		repaint();
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
        // keyPressed는 키를 눌렀을 때 호출됩니다.
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                clientCharacter.y -= 10;
                break;
            case KeyEvent.VK_DOWN:
            	clientCharacter.y += 10;
                break;
            case KeyEvent.VK_LEFT:
            	clientCharacter.x -= 10;
                break;
            case KeyEvent.VK_RIGHT:
            	clientCharacter.x += 10;
                break;
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
