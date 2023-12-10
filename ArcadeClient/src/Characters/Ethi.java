package Characters;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Ethi extends GameCharacter {
	private ImageIcon front = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_front.png");
	private ImageIcon left = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_left.png");
	private ImageIcon right = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_right.png");
	private ImageIcon back = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_back.png");
	
	public Ethi(int clientId, int x, int y, String userName, BufferedWriter out) {
		super();
		this.setIcon(front);
		this.x = x;
		this.y = y;
		this.clientId = clientId;
		this.username = userName;
		this.out = out;
		
		// 캐릭터 이미지
		for(int i=0;i<6;i++) {
			frontMove[i] = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_front_"+i+".png");
			leftMove[i] = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_left_"+i+".png");
			rightMove[i] = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_right_"+i+".png");
			backMove[i] = new ImageIcon("./GamePlayImages/Charactor/Ethi/ethi_back_"+i+".png");
		}
	}

	@Override
	public void BlockDestroyMessage(int x, int y) {
		// TODO Auto-generated method stub
		try {
			out.write("100/_/_/"+x+"/"+y+"\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void died() {
		// TODO Auto-generated method stub
	}

}