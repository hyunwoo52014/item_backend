package kr.happyjob.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Happyjob3Application.class)
class Happyjob3ApplicationTests {

	@Test
	void contextLoads() {
        System.out.println("애플리케이션 컨텍스트가 성공적으로 로드되었습니다.");
    }

}
