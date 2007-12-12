package praktika1;

public class AnalizatzaileSintaktikoa {

	int id;

	Token look;

	private void Match(String token) {
		if (!look.getTokenMota().equals(token)) {
			System.out.print("Errore sintaktikoa lerro zenbakia: "
					+ look.Linea() + ".");
			System.out.println(" Espero genuen: " + token
					+ " eta hauxe da daukaguna: " + look.getTokenIzena());
		}
		look.Next_token();
	}

	private String Adierazpen_bakuna() {
		// TODO Auto-generated method stub
		return null;
	}

	public String faktore() {

		String id_izena, zenb_osoko_izena, zenb_erreal_izena, adierazpen_bakuna_izena;
		String faktore_izena = "";

		if (look.getTokenMota().equals("Id")) {
			id_izena = look.getTokenIzena();
			Match("Id");
			faktore_izena = id_izena;
		} else if (look.getTokenMota().equals("zenb_osoko")) {
			zenb_osoko_izena = look.getTokenIzena();
			Match("num_entero");
			faktore_izena = zenb_osoko_izena;
		} else if (look.getTokenMota().equals("zenb_erreal")) {
			zenb_erreal_izena = look.getTokenIzena();
			Match("num_real");
			faktore_izena = zenb_erreal_izena;
		} else {
			// if (look.T_token().equals("("))
			Match("(");
			adierazpen_bakuna_izena = Adierazpen_bakuna();
			Match(")");
			faktore_izena = adierazpen_bakuna_izena;
		}

		return faktore_izena;
	}

	 public int M{
	 return lortu_erreferentzia();
	 }
	 
	 public void Aukerazko_id()
	 { 
	 	if (look.T_Token().equals("Id"))
	 		match("Id");
}

