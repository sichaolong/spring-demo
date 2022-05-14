package study_single;


/**
 * 饿汉式
 */
public class HungryMan {


    // 因为是getInstance是静态方法，因此开始就被加载进内存，因此有可能会浪费内存空间
    private byte[] data1 = new byte[1024 * 1024];
    private byte[] data2 = new byte[1024 * 1024];
    private byte[] data3 = new byte[1024 * 1024];
    private byte[] data4 = new byte[1024 * 1024];



    // 私有构造方法
    private HungryMan(){

    }

    private static final HungryMan HUNGRY_MAN = new HungryMan();

    public static HungryMan getInstance(){
        return HUNGRY_MAN;
    }
}
