import java.io.IOException;
import java.net.*;

public class RunChat {
	
	public static void main(String[] args) {
		new Thread(new ServerFrame()).start();
		new Thread(new ClientFrame()).start();
	}
	
}
