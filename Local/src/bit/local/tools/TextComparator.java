package bit.local.tools;
/**
 * @author lire
 * @title: TextComparator
 * @projectName LexueHelper
 * @description: 比较文本
 */
public class TextComparator {
    private IgnoreMode mode;
    private String ownText;

    /**
     * 构造器。
     * @param mode 忽略模式
     * @param ownText 自身的文本
     */

    public TextComparator(IgnoreMode mode, String ownText) {
        this.mode = mode;
        this.ownText = ownText;
    }
    public TextComparator(String ownText) {
        this(IgnoreMode.IGNORE_NO_BLANK, ownText);
    }
    public IgnoreMode getMode() {
        return mode;
    }

    public void setMode(IgnoreMode mode) {
        this.mode = mode;
    }

    public String getOwnText() {
        return ownText;
    }

    public void setOwnText(String ownText) {
        this.ownText = ownText;
    }

    private boolean compareWithBlank(String other) {
        return ownText.equals(other);
    }

    private boolean compareWithoutEndBlank(String other) {
        return ownText.trim().equals(other.trim());
    }

    private boolean compareWithoutBlank(String other) {
        String _text = ownText.replaceAll("\\s", "");
        String _other = other.replaceAll("\\s", "");
        return _text.equals(_other);
    }

    /**
     * 比较的函数
     * @param other 比对对象字符串
     * @return 是否能通过比较
     */

    public boolean compareWith(String other) {
        switch (mode) {
            case IGNORE_NO_BLANK:
                return compareWithBlank(other);
            case IGNORE_ALL_BLANK:
                return compareWithoutBlank(other);
            case IGNORE_BLANK_ON_END:
                return compareWithoutEndBlank(other);
            default:
                return false;
        }
    }
}
