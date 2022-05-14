package study_single;


/**
 * 枚举单例
 */
public enum  EnumSingle {
    INSTANCE;

    private EnumSingle(){}

    public  static EnumSingle getInstance(){
        return INSTANCE;
    }


}
