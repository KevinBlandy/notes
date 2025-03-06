----------------------------
springboot�ĵ�Ԫ����		|
----------------------------
	# ��������
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	
	# ���Դ���
		import io.springboot.jpa.JpaApplication;
		import io.springboot.jpa.repository.UserRepository;
		import junit.framework.TestCase;
		import org.junit.Test;
		import org.junit.Before;
		import org.junit.After;
		import org.junit.runner.RunWith;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.boot.test.context.SpringBootTest;
		import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
		import org.springframework.test.context.junit4.SpringRunner;
		



		@RunWith(SpringRunner.class)
		// ����@SpringBootApplication �࣬������������˿ڼ���
		@SpringBootTest(classes = JpaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
		public class JpaTest {

			// ����ʹ��IOC
			@Autowired
			private UserRepository userRepository;

			@Before
			public void testBefore(){
				System.out.println("����ǰ");
			}

			@After
			public void testAfter(){
				System.out.println("���Ժ�");
			}

			@Test
			public void test(){
				//TODO ִ�в��Դ���

				// ���Էǿ�
				TestCase.assertNotNull();
				// ����equals
				TestCase.assertEquals();
			}
		}
	
	# SB 3

		import org.junit.jupiter.api.Test;
		import org.springframework.boot.test.context.SpringBootTest;
		import org.springframework.test.context.ActiveProfiles;

		import com.app.App;


		@ActiveProfiles("dev")
		@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
		public class AppTests {

			@Test
			public void test () {
				
				
			}
		}

	
	# ָ�� Profile
		
		import org.springframework.test.context.ActiveProfiles;
		@ActiveProfiles("dev")