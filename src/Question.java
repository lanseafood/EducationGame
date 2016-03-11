public class Question {
	private int question_id;
	private int ecology_id;
	private String question;
	private String answer;
	
	public Question(int rowid, int id, String q, String a) {
		question_id = rowid;
		ecology_id = id;
		question = q;
		answer = a;
	}
	
	public int get_Question_ID() {
		return question_id;
	}
	
	public int get_Ecology_ID() {
		return ecology_id;
	}
	
	public String get_Question() {
		return question;
	}
	
	public String get_Answer() {
		return answer;
	}
}