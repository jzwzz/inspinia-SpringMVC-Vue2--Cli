package com.cmbchina.microray.cli.auth.cas;

import com.cmb.ccd.mr.common.cas.config.CASClusterConfig;
import com.cmb.ccd.mr.common.cas.config.CASConfig;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.URLDecoder;

public class CASConfigContextListener implements ServletContextListener {

    private static Logger LOG = Logger.getLogger(CASConfigContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            CASConfig.init(URLDecoder.decode(CASConfig.class.getResource("/").getPath() + "cas.properties", "UTF-8"));
            LOG.info("init CASConfig success");
        } catch (Exception e) {
            LOG.warn("can not init CASConfig: "+e.getMessage());
        }
        try {
            CASClusterConfig.init();
            LOG.info("init CASClusterConfig success");
        } catch (Exception e) {
            LOG.warn("can not init CASClusterConfig: "+e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

}
