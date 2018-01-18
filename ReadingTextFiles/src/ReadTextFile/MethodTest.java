package ReadTextFile;

public class MethodTest {
	
	public static void main (String args[]) {
		
		String NonTrimXpath = " //label[text()='Document Title']/../..//textarea ";
		String xpath = NonTrimXpath.trim();
		
		System.out.println(xpath);
	}

}
