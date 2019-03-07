public class Person {
	private String name;

	public Person(String name) {
		this.name = name;
	}

	public Person() {
		this("Null");
	}

	public String getName() {
		return name;
	}

}
