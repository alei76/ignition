package core;

public class Other {

	@Override
	public String toString() {
		AutoValue autoValue = new AutoValue();
		int sample = autoValue.getSample();
		System.out.println(sample);
		return "Other [getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
