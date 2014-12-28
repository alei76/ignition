package hello.infrastructure;

public class Cast {

	   @SuppressWarnings("unchecked")
	   public static <T> T cast(Object o) {
	      return (T) o;
	   }
	}
