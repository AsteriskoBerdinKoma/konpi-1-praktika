package praktika1;

///////////////////////////////////////////////////
// Praktika Raul Barrenak partzialki euskaratuta //
///////////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import praktika1.Afd;

public class Analex {

	private FileInputStream f;
	private BufferedReader fitxategia;

	private String buffer;
	private String newBuffer;

	private int inicio, inicio_ant, unekoa;

	final private int ind_superior_pr = 31;

	final private Afd automata = new Afd();

	private int lerroZenbakia;

	private int tokenEgoera;

	private boolean prevLerroa;

	final private String hitzErreserbatuak[] =

	{ "program", "procedure", "is", "begin", "end_program", "var", "integer",
			"float", "in", "out", "if", "then", "else", "end_if", "repeat",
			"until", "end_repeat", "for", "ascending", "from", "to", "do",
			"end_for", "descending", "get","end_procedure",
			"put_line",

			// Berdina baina letra larriz

			"PROGRAM" , "PROCEDURE", "IS", "BEGIN", "END_PROGRAM", "VAR",
			"INTEGER", "FLOAT", "IN", "OUT", "IF", "THEN", "ELSE", "END_IF",
			"REPEAT", "UNTIL", "END_REPEAT", "FOR", "ASCENDING", "FROM", "TO",
			"DO", "END_FOR", "DESCENDING", "GET","END_PROCEDURE", "PUT_LINE"};

	// klasearen eraikitzaileak fitxategiaren izena behar du
	public Analex(String fitxIzena) throws FileNotFoundException, IOException {

		f = new FileInputStream(fitxIzena);
		fitxategia = new BufferedReader(new InputStreamReader(f));

		lerroZenbakia = 1;
		buffer = (String) fitxategia.readLine();
		newBuffer = buffer;
		inicio = 0;
		unekoa = 0;
		inicio_ant = 0;
		prevLerroa = false;

	}

	// Buru irakurtzaileak irakurri duen karakterearen mota itzultzen du
	final private char karaktereMota(char c) {
		if ((c >= '0') && (c <= '9'))
			return ('d');

		if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) {

			if ((c == 'E') || (c == 'e'))
				return ('e');
			else
				return ('h');
		}

		if ((c == ' ') || (c == '\t') || c == '\n')
			return ('z');

		return (c);

	}

	// Karaktere-mota bat emanda, Automatak onartzen duen ikurra itzultzen du.
	final private int karaktereMotaItzuli(char car) {

		char tdc = karaktereMota(car);
		switch (tdc) {

		case 'h':
			return (0); // E edo e ez den edozein hizki

		case 'e':
			return (18); // E edo e letrak

		case 'd':
			return (16); // digituak

		case '_':
			return (1);

		case '.':
			return (17);

		case '+':
			return (10);

		case '-':
			return (11);

		case '*':
			return (3);

		case '/':
			return (12);

		case '>':
			return (14);

		case '<':
			return (13);

		case '=':
			return (15);

		case ':':
			return (7);

		case ',':
			return (5);

		case ';':
			return (6);

		case '(':
			return (2);

		case ')':
			return (4);

//		case '{':
//			return (8);
//
//		case '}':
//			return (9);

		case 'z':
			return (19); // zuriuneak

		default:
			return (20);
		}

	}

	/*
	 * OHARRA
	 * 
	 * Es_separador eta Saltar separadores funtzioak ez dira beharrezkoak, nik
	 * planteatu dudanari dagokionez. Bi funtzio hauen bidez zuriuneak saltatzen
	 * dira automatikoki, baina nire programan zuriuneak token-mota onartzen
	 * dira horregatik bi funtzio hauek soberan daude eta tokenak bueltatzeko
	 * erabiltzen den Next_token() funtzioan hauxe besterik ez da adierazi
	 * behar: token_mota zuriuneak baldin bada Hurrengo_token() funtzioari deitu
	 * behar zaio beste token bat bueltatzeko.
	 * 
	 * Asuntua da, saiatu naizela hori egiten baina indizeren batek errore
	 * ematen dit eta ez dakit zergatik. Denbora dexente egon naiz indizeak
	 * berrikusten eta azkenean erabaki dut Es_separador eta Saltar_separadores
	 * erabiltzea, jakinda ere soberan daudela.
	 * 
	 * 
	 * 
	 */

	final private boolean isHitzErreserbatua(String mota) {

		boolean parar = false;

		for (int i = 0; ((!parar) && (i <= ind_superior_pr)); i++) {

			parar = ((hitzErreserbatuak[i].equalsIgnoreCase(mota)) || parar);
		}

		return (parar);

	}

	final public void Next_token() throws IOException, Fitx_bukaera {

		lortuTokena();
		String token_mota = getTokenMota();
		while (token_mota.equals("BEREIZLEA") || token_mota.equals("IRUZKINA")) {
			lortuTokena();
			token_mota = getTokenMota();
		}

	}

	final public void lortuTokena() throws IOException, Fitx_bukaera {
		lortuTokena(0, 0);
	}

	final public void lortuTokena(int ego, int hurEgo) throws IOException,
			Fitx_bukaera {
		boolean trantsizioEgokia = true;
		boolean errorea = false;
		int unekoEgoera = ego;
		int hurrengoEgoera = hurEgo;
		boolean rellenar = false;

		// inicio_ant=inicio;

		while (unekoa == buffer.length()) {
			if (!buffer.trim().equals("")) {
			}
			buffer = lerroBerria();
			if (buffer == null) {
				throw new Fitx_bukaera();
			}
			lerroZenbakia++;
			inicio_ant = 0;
			unekoa = 0;
		}

		inicio_ant = inicio;
		inicio = unekoa; // inicio aldagaian gordetzen dugu hurrengo
							// tokenaren hasiera

		while (trantsizioEgokia && (buffer != null)) {
			hurrengoEgoera = automata.trantsizioa(unekoEgoera,
					karaktereMotaItzuli(buffer.charAt(unekoa)));

			if (hurrengoEgoera < 0)
				trantsizioEgokia = false; // transizioa ez da posible

			else {
				unekoEgoera = hurrengoEgoera;
				unekoa++;
			} // end if

			if (unekoa == buffer.length()) {
				trantsizioEgokia = false;
				rellenar = true;
			}
		}// end while

		if (buffer == null) {
			throw new Fitx_bukaera();
		} else {

			// Lerro bukaeran dagoen tokena bukaerako sinboloa den frogatzen du.
			// Hala ez bada, errorea jaurtitzen du.

			if (automata.isBukaerako(unekoEgoera)) {
				rellenar = false;
				// /*berria*/ if (actual!=buffer.length()) {rellenar=false;}
			} else {

				if (unekoEgoera == 10 || unekoEgoera == 11) {
					buffer += lerroBerria();
					lortuTokena(unekoEgoera, hurrengoEgoera);
					unekoEgoera = tokenEgoera;

				} else {

					errorea = true;
					System.out.println("Errore lexikoa " + lerroZenbakia
							+ " lerroan");
				}
			}

		}

		if (rellenar) {

			// ikusiko dugu ea tokenak hurrengo lerroan jarraitzen duen

			if (!buffer.trim().equals("")) {
			}
			buffer = lerroBerria();
			lerroZenbakia++;
			inicio_ant = 0;
			unekoa = 0;
		}// end if

		inicio_ant = inicio;
		inicio = unekoa; // inicio aldagaian gordetzen dugu hurrengo
							// tokenaren hasiera

		if (errorea) {
			unekoa++;
			inicio = unekoa;
			lortuTokena();
		}

		tokenEgoera = unekoEgoera;

	}

	private String lerroBerria() throws IOException {

		inicio_ant = 0;
		inicio = 0;

		if (prevLerroa) {
			prevLerroa = false;
//			System.out.println();
			return newBuffer;
		} else
//			System.out.println();
		return fitxategia.readLine();

	}

	/*
	 * public void Retroceder () {
	 * 
	 * if (linea_nueva){ actual=inicio_buff_ant; buffer_nuevo=buffer;
	 * buffer=buffer_anterior; linea_ant=true; linea_nueva=false;} else
	 * actual=inicio_ant;
	 * 
	 * inicio=inicio_ant;
	 * 
	 * }//end Retroceder
	 */

	// Tokenaren izena bueltatzen du
	public String getTokenIzena() {

		// String car_aux=buffer.substring(inicio_ant,actual).toLowerCase();
		String car_aux = buffer.substring(inicio_ant, unekoa);
		return (car_aux);

	}

	// Token-mota bueltatzen du
	public String getTokenMota() {

		String car_aux = buffer.substring(inicio_ant, unekoa);
		String car_dos = "error";

		switch (tokenEgoera) {
		case 1:
			if (isHitzErreserbatua(car_aux)) {
				car_dos = car_aux;
			} else {
				car_dos = "ID";
			}
			break;

		case 3:
			car_dos = "OSOKOA";
			break;
		case 5:
			car_dos = "ERREALA";
			break;
		case 7:
			car_dos = "ERREALA";
			break;
		case 9:
			car_dos = "(";
			break;
		case 12:
			car_dos = "IRUZKINA";
			break;
		case 13:
			car_dos = "+";
			break;
		case 14:
			car_dos = "-";
			break;
		case 15:
			car_dos = "*";
			break;
		case 16:
			car_dos = "/";
			break;
		case 17:
			car_dos = ">";
			break;
		case 28:
			car_dos = ">=";
			break;
		case 18:
			car_dos = "=";
			break;
		case 19:
			car_dos = "<";
			break;
		case 29:
			car_dos = "<=";
			break;
		case 20:
			car_dos = ":";
			break;
		case 21:
			car_dos = ":=";
			break;
		case 22:
			car_dos = ",";
			break;
		case 23:
			car_dos = ";";
			break;
		case 24:
			car_dos = ")";
			break;
//		case 25:
//			car_dos = "{";
//			break;
//		case 26:
//			car_dos = "}";
//			break;
		case 27:
			car_dos = "BEREIZLEA"; //' ','\t','\n'
			break;
		}

		return (car_dos);

	}

	public int Linea() {

		return lerroZenbakia;

	}
}
