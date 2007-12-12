package praktika1;

import java.io.IOException;

public class Nagusia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Token t;
		try {
			if (args.length == 0)
				System.out.println("Sarrerako fitxategiaren path-a sartu");
			else {
				t = new Token(args[0]);

				t.Next_token();
				while (!t.getTokenMota().equals("EOF")) {
					System.out.println("[" + t.getTokenIzena() + " | "
							+ t.getTokenMota() + "]");
					t.Next_token();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
