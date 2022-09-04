package henu.soft.scl.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author sichaolong
 * @date 2022/8/27 15:03
 */
public class ExceptionUtil {
    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public static String handleException( BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "ExceptionUtil中定义的handleException, error occurred at ";
    }
}
