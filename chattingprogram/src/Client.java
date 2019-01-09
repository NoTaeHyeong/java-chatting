import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Frame implements Runnable{
	Socket c_socket;
	TextArea ta;
	TextField tf;
	String sendMessage;
	
	public Client() {
		super("Client");
		setBounds(900, 200, 500, 500);
		
		ta = new TextArea();
		ta.setFocusable(false);
		ta.setBackground(Color.LIGHT_GRAY);
		
		tf = new TextField();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					PrintWriter pw = new PrintWriter(c_socket.getOutputStream(), true);
					pw.println("상대방이 나갔습니다.");
					dispose();
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
		try {
			c_socket = new Socket("192.168.0.14", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendMessage(); recieveMessage();
	}
	
	public void sendMessage() {
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sendMessage = ae.getActionCommand();
				try {
					PrintWriter pw = new PrintWriter(c_socket.getOutputStream(), true);
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
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(c_socket.getInputStream()))) {
			while( (recieveMessage = br.readLine()) != null ) {
				if(recieveMessage.equals("상대방이 나갔습니다."))
					ta.append(recieveMessage);
				else
					ta.append("상대방 : " + recieveMessage + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}





