/**
 * 
 * 当前租户ID
 * @author KevinBlandy
 *
 */
public class TenantIdHolder {

	private static final ThreadLocal<Object> context = new ThreadLocal<>();

	public static Object get() {
		return context.get();
	}

	public static void set(Object tenant) {
		context.set(tenant);
	}

	public static void remove() {
		context.remove();
	}
}
