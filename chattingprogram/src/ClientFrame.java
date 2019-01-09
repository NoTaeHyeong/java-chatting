import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class ClientFrame extends Frame implements Runnable{
	Socket cSocket;
	TextArea ta;
	TextField tf;
	String sendMessage;
	
	public ClientFrame() {
		super("Client");
		
		setBounds(1000, 200, 600, 600);
		
		ta = new TextArea();
		ta.setFocusable(false);
		ta.setBackground(Color.LIGHT_GRAY);
		
		tf = new TextField();
		tf.requestFocus();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try{
					PrintWriter pw = new PrintWriter(cSocket.getOutputStream(), true);
					pw.println("상대방과의 연결이 끊어졌습니다.");
					pw.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(ta, "Center");
		add(tf, "South");
		setVisible(true);
	}
	
	@Override
	public void run() {
		try{
			cSocket = new Socket("192.168.0.14", 3333);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		sendMessage(); recieveMessage();
	}
	
	public void sendMessage() {
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sendMessage = ae.getActionCommand();
				try{
					PrintWriter pw = new PrintWriter(cSocket.getOutputStream(), true);
					pw.println(sendMessage);
				} catch(IOException e) {
					e.printStackTrace();
				}
				
				ta.append(sendMessage + "\n");
				tf.getText(); tf.setText("");
			}
		});
	}
	
	public void recieveMessage() {
		String recieveMessage;
		try(BufferedReader br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()))){
			while( (recieveMessage = br.readLine()) != null ) {
				ta.append("상대방 : " + recieveMessage + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}









