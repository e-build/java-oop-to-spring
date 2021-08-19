package com.framework.web.server;

import com.framework.core.db.ConnectionManager;
import com.framework.core.new_mvc.AnnotationHandlerMapping;
import com.framework.core.new_mvc.HandlerMapping;
import com.framework.core.mvc.LegacyHandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {

    private static List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws IOException {
        int port = 0;
        if (args == null || args.length == 0)
            port = DEFAULT_PORT;
        else
            port = Integer.parseInt(args[0]);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port);){
            logger.info("Web Application Server started {} port.", port);

            // 데이터 베이스 초기화 (테이블 생성)
            ConnectionManager.executeInitialScript();

            // 레거시 컨트롤러 초기화 매핑
            LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
            legacyHandlerMapping.initialize();

            // 어노테이션 컨트롤러 초기화 매핑
            AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("com.business");
            annotationHandlerMapping.initialize();

            handlerMappings.add(annotationHandlerMapping);
            handlerMappings.add(legacyHandlerMapping);

            // 클라이언트 대기
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                // Request 당 하나의 스레드 할당
                Thread thread = new Thread(new DispatchRequest(connection));
                thread.start();
            }
        }
    }

    public static List<HandlerMapping> getHandlerMappings(){
        return handlerMappings;
    }
}
