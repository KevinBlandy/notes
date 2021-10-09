-------------------
Cleanup
-------------------
	# 关闭资源代码生成
	# Cleanup
		@Target(ElementType.LOCAL_VARIABLE)
		@Retention(RetentionPolicy.SOURCE)
		public @interface Cleanup {
			/** @return The name of the method that cleans up the resource. By default, 'close'. The method must not have any parameters. */
			String value() default "close";
				* 指定关闭资源的方法名称，默认就是 close()
		}


	# Demo
		* 原代码
			import lombok.Cleanup;
			import java.io.*;

			public class CleanupExample {
			  public static void main(String[] args) throws IOException {
				@Cleanup InputStream in = new FileInputStream(args[0]);
				@Cleanup OutputStream out = new FileOutputStream(args[1]);
				byte[] b = new byte[10000];
				while (true) {
				  int r = in.read(b);
				  if (r == -1) break;
				  out.write(b, 0, r);
				}
			  }
			}
		
		* 生成代码
			import java.io.*;
			public class CleanupExample {
			  public static void main(String[] args) throws IOException {
				InputStream in = new FileInputStream(args[0]);
				try {
				  OutputStream out = new FileOutputStream(args[1]);
				  try {
					byte[] b = new byte[10000];
					while (true) {
					  int r = in.read(b);
					  if (r == -1) break;
					  out.write(b, 0, r);
					}
				  } finally {
					if (out != null) {
					  out.close();
					}
				  }
				} finally {
				  if (in != null) {
					in.close();
				  }
				}
			  }
			}