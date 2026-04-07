package boot.linksOrganizer.utils;

import boot.linksOrganizer.dto.MessageDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
  public static String splitFirstPosition(String str) {
    return str.split("\n")[0];
  }

  public static String splitSecondPosition(String str) {
    return str.split("\n")[1];
  }
}
