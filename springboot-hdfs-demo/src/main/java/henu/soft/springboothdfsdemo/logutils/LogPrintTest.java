package henu.soft.springboothdfsdemo.logutils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LogPrintTest {

    public static void main(String[] args) {

        log.info("this info level");
        log.warn("this info level");
        log.error("this info level");
        log.debug("this info level");

//        Logger myloger = LogManager.getLogger(LogPrintTest.class);
//        myloger.error("error");
//        myloger.warn("warn");
//        myloger.info("info");
//        myloger.debug("debug");
    }



}
