import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreTests {

    //Автотесты
    // 1. Корректное открывание страницы
    @BeforeEach
    void setUp() {
        open("https://www.demoblaze.com/");
        getWebDriver().manage().window().maximize();
    }


    // 2. Добавление товара в корзину
    @Test
    void test01AddProduct() {
        $x("//a[.='Samsung galaxy s6']").click();
        $x("//h2[contains(@class, 'name')]").shouldHave(text("Samsung galaxy s6"));
        $x("//a[.='Add to cart']").click();
        Alert alert= switchTo().alert();
        assertTrue(alert.getText().contains("Product added"));
        alert.accept();
    }

    // 3. Оформление заказа с валидными данными
    @Test
    void test02PlacingOrder() {
        $x("//a[.='Cart']").click();
        $x("//button[.='Place Order']").click();
        $("#name").setValue("Ivan");
        $("#country").setValue("Russia");
        $("#city").setValue("a");
        $("#card").setValue("646465626");
        $("#month").setValue("12");
        $("#year").setValue("2025");
        $x("//button[.='Purchase']").click();
        $(By.tagName("body")).shouldHave(text("Thank you for your purchase!"));
    }

    // 4. Оформление заказа с пустыми значениями
    @Test
    void test03PlacingNotOrder() {
        $x("//a[.='Cart']").click();
        $x("//button[.='Place Order']").click();
        $("#name").setValue("");
        $("#country").setValue("");
        $("#city").setValue("");
        $("#card").setValue("");
        $("#month").setValue("");
        $("#year").setValue("");
        $x("//button[.='Purchase']").click();
        Alert alert= switchTo().alert();
        assertTrue(alert.getText().contains("Please fill out Name and Creditcard."));
        alert.accept();
    }

    // 5. Вход в аккаунт
    @Test
    void test04LogIn() {
        $("#login2").click();
        $("#loginusername").setValue("User");
        $("#loginpassword").setValue("password");
        $x("//button[.='Log in']").click();
        Alert alert= switchTo().alert();
        assertTrue(alert.getText().contains("Wrong password."));
        alert.accept();
    }


}
