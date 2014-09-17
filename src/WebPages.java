import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class WebPages {
	ArrayList<Term> termIndex;
	//P1 p1 = new P1();
	public WebPages(){
		termIndex = new ArrayList<Term>();
	}
	
	public void addPage(String filename){
		
	}
	
	public void printTerms(){
		for(Term t: termIndex){
			System.out.println(t);
		}
	}
	
	public void pruneStopWords(int n){
		
	}
	
	public String[] whichPages(String word){
		ArrayList<String> s = new ArrayList<String>();
		for(Term t: termIndex){
			if(t.name.equals(word)){
				for(Occurrence o: t.occurrences){
					s.add(o.docName);
				}
				return (String[]) s.toArray();
			}
		}
		return null;
	}
	
	public class ReadDoc {
		public ArrayList<String> words = new ArrayList<String>();
		private ArrayList<String> tag = new ArrayList<String>();
		private boolean inTag =false;

		public ReadDoc(String filename){
			readFile(filename);
		}

		public void readFile(String filename) {
			String w = "";
			try {
				Scanner scanner = new Scanner(new File(filename));
				while (scanner.hasNext()) {
					
					w = scanner.next().toLowerCase();

					if(w.contains("-->")){
						@SuppressWarnings("unused")
						int x=2;
					}
					
					if (w.contains("<") || w.contains(">")) {// checks if tag
						tagWord(w);
						for (String s : tag) {
							s = punct(s);
							if (binarySearch(s, 0, words.size() - 1)>-1
									&& s.length() > 0) {
								words.add(binarySearch(s,0,words.size()-1),s);
							}
						}
						tag = new ArrayList<String>();
					} else if(!inTag){
						w = punct(w);
						if (binarySearch(w, 0, words.size() - 1)>-1 && w.length() > 0) {
							words.add(binarySearch(w,0,words.size()-1),w);
						}
					}
				}
				scanner.close();

			} catch (FileNotFoundException e) {
				// TODO How do they want errors
				e.printStackTrace();
			}
		}

		private String punct(String w) {
			char[] s = w.toCharArray();
			if(s.length<=0){//handle empty case later
				return "";
			}
			if (!Character.isAlphabetic(s[0]) && !Character.isDigit(s[0])) {
				return punct(w.substring(1));
			} else if (!Character.isAlphabetic(s[s.length - 1])
					&& !Character.isDigit(s[s.length - 1])) {
				return punct(w.substring(0, w.length() - 1));
			}
			return w;

		}

		private void tagWord(String w) {
			if (w.contains("<") && !w.contains(">")) {// ###<####
				tag.add(w.substring(0, w.indexOf('<')));
				inTag =true;
			}
			else if (w.contains(">") && !w.contains("<")) {// ###>###
				tag.add(w.substring(w.indexOf('>')+1));
				inTag=false;
			}
			else if (!w.contains(">") && !w.contains("<")) {//
				tag.add(w);
			} else {// ##<###>--
				if (w.indexOf('<') < w.indexOf('>')) {// ##<###>--
					tag.add(w.substring(0, w.indexOf('<')));// ##

				}
				tagWord(w.substring(w.indexOf('>') + 1));// --
			}
		}

		private int binarySearch(String s, int first, int last) {
			int result =0;
			if (first > last)//not found but will push from there
				result= first;
			else {
				int mid = (first + last) / 2;
				if (s.equals(words.get(mid)))
					result= -1;//implies found
				else if (s.compareTo(words.get(mid)) < 0)
					result= binarySearch(s, first, mid - 1);
				else if (s.compareTo(words.get(mid)) > 0)
					result= binarySearch(s, mid + 1, last);
			}
			return result;
		}

	}
	
	
	
}
