package pers.jd.system;

/**
 * @author jensen_deng
 */
public enum SystemInfo {
  /** Windows */
  WINDOWS("windows"),
  WINDOWS_CMD("cmd"),
  /** Mac */
  MACOS("mac"),
  MACOS_BASH("/bin/bash"),
  MACOS_ZSH("/bin/zsh"),
  /** Linux */
  LINUX("linux"),
  LINUX_BASH("/bin/bash");

  private final String value;

  SystemInfo(String value) {
    this.value = value;
  }

  /** 获取当前系统名称 */
  public static String getCurrentSystem() {
    String system = System.getProperty("os.name").toLowerCase();
    if (system.startsWith(MACOS.value)) {
      return MACOS.value;
    } else if (system.startsWith(WINDOWS.value)) {
      return WINDOWS.value;
    } else if (system.startsWith(LINUX.value)) {
      return LINUX.value;
    }
    return "No matching values.";
  }

  public static String getCurrentSystemCommandExecutor(String system) {
    if (system.startsWith(MACOS.value)) {
      return MACOS_BASH.value;
    } else if (system.startsWith(WINDOWS.value)) {
      return WINDOWS_CMD.value;
    } else if (system.startsWith(LINUX.value)) {
      return LINUX_BASH.value;
    }
    return "No matching values.";
  }

  public String getValue() {
    return value;
  }
}
