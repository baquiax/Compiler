package compiler.semantic;
import java.util.Hashtable;
import java.util.ArrayList;

public class TypeManager {
	private static final Hashtable<String,String[][]> arithOp = new Hashtable<String, String[][]>() {{     
		//operator type type resultType
		put("+", new String[][]{ new String[]{"int" , "int", "int"} });		
		put("-", new String[][]{ new String[]{"int" , "int", "int"} });
		put("/", new String[][]{ new String[]{"int" , "int", "int"} });
		put("*", new String[][]{ new String[]{"int" , "int", "int"} });
		put("%", new String[][]{ new String[]{"int" , "int", "int"} });
   	}}; 

	private static final Hashtable<String,String[][]> logicalOp = new Hashtable<String, String[][]>() {{     
		put(">",  new String[][]{ new String[]{"int" , "int", "boolean"} });
		put(">=", new String[][]{ new String[]{"int" , "int", "boolean"} });
		put("<",  new String[][]{ new String[]{"int" , "int", "boolean"} });
		put("<=", new String[][]{ new String[]{"int" , "int", "boolean"} });		
   	}}; 

	private static final Hashtable<String,String[][]> booleanOps = new Hashtable<String, String[][]>() {{     
		put("&&", new String[][]{ new String[]{"boolean" , "boolean", "boolean"} });
		put("||", new String[][]{ new String[]{"boolean" , "boolean", "boolean"} });		
   	}}; 

	private static final Hashtable<String,String[][]> comparationOp = new Hashtable<String, String[][]>() {{     
		put("==", new String[][]{ 
			new String[]{"int" , "int", "boolean"}, 
			new String[]{"boolean" , "boolean", "boolean"} 
		});
		put("!=", new String[][]{ 
			new String[]{"int" , "int", "boolean"},
			new String[]{"boolean" , "boolean", "boolean"}
		});
   	}}; 

	public static String checkType(String firstType, String secondType, String operator) {
		String[][] rules = TypeManager.getValidRules(operator);
		if (rules != null) {
			for (int i = 0; i < rules.length; i++) {
				String[] rule = rules[i];
				if (rule[0] == firstType && rule[1] == secondType) {
					return rule[2];
				}
			}
		}
		return "error";
	}

	private static String[][] getValidRules(String operator) {
		String[][] result = TypeManager.arithOp.get(operator);
		if (result == null) {
			result = TypeManager.logicalOp.get(operator);
			if (result == null) {
				result = TypeManager.booleanOps.get(operator);
				if (result == null) {
					result = TypeManager.comparationOp.get(operator);
				}
			}
		}
		return result;
	}
	
}