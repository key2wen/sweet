package com.wenwen.sweet.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * 安全插入引用数据, 防止xss攻击
 * 如果需要显示原数据, 使用宏: <code> #sliteral($!value) </code>
 *
 * @author yunxiang.zyx
 * @date 16/3/21.
 */
public class SecurityInsertionEventHandler implements ReferenceInsertionEventHandler {

    private Logger logger = LoggerFactory.getLogger(SecurityInsertionEventHandler.class);

    public Object referenceInsert(String reference, Object value) {

        if (value instanceof String) {
            ReferenceAction action = RootReferenceFilter.getReferenceAction(reference);
            String retValue = (String) value;
            switch (action) {
                case LITERAL:
                    break;
                default:
                    // 在默认的情况下，日志记录，可能是html的输出
                    if (logger.isDebugEnabled()) {
                        if (retValue.indexOf('<') > -1 && retValue.indexOf('>') > -1) {
                            logger.debug("reference=" + reference + " maybe is a html content");
                        }
                    }
                    retValue = StringEscapeUtils.escapeHtml(String.valueOf(value));
                    break;
            }
            return retValue;
        }

        return value;
    }

}

enum ReferenceAction {
    LITERAL,
    NOT_DEFINED
}

class RootReferenceFilter {
    static Map<String, ReferenceAction> filter = new HashMap<String, ReferenceAction>();

    static {
        filter.put("$screen_content", ReferenceAction.LITERAL);
        filter.put("$!screen_content", ReferenceAction.LITERAL);

        filter.put("$!{security_literal_x_z}", ReferenceAction.LITERAL);
    }

    public static ReferenceAction getReferenceAction(String ref) {
        ReferenceAction action = filter.get(ref);
        if (action == null) {
            action = ReferenceAction.NOT_DEFINED;
        }
        return action;
    }
}
