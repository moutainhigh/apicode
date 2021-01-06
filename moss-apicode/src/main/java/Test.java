import com.ycandyz.master.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

public class Test {
    public static void main(String[] args) {
        String menuIdStr = "a11a1";
        boolean isNum = StringUtils.isNumeric(menuIdStr);
        System.out.println(isLetterDigit(menuIdStr));

    }

    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
}
