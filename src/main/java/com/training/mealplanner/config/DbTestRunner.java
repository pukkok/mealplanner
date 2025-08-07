package com.training.mealplanner.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

// 나중에 다시 쓸 때 주석 풀기
//@Component
public class DbTestRunner {

    private final DataSource dataSource;

    public DbTestRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Oracle DB 연결 완료");
            System.out.println("URL: " + conn.getMetaData().getURL());
            System.out.println("User: " + conn.getMetaData().getUserName());
        } catch (Exception e) {
            System.err.println("DB 연결 실패:");
            e.printStackTrace();
        }
    }
}
