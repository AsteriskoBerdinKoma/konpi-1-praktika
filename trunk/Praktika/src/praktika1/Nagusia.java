package praktika1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Nagusia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 2)
				System.out
						.println("Sarrerako eta irteerako fitxategien izenak sartu");
			else {
				String sarreraFitx = args[0];
				// Token t = new Token(sarreraFitx);
				// t.Next_token();
				// while (!t.getTokenMota().equals("EOF")) {
				// System.out.println("[" + t.getTokenIzena() + " | "
				// + t.getTokenMota() + "]");
				// t.Next_token();
				// }

				String irteeraFitx = args[1];

				FileOutputStream f = new FileOutputStream(irteeraFitx);
				AnalizatzaileSintaktikoa szie = new AnalizatzaileSintaktikoa(
						sarreraFitx);

				Vector<String> erantzuna = szie.Programa();

				byte barray[];
				for (String instr : erantzuna) {
					instr += " \n";
					barray = instr.getBytes();
					f.write(barray);
				}
				f.close();
				// while (!(erantzuna.isEmpty())) {
				// String instr;
				// instr = (String) erantzuna.firstElement();
				// instr = instr + " \n";
				// barray = new byte[instr.length()];
				// instr.getBytes(0, instr.length(), barray, 0);
				// f.write(barray);
				// erantzuna.removeElementAt(0);
				// }// end while
				// f.close();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
