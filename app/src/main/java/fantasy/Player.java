package fantasy;

public class Player {

	public String name;

	public Player(String name) {
		this.name = name;
	}

	public String shortName() {
		if (name == null)
			return null;

		if (this.name.contains(","))
			return name.substring(0, name.indexOf(","));

		return name;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

}
