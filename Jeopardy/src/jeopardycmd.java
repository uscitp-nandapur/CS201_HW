
public class jeopardycmd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GameBoard g = new GameBoard();
		
		g.initialMenu();
		g.parseFile("input");
		g.commandLineMenu();
		

	}

}
