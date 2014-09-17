import java.util.LinkedList;


public class Term {
	
	String name;
	int docFrequency;
	int totalFrequency;
	LinkedList<Occurrence> occurrences = new LinkedList<Occurrence>();
	
	public Term(String name){
		this.name = name.toLowerCase();
		docFrequency=1;
	}
	
	public void incFrequence(String document){
		totalFrequency++;
		//TODO: Implement complete method
	}
}
