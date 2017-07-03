import java.io.Serializable;

public class Score implements Serializable {
	public Score(String name, int score) {
		this.score = score;
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int score;
	private String name;

}
