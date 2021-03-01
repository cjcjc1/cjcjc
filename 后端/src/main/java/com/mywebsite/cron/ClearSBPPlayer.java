package com.mywebsite.cron;

import com.mywebsite.socket.SimulationBPWebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class ClearSBPPlayer {
    @Scheduled(cron = "0 0 3 * * ?")
    private void configureTasks() throws IOException {
        SimulationBPWebSocketServer.init();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + " 清理模拟BP玩家");
    }
}
