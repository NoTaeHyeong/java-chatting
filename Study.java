import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class Study {

	public static void main(String[] args) {
		boolean run = true;
		int studentNum = 0;
		int max = 0;
		int sum = 0;
		int[] scores = null;
		double avr = 0.0;
		Scanner sc = new Scanner(System.in);
		
		while(run) {
			System.out.println("-----------------------------------------------------");
			System.out.println("1.�л��� | 2.�����Է� | 3.��������Ʈ | 4.�м� | 5.����");
			System.out.println("-----------------------------------------------------");
			System.out.print("����> ");
			
			int selectNo = sc.nextInt();
			
			if(selectNo == 1) {
				System.out.print("�л���> ");
				studentNum = sc.nextInt();
				scores = new int[studentNum];
			} else if(selectNo == 2) {
				for(int i=0; i<studentNum; i++) {
					System.out.print("scroes[" + i + "]> ");
					scores[i] = sc.nextInt();
				}
			} else if(selectNo == 3) {
				for(int i=0; i<studentNum; i++) {
					System.out.println("scroes[" + i + "]: " + scores[i]);
				}
			} else if(selectNo == 4) {
				for(int i=0; i<studentNum; i++) {
					sum += scores[i];
					
					if(max < scores[i]) {
						max = scores[i];
					}
				}
				
				avr = sum / (double)studentNum;
				
				System.out.println("�ְ� ����: " + max);
				System.out.println("��� ����: " + avr);
			} else if(selectNo == 5) {
				run = false;
			}
		}
		
		System.out.println("���α׷� ����");
	}

}
