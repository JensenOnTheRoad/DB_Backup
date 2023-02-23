package pers.jd.mail;

import java.util.Arrays;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.jd.system.SystemInfo;

/**
 * @author jensen_deng
 */
@SpringBootTest
class SystemTest {
  @Test
  @DisplayName("获取系统信息")
  void should_get_system_info() {
    // 当前系统名称
    String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);
    System.out.println(OS_NAME);
    // 当前系统的位数X86?X64
    String OS_ARCH = System.getProperty("os.arch").toLowerCase(Locale.US);
    System.out.println(OS_ARCH);
    // 获取当前系统的版本信息
    String OS_VERSION = System.getProperty("os.version").toLowerCase(Locale.US);
    System.out.println(OS_VERSION);

    if (OS_NAME.contains(SystemInfo.MACOS.getValue())) {
      Arrays.asList("cmd.exe", "/c", "dir");
      System.out.println("this is mac");
    } else {
      Arrays.asList("sh", "-c", "ls");
    }
  }
}
