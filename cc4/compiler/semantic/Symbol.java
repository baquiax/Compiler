package compiler.semantic;

public class Symbol {
	private String idSymbol;

	public Symbol(String id)  {
		idSymbol=id;
	}

	public String returnSymbol() {
		return idSymbol;
	}
}
