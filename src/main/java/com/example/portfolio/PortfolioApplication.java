package com.example.portfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import com.example.portfolio.entity.Category;
import com.example.portfolio.repository.CategoryRepository;

@SpringBootApplication
@EnableJpaAuditing
public class PortfolioApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @Bean
    CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {
            try {
                initializeCategories(categoryRepository);
            } catch (Exception e) {
                System.err.println("カテゴリ初期化に失敗しました: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    @Transactional
    public void initializeCategories(CategoryRepository categoryRepository) {
        if (categoryRepository.count() == 0) {
            Category c1 = new Category();
            c1.setName("仕事");
            c1.setShared(true); // デフォルトカテゴリなので共有フラグをON

            Category c2 = new Category();
            c2.setName("学習");
            c2.setShared(true);

            Category c3 = new Category();
            c3.setName("プライベート");
            c3.setShared(true);

            categoryRepository.save(c1);
            categoryRepository.save(c2);
            categoryRepository.save(c3);

            System.out.println("デフォルトカテゴリを初期化しました。");
        }
    }
}
