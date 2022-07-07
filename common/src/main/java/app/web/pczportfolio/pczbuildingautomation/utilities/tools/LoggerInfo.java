package app.web.pczportfolio.pczbuildingautomation.utilities.tools;

import org.slf4j.Logger;

public class LoggerInfo {
    public static void  showInfo(Logger logger, String info){
        logger.info("<>---------------------------------------<>");
        logger.info(info);
        logger.info("<>---------------------------------------<>");
    }
}
