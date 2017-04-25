package com.soho.inko.constant.im4java;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;

/**
 * Created by ZhongChongtao on 2017/3/26.
 */
public enum ImageCommandType {
    CONVERT(ConvertCmd.class), IDENTIFY(IdentifyCmd.class);
    private Class<? extends ImageCommand> commandClazz;

    ImageCommandType(Class<? extends ImageCommand> commandClazz) {
        this.commandClazz = commandClazz;
    }

    public Class<? extends ImageCommand> getCommandClazz() {
        return commandClazz;
    }
}
