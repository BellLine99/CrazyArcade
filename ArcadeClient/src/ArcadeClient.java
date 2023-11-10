import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ArcadeClient extends JFrame{
		
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	
	JScrollPane scrollPane;
	
	private ImageIcon bg = new ImageIcon("./LoginImages/LoginBackground.png");//배경화면
	
	private int width = 600;
	private int height = 600;
	
	public static void main(String[] args) {
		ArcadeClient clientLogin = new ArcadeClient();
	}
	
	public ArcadeClient() {  // 생성자
		setTitle("크레이지아케이드 - 로그인");
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
		
		// 유저 이름 라벨
		JLabel userNameLabel = new JLabel("User Name");
		userNameLabel.setSize(100,20);
		userNameLabel.setLocation(200,500);
		add(userNameLabel);
		
		// 유저 이름 텍스트필드
		JTextField userNameTextField = new JTextField();
		userNameTextField.setSize(100,20);
		userNameTextField.setLocation(250,500);
		userNameTextField.setColumns(10);
		add(userNameTextField);
		
		// 아이디 라벨
		JLabel idLabel = new JLabel("ID");
		idLabel.setSize(100,20);
		idLabel.setLocation(200,550);
		add(idLabel);
		
		// 유저 이름 텍스트필드
		JTextField idLabelTextField = new JTextField();
		idLabelTextField.setSize(100,20);
		idLabelTextField.setLocation(250,550);
		idLabelTextField.setColumns(10);
		add(idLabelTextField);
		
		
		//시작버튼
		JButton btnConnect = new JButton("ㅋ");
		btnConnect.setSize(300,300);
		btnConnect.setLocation(0,0);
		add(btnConnect);
		btnConnect.addActionListener(new ActionListener(){ // 내부클래스로 액션 이벤트 처리 클래스
			public void actionPerformed(ActionEvent e) {
				String username = userNameTextField.getText().trim();
				String ip_addr = idLabelTextField.getText().trim();
				
				ClientLobby clientLobby = new ClientLobby(username, ip_addr);
				setVisible(false);
			}
		});
	}
}
