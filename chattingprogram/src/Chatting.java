//	서버 PrintWriter 널 예외, 서버 while(true)

public class Chatting {

	public static void main(String[] args) {
		Server server = new Server();
		Client client = new Client();
		new Thread(server).start();
		new Thread(client).start();
		
	}
	
}
