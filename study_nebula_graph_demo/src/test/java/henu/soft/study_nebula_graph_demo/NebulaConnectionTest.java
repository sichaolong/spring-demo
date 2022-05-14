package henu.soft.study_nebula_graph_demo;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class NebulaConnectionTest {

    @Test
    void testNebulaConnection(){

        log.info("开始连接nebula数据库....");

        NebulaPool pool = new NebulaPool();
        Session session;
        try {
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            nebulaPoolConfig.setMaxConnSize(100);
            List<HostAddress> addresses = Arrays.asList(new HostAddress("127.0.0.1", 9669));
            Boolean initResult = pool.init(addresses, nebulaPoolConfig);

            if (!initResult) {
                log.error("pool init failed.");
                return;
            }
            session = pool.getSession("root", "nebula", false);
            boolean pingRes = session.ping();
            log.info("ping 结果为 ：{}",pingRes);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (NotValidConnectionException e) {
            e.printStackTrace();
        } catch (AuthFailedException e) {
            e.printStackTrace();
        } catch (ClientServerIncompatibleException e) {
            e.printStackTrace();
        } catch (IOErrorException e) {
            e.printStackTrace();
        }

    }
}
