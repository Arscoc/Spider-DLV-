import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
@Id("move")

public class Move {

	@Param(0)	
	int column;
	@Param(1)
	int line; 
	@Param(2)
	int to;

	
	public Move(int column, int line, int to) {
		super();
		this.column = column;
		this.line = line;
		this.to = to;
	}


	public Move() {
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public int getLine() {
		return line;
	}


	public void setLine(int line) {
		this.line = line;
	}


	public int getTo() {
		return to;
	}


	public void setTo(int to) {
		this.to = to;
	}
	@Override
	public String toString() {
		return "move(" + column + "," + line + "," + to + ").";
	}
}
