
public class Question {
	private String ques = "";
	private String ans = "";
	private int value = 0;
	boolean asked = false;
	
	public String getQuestion(){
		return ques;
	}
	
	public String getAnswer(){
		return ans;
	}
	public int getValue(){
	    return value;
	}
	
	 public void setQuestion(String q){
		 ques = q;
	 }
	 public void setAnswer(String a){
		 ans = a;
	 }
	public void setValue(int v){
	    value=v;
	}
	public void wasAsked(){
		asked=true;
	}
}

