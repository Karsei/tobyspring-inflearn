package kr.pe.karsei.config.autoconfig;

import kr.pe.karsei.config.MyConfigurationProperties;

@MyConfigurationProperties(prefix = "server")
public class ServerPropertiesV2 {
    private String contextPath;
    private int port;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
