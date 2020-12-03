import org.apache.commons.lang3.StringUtils;

public class Test {
    public static void main(String[] args) {
        String menuIdStr = "224";
        boolean isNum = StringUtils.isNumeric(menuIdStr);
        System.out.println(isNum);
    }
}
