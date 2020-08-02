package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class StepikTests {

    @BeforeEach
    void openingSite(){
        // Переход на страницу тестируемого сайта
        open("https://stepik.org/");
        //Нажатие кнопки "Войти"
        $("#ember201").click();
        // Ввести логин
        $(byName("login")).setValue("test.user.pikabu@gmail.com");
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue("pikabu123").pressEnter();
    }
    @Test
    void authorizeTest() {

        // Проверить наличие аватара пользователя
        $(".navbar__profile-img").isEnabled();
        //Вызов выпадающего меню
        $(".navbar__profile-img").click();
        //Нажатие на пункт "Профиль"
        $(".drop-down__body a").click();
        //Проверка отображения имени пользователя
        $(".profile-header-widget__name-wrapper h1").shouldHave(text("Тестовый пользователь"));
    }

    @Test
    void searchTest(){

        //$(".st-course-filters").shouldHave(text("Математика")).click();
        //Вводим запрос в поле поиска
        $(".explore__search__input-wrapper input[placeholder = 'Поиск по каталогу']").setValue("Уравнения").pressEnter();
        //Проверяем результат поиска
        $(".course-pack").shouldHave(text("Уравнения"));
    }
}
