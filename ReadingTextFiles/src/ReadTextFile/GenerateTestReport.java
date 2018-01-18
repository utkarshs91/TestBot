package ReadTextFile;

public class GenerateTestReport {
	
	private String TestName;
	private String TestStatus;
	private String stackTrace;
	
	
	
	 public GenerateTestReport(String TestName, String TestStatus, String stackTrace) {
	        this.TestName = TestName;
	        this.TestStatus = TestStatus;
	        this.stackTrace = stackTrace;
	    }
	 
	 
	 
	 
	 public String getTestName() {
		return TestName;
	}




	public void setTestName(String testName) {
		TestName = testName;
	}




	public String getTestStatus() {
		return TestStatus;
	}




	public void setTestStatus(String testStatus) {
		TestStatus = testStatus;
	}




	public String getStackTrace() {
		return stackTrace;
	}




	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}




	
	 
	 
}
