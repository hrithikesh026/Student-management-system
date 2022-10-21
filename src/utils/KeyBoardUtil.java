package utils;

import java.util.Scanner;

public class KeyBoardUtil {
	public int readInt(String message) {
		System.out.println(message);
		return new Scanner(System.in).nextInt();
	}
	public String readString(String message) {
		System.out.println(message);
		return new Scanner(System.in).nextLine();
	}
}
