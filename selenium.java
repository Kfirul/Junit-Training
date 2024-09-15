import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        FirefoxOptions options = new FirefoxOptions();
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.setBinary(firefoxBinary);

        WebDriver driver = new FirefoxDriver(options);

        String[] accounts = {"standard_user", "locked_out_user", "wrong_user"};


        for (int i = 0; i < accounts.length; i++) {
            if (loginAndVerify(driver, accounts[i])) {
                printProductNames(driver);
                addExpensiveCheapestProductsToCart(driver);
                verifyNumberProductCart(driver);
                removingTheSecondProduct(driver);
                resetAppStateAndLogOut(driver);
            }
        }

        driver.quit();
    }

    private static boolean loginAndVerify(WebDriver driver, String username) {
        driver.get("https://www.saucedemo.com/");

        // Perform login
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify the expected behavior
        if (driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")) {
            System.out.println("Login successful for user: " + username);
            return true;
        } else {
            System.err.println("Error: Login failed for user: " + username);
            return false;
        }
    }

    private static void printProductNames(WebDriver driver) {
        // Select "Name (A to Z)" option from the dropdown to sort the products
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        sortDropdown.click();  // Open the dropdown
        driver.findElement(By.cssSelector("option[value='az']")).click();  // Select 'Name (A to Z)'

        // Wait for the sorting to take effect (you may need to adjust the wait time)
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        ArrayList<String> productNames = getProductNames(driver);

        System.out.println("Product Names (Sorted A to Z):");
        for (String productName : productNames) {
            System.out.println(productName);
        }
    }

    private static ArrayList<String> getProductNames(WebDriver driver) {
        List<WebElement> productElements = driver.findElements(By.className("inventory_item_name"));

        ArrayList<String> productNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            productNames.add(productElement.getText());
        }

        return productNames;
    }


    private static void addExpensiveCheapestProductsToCart(WebDriver driver) {
        // Sort products by price (high to low)
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        sortDropdown.click();
        driver.findElement(By.cssSelector("option[value='hilo']")).click();  // Select 'Price (high to low)'

        // Wait for the sorting to take effect (you may need to adjust the wait time)
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Find and add the most expensive product to the cart
        List<WebElement> products = driver.findElements(By.className("inventory_item_name"));

        if (!products.isEmpty()) {
            WebElement mostExpensiveProduct = products.get(0);

            // Find the parent container of the product
            WebElement productContainer = mostExpensiveProduct.findElement(By.xpath("./ancestor::div[@class='inventory_item']"));

            // Locate the "ADD TO CART" button within the product container
            WebElement addToCartButton = productContainer.findElement(By.cssSelector(".btn_inventory"));

            addToCartButton.click();

            // Find the cheapest product (last product in the list)
            WebElement cheapestProduct = products.get(products.size() - 1);

            // Find the parent container of the cheapest product
            WebElement cheapestProductContainer = cheapestProduct.findElement(By.xpath("./ancestor::div[@class='inventory_item']"));

            // Locate the "ADD TO CART" button within the cheapest product container
            WebElement addToCartCheapestButton = cheapestProductContainer.findElement(By.cssSelector(".btn_inventory"));

            addToCartCheapestButton.click();
        }


    }

    public static void verifyNumberProductCart(WebDriver driver) {
        WebElement cart = driver.findElement(By.className("shopping_cart_link"));
        WebElement cartBadge = cart.findElement(By.className("shopping_cart_badge"));

        // Get the text content of the "shopping_cart_badge" element
        String cartBadgeText = cartBadge.getText();

        System.out.println("Number of products in the cart: " + cartBadgeText);
    }


    public static void removingTheSecondProduct(WebDriver driver){
        // Go to the cart
        WebElement cart = driver.findElement(By.className("shopping_cart_link"));
        cart.click();

        List<WebElement> products = driver.findElements(By.className("inventory_item_name"));

        WebElement cheapestProduct = products.get(products.size() - 1);

        // Find the parent container of the cheapest product
        WebElement cheapestProductContainer = cheapestProduct.findElement(By.xpath("./ancestor::div[@class='cart_item']"));

        // Locate the "ADD TO CART" button within the cheapest product container
        WebElement removeButton = cheapestProductContainer.findElement(By.cssSelector(".cart_button"));

        removeButton.click();


    }

    public static void resetAppStateAndLogOut(WebDriver driver) {
        WebElement goBack = driver.findElement(By.cssSelector(".btn.btn_secondary.back.btn_medium"));
        goBack.click();

        WebElement menu = driver.findElement(By.className("bm-burger-button"));
        menu.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Add a hash before the ID in the selector
        driver.findElement(By.cssSelector("#reset_sidebar_link")).click();
        driver.findElement(By.cssSelector("#logout_sidebar_link")).click();

        if (driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")) {
            System.out.println("At Login");
        }
    }







}