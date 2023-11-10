import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClientLobby extends JFrame {
	private BufferedReader in;
	private BufferedWriter out;
	
	private ImageIcon bg = new ImageIcon("./LobbyImages/LobbyBackground.png"); //배경화면
	private int width = bg.getIconWidth();
	private int height = bg.getIconHeight()+30;
	
	public ClientLobby(String username, String ip_addr) {
		setTitle("크레이지아케이드 - 대기실");
		setLocation(0,0);
		setSize(width,height);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 배경
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setSize(width,height);
		backgroundLabel.setLocation(0,0);
		add(backgroundLabel);
		
		try {
			Socket socket = new Socket("localhost", 9999); // 클라이언트 소켓 생성
			// 클라이언트로부터의 입출력 스트림
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 클라이언트로의 출력 스트림
			sendMsgToServer(username+"이 대기실에 입장하였습니다.");
		} catch (IOException e) {
			System.out.println("서버 접속 실패 : 서버가 켜져있는지 다시 확인해 주세요!");
			setVisible(false);
		} 
	}
	
	private void sendMsgToServer(String msg) {
		try { // 문자열 전송
			out.write(msg+"\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
}
