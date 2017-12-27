import java.util.ArrayList;
import java.util.List;


public class Test1 {
	public static boolean check(String str) {
		str = str.toLowerCase();
		int length = str.length();
		int i = 0; 
		int j = length - 1;
		while (i < j) {
			char a;
			do {
				a = str.charAt(i ++);
			} while (! ( (a < 'z' && a > 'a') ));
			char b;
			do {
				b = str.charAt(j --);
			} while (! ( (b < 'z' && b > 'a') ));
			
			if (i - 1 < j + 1 && a != b) {
				return false;
			}
		}
		return true;
	}
	
	public static List<String> reorder(List<String> words, int count) {
		List<String> reorderedWords = new ArrayList<String>();
		if (count == 1) {
			for (String word : words) {
				reorderedWords.add(word);
			}
		} else {
			for (int i = 0 ; i < words.size(); i ++) {
				String currentWord = words.get(i);
				List<String> tempWords = new ArrayList<String>();
				for (int j = 0; j < words.size(); j ++) {
					if (i != j) {
						tempWords.add(words.get(j));
					}
				}
				List<String> reorderedWords1 = reorder(tempWords, count - 1);
				for (String tmp : reorderedWords1) {
					reorderedWords.add(currentWord + " " + tmp);
				}
			}
		}
		return reorderedWords;
	}
	
	public static void main(String[] args) {
		System.out.println("Madam, I'm Adam is " + check("Madam, I'm Adam"));
		System.out.println("Racecar is " + check("racecar"));
		System.out.println("Foo is " + check("foo"));
		
		List<String> words = new ArrayList<String>();

		System.out.print("Input: ");
		for (String word : args) {
			words.add(word);
			System.out.print(" " + word);
		}
		System.out.println();
		
		int maxLength = 0;
		String maxWords = "";
		for (int count = 1; count < args.length; count ++) {
			List<String> reorderedWords = reorder(words, count);
			for (String str : reorderedWords) {
				boolean is = check(str);
				if (is && str.length() > maxLength) {
					maxWords = str;
					maxLength = str.length();
				}
			}
		}
		System.out.println("Max: " + maxWords);
	}
}
