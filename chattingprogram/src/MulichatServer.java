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
	
	//서버 생성, 클라이언트 접속 대기 
	public void start() {
		ServerSocket server = null;
		Socket socket = null;
		
		// 1.서버 생성 
		try {
			server = new ServerSocket(7777);
			System.out.println("서버 시작!");
			
			while(true) {
			
			socket = server.accept();
			System.out.println(socket.getInetAddress() + " 에서 접속 ");
			
			// 수신 클래스 실행
			ServerReceiver thread = new ServerReceiver(socket);
			thread.start();
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public class ServerReceiver extends Thread{
		//서버 정보 저장 클래스 
		Socket socket; 
		DataInputStream in;
		DataOutputStream out;
		
		public ServerReceiver(Socket socket) {
			 this.socket = socket;
			 
			 try {
				 // 데이터 읽어오기
				in = new DataInputStream(socket.getInputStream());
				// 데이터 전송 (출력)
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
				
				// 전체 메세지 알림
				sendToAll("#"+ name + " 님이 들어오셨습니다");
				
				clients.put(name, out); // 접속 정보 저장 
				System.out.println("현재 서버 접속자 수 :" + clients.size());
				
				//무한 메세지 받음 
				while(in !=null) {
					sendToAll(in.readUTF());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			 
		} 

	}
	
	//전체 USER 메세지 전송 하는 메서드 
	public void sendToAll(String msg) {
		// 접속 user 이름만 다 가져옴 
		//1사람 , 127.0.0.1
		//2사람 , 127.0.0.2
		//3사람 , 127.0.0.3
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()) { // 접속자 수 만큼 count
			// 1 USER 에 데이터 전송 
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
