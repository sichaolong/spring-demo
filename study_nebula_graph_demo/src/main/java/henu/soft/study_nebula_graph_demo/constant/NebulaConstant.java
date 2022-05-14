package henu.soft.study_nebula_graph_demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常用的字符串常量
 */
public class NebulaConstant {
    public static final String USE = "USE ";
    public static final String SEMICOLON = "; ";
    public static final String ERROR_CODE = "-1";

    @Getter
    @AllArgsConstructor
    public enum NebulaJson{
        ERRORS("errors"),
        CODE("code"),
        MESSAGE("message"),
        RESULTS("results"),
        COLUMNS("columns"),
        DATA("data"),
        ROW("row");
        private String key;
    }
}

