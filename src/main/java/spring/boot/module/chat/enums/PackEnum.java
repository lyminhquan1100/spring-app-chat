package spring.boot.module.chat.enums;

import spring.boot.core.enums.IEnum;

public enum PackEnum implements IEnum {
    LIST_ROOM((short)1),
    JOIN((short)2),
    WARNING((short)3),
    CHAT((short)4),
    EMOJI((short)5),
    EMOJI_REMOVE((short)6),
    LAST_SEEN((short)7),
    LOGOUT_DEVICE((short)8),
    COORDS_UPDATE((short)9),
    SUCCESS_LOGOUT((short)10);

    private final Short value;

    PackEnum(Short value) {
        this.value = value;
    }

    @Override
    public Short getValue() {
        return this.value;
    }
}
