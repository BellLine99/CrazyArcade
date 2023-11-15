import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientWaitingRoom extends JFrame {
	
	private ImageIcon BG = new ImageIcon("./WaitingRoomImages/waitRoomBG.png");
	private ImageIcon backUI = new ImageIcon("./WaitingRoomImages/waitRoomUI.png");
	private ImageIcon startButtonDisable = new ImageIcon("./WaitingRoomImages/startButtonDisable.png");
	private ImageIcon startButtonAble = new ImageIcon("./WaitingRoomImages/startButtonAble.png");
	private ImageIcon readyButton = new ImageIcon("./WaitingRoomImages/readyButton.png");
	
	private int roomNumber; // 현재 대기방 번호, 0~5
	private int clientUserIndex; // 현재 대기방에 입장한 유저의 번호, 0~7
	private int userCount; // 현재 대기방에 입장한 유저 수
	private String username;
	private String userId;
	private BufferedWriter out;
	
	private JButton startBtn;
	private JButton readyBtn;
	private JLabel bgUI;
	
	public Vector<ClientWaitingRoomUsers> waitUsers = new Vector<ClientWaitingRoomUsers>();
	
	public ClientWaitingRoom() {
		super();
		
		setTitle("크레이지아케이드 - 대기방");
		this.setSize(BG.getIconWidth(),BG.getIconHeight()+35);
		this.setLocation(0,0);
		setLayout(null);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 배경
		JLabel background = new JLabel(BG);
		background.setLocation(0,0);
		background.setSize(BG.getIconWidth(),BG.getIconHeight());
		background.setVisible(true);
		add(background);
		
		// 배경 위에 올라갈 UI
		bgUI = new JLabel(backUI);
		bgUI.setSize(backUI.getIconWidth(),backUI.getIconHeight());
		bgUI.setLocation(10,20);
		bgUI.setLayout(null);
		background.add(bgUI);
		
		// 시작 버튼 및 준비 버튼
		// 방장에게는 시작 버튼이, 다른 유저에겐 준비 버튼이 보인다
		startBtn = new JButton(startButtonDisable);
		StartFunc startFunc = new StartFunc();
		startBtn.addMouseListener(startFunc);
		startBtn.setSize(startButtonDisable.getIconWidth(),startButtonDisable.getIconHeight());
		startBtn.setLocation(400,100);
		
		readyBtn = new JButton(readyButton);
		ReadyFunc readyFunc = new ReadyFunc();
		readyBtn.addMouseListener(readyFunc);
		readyBtn.setSize(readyButton.getIconWidth(),readyButton.getIconHeight());
		readyBtn.setLocation(400,100);
		
		// 들어온 유저 목록 아이콘 및 벡터 저장
		for(int i=0;i<8;i++) {
			ClientWaitingRoomUsers user = new ClientWaitingRoomUsers(username,userId,roomNumber ,i);
			waitUsers.add(user);
			bgUI.add(user);
		}
		repaint();
	}
	
	// 유저가 대기방 오픈하면
	public void userOpenWaitingRoom(String username,String userId,int roomIndex,BufferedWriter out) {
		// 해당 클라이언트의 대기방 속성 받아오기
		this.username = username;
		this.userId = userId;
		this.out = out;
		
		// 새로고침 뒤 창을 보이게 한다
		represhWaitingRoom();
		// 유저가 몇 번째로 입장했는지 저장
		for(int i=0;i<waitUsers.size();i++) {
			if(!waitUsers.elementAt(i).isUserEntered) {
				clientUserIndex=i-1;
				break;
			}
		}
		
		// 방장의 경우 대기방 설정
		if(clientUserIndex==0) {
			bgUI.add(startBtn);
			startBtn.setVisible(true);
		}
		
		// 방장 아닌 경우 대기방 설정
		else {
			bgUI.add(readyBtn);
			readyBtn.setVisible(true);
		}
			
		setVisible(true);
	}
	
	// 대기방 새로고침, 유저가 대기방 오픈하거나 나가거나 레디버튼 누를때 호출
	public void represhWaitingRoom() {
		for(int i=0;i<waitUsers.size();i++) {
			ClientWaitingRoomUsers target = waitUsers.elementAt(i);
			if(!target.userName.equals("-")) target.userEntered(target.userName,target.userID,i);
			if(target.isReady) target.userReady(true);
		}
		repaint();
	}
	
	public void setRoomIndex(int idx) {roomNumber = idx;}
	
	// 어느 waitRoomUser가 버튼을 눌렀는지 체크해야 되서, 여기서 시작/준비 버튼 이벤트 정의
	
	// 시작 버튼 클릭시 수행할 함수
	public class StartFunc extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
	}
	
	// 준비 버튼 클릭시 수행할 함수
	public class ReadyFunc extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				out.write("7/"+userId+"/"+username+"/"+roomNumber+"/"+clientUserIndex+"\n");
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
