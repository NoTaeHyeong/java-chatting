import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatFrame extends Frame{
	TextArea ta;
	TextField tf;
	
	public ChatFrame(String name) {
		super(name);
		
		ta = new TextArea();
		ta.setBackground(Color.LIGHT_GRAY);
		ta.setFocusable(false);
		
		tf = new TextField();
		tf.requestFocus();
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.append(e.getActionCommand() + "\n");
				tf.getText();
				tf.setText("");
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		add(ta, "Center");
		add(tf, "South");
		setVisible(true);
	}
}

class SendClient extends Thread {
	private Socket socket;
	
	@Override
	public void run() {
		try(PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}