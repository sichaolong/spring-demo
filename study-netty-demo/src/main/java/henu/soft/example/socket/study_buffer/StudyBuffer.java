package henu.soft.example.socket.study_buffer;

import java.nio.IntBuffer;

public class StudyBuffer {

    public static void main(String[] args) {

        // new StudyBuffer().test1();
        new StudyBuffer().test2();

    }


    /**
     * 测试写入
     */
    public void test1(){
        IntBuffer intBuffer = IntBuffer.allocate(2);
        intBuffer.put(12345678);
        intBuffer.put(2);
        intBuffer.flip();
        System.err.println(intBuffer.get());
        System.err.println(intBuffer.get());
    }

    /**
     * 写入buffer两个数据，在写、读模式下capacity、position和limit关系
     * Write mode:
     * 	Capacity: 10
     * 	Position: 2
     * 	Limit: 10
     * Read mode:
     * 	Capacity: 10
     * 	Position: 0
     * 	Limit: 2
     */
    public void test2(){
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(10);
        intBuffer.put(101);
        System.out.println("写入buffer两个数据，在写、读模式下capacity、position和limit关系");
        System.err.println("Write mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        intBuffer.flip(); // 转为读模式，position置为 0
        // Buffer 的读/写模式共用一个 position 和 limit 变量.
        //当从写模式变为读模式时, 原先的 写 position 就变成了读模式的 limit.
        System.err.println("Read mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
    }
}

