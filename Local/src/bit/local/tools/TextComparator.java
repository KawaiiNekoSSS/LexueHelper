package bit.local.tools;

public class TextComparator {
    private IgnoreMode mode;
    private String ownText;

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
