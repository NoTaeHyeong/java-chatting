import java.io.IOException;
import java.net.*;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;


public class MulichatServer {
	HashMap clients;
	
	public MulichatServer() {
		clients = new HashMap();
		
	}
	
	//���� ����, Ŭ���̾�Ʈ ���� ��� 
	public void start() {
		ServerSocket server = null;
		Socket socket = null;
		
		// 1.���� ���� 
		try {
			server = new ServerSocket(7777);
			System.out.println("���� ����!");
			
			while(true) {
			
			socket = server.accept();
			System.out.println(socket.getInetAddress() + " ���� ���� ");
			
			// ���� Ŭ���� ����
			ServerReceiver thread = new ServerReceiver(socket);
			thread.start();
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public class ServerReceiver extends Thread{
		//���� ���� ���� Ŭ���� 
		Socket socket; 
		DataInputStream in;
		DataOutputStream out;
		
		public ServerReceiver(Socket socket) {
			 this.socket = socket;
			 
			 try {
				 // ������ �о����
				in = new DataInputStream(socket.getInputStream());
				// ������ ���� (���)
				out = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		
		@Override
		public void run() {
			 String name ="";
			 
			 try {
				name = in.readUTF();
				
				// ��ü �޼��� �˸�
				sendToAll("#"+ name + " ���� �����̽��ϴ�");
				
				clients.put(name, out); // ���� ���� ���� 
				System.out.println("���� ���� ������ �� :" + clients.size());
				
				//���� �޼��� ���� 
				while(in !=null) {
					sendToAll(in.readUTF());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			 
		} 

	}
	
	//��ü USER �޼��� ���� �ϴ� �޼��� 
	public void sendToAll(String msg) {
		// ���� user �̸��� �� ������ 
		//1��� , 127.0.0.1
		//2��� , 127.0.0.2
		//3��� , 127.0.0.3
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()) { // ������ �� ��ŭ count
			// 1 USER �� ������ ���� 
			DataOutputStream out = (DataOutputStream) clients.get(it.next());
			try {
				out.writeUTF(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	public static void main(String[] args) {
		MulichatServer s = new MulichatServer();
		s.start();

	}

}
