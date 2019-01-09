import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerFrame extends Frame implements Runnable{
	Socket sSocket;
	TextArea ta;
	TextField tf;
	String sendMessage;
	
	public ServerFrame() {
		super("Server");
		
		setBounds(300, 200, 600, 600);
		
		ta = new TextArea();
		ta.setFocusable(false);
		ta.setBackground(Color.LIGHT_GRAY);
		
		tf = new TextField();
		tf.requestFocus();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try(PrintWriter pw = new PrintWriter(sSocket.getOutputStream(), true)) {
					pw.println("상대방과의 연결이 끊어졌습니다.");
					sSocket.close();
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
			ServerSocket serverSocket = new ServerSocket(3333);
			sSocket = serverSocket.accept();
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
					PrintWriter pw = new PrintWriter(sSocket.getOutputStream(), true);
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
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()))){
			while( (recieveMessage = br.readLine()) != null ) {
				ta.append("상대방 : " + recieveMessage + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}









