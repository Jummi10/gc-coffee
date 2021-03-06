package com.example.gccoffee.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;
    @Autowired
    ProductJdbcRepository productRepository;
    private Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L,
        null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v5_7_latest)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql")).start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("????????? ????????? ??? ??????.")
    void testInsert() {
        productRepository.insert(newProduct);
        List<Product> all = productRepository.findAll();

        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("????????? ???????????? ????????? ??? ??????.")
    void testFindByName() {
        Optional<Product> product = productRepository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("????????? ???????????? ????????? ??? ??????.")
    void testFindById() {
        Optional<Product> product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("????????? ??????????????? ????????? ??? ??????.")
    void testFindByCategory() {
        List<Product> product = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("????????? ????????? ??? ??????.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        productRepository.update(newProduct);

        Optional<Product> product = productRepository.findById(newProduct.getProductId());

        assertThat(product.isEmpty(), is(false));
        // assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("????????? ?????? ????????????.")
    void testDeleteAll() {
        productRepository.deleteAll();
        List<Product> all = productRepository.findAll();
        assertThat(all.isEmpty(), is(true));
    }
}