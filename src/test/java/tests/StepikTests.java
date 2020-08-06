package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class StepikTests {

    private final String url = "https://stepik.org/";
    private final String login = "test.user.pikabu@gmail.com";
    private final String password = "pikabu123";

        @BeforeAll
        static void config(){
            Configuration.timeout = 10000;
        }

    @Test
    void IncorrectAuthorizationTest() {
        // Переход на страницу тестируемого сайта
        open(url);
        //Нажатие кнопки "Войти"
        $(".navbar__auth_login").click();
        // Ввести логин
        $(byName("login")).setValue(login);
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue("123").pressEnter();
        // Проверить наличие срообщения об ошибке
        $("ul.sign-form__messages li[role='alert']").shouldBe(visible);
        //$(".modal-dialog-top__close").click();
    }

    @Test
    void checkProfileTest() {
        // Переход на страницу тестируемого сайта
        open(url);
        //Нажатие кнопки "Войти"
        $(".navbar__auth_login").click();
        // Ввести логин
        $(byName("login")).setValue(login);
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue(password).pressEnter();
        // Проверить наличие аватара пользователя
        $(".navbar__profile-img").isEnabled();
        //Вызов выпадающего меню
        $(".navbar__profile-img").click();
        //Нажатие на пункт "Профиль"
        $(".drop-down__body a").click();
        //Проверка отображения имени пользователя
        $("img.navbar__profile-img").shouldBe(visible);
        $(".profile-header-widget__name-wrapper h1").shouldHave(text("Тестовый пользователь"));
    }

    @Test
    void searchTest() {
        // Переход на страницу тестируемого сайта
        open(url);
        //Нажатие кнопки "Войти"
        $(".navbar__auth_login").click();
        // Ввести логин
        $(byName("login")).setValue(login);
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue(password).pressEnter();
        $(byText("Каталог")).click();
        //$(".st-course-filters").shouldHave(text("Математика")).click();
        //Вводим запрос в поле поиска
        $(".explore__search__input-wrapper input[placeholder = 'Поиск по каталогу']").setValue("Уравнения").pressEnter();
        //Проверяем результат поиска
        $(".course-pack").shouldHave(text("Уравнения"));
    }

    @Test
    void joinAndExitCourseTest() {
        // Переход на страницу тестируемого сайта
        open(url);
        //Нажатие кнопки "Войти"
        $(".navbar__auth_login").click();
        // Ввести логин
        $(byName("login")).setValue(login);
        // Ввести пароль и нажать Enter
        $(byName("password")).setValue(password).pressEnter();
        //Найти курс и перейти на его страницу
        $(byText("Машинное обучение и управление проектами в IT для преподавателей")).click();
        //Записаться на курс
        $(byText("Поступить на курс")).click();
        //Дождаться загрузки страницы первого урока
        $(".lesson-sidebar__course-title").shouldBe(visible);
        //Открыть список меню пользователя и перейти в пункт "Мои курсы"
        $(".navbar__profile-img").click();
        $(".drop-down__body a", 1).click();
        //Проверить наличие курса в списке курсов пользователя
        $(".course-widget__main-info a").shouldHave(text("Машинное обучение и управление проектами в IT для преподавателей"));
        //Покинуть курс
        $(".course-widget__menu").click();
        $(byText("Покинуть")).click();
        //Подтвердить действие
        switchTo().alert().accept();
        //Проверить, что у объекта курса пропали контролы
        Assertions.assertEquals(0, $$("course-widget__menu-toggler").size());
        //Обновить страницу
        refresh();
        //Проверить,что список курсов пустой
        Assertions.assertEquals(0, $$(".course-widget__main-info").size());
    }

    @AfterEach
    void exit() {
        /*$(".navbar__profile-img").click();
        $(".drop-down__body button", 1).click();
        switchTo().alert().accept();*/
        closeWindow();
    }
}
