package com.gmap.maptest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class GmapTest {

    private AppiumDriver driver;
    private WebDriverWait webDriverWait;
    List<MobileElement> listOfResults;
    String resName;
    MobileElement element;
    String v, b, d, ip, udid, pack, appActivity;

    @Before
    public void setup(String v, String ip, String udid, String pack, String appActivity) throws MalformedURLException {
        this.v = v;
        this.ip= ip;
        this.udid=udid;
        this.pack=pack;
        this.appActivity=appActivity;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, v);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,pack);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,appActivity);
        driver = new AndroidDriver(new URL("http://"+ip+":4723/wd/hub"), desiredCapabilities);
        webDriverWait = new WebDriverWait(driver, 30);

    }

    @Test
    public void testGmap(String b) {
        this.b = b;
        // How to click the first element in the search result
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_text_box").click();
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_edit_text").sendKeys(b + " \n");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.apps.maps:id/recycler_view")));
        listOfResults = driver.findElementsById("com.google.android.apps.maps:id/title");
        for (MobileElement e : listOfResults) {
            System.out.println("Restaurant Name : " + e.getText().toString() + "\n");
        }
        resName = listOfResults.get(0).getText();
        listOfResults.get(0).click();
        element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + resName + "\");"));
        Assert.assertTrue(element.isDisplayed());

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String args[]) throws IOException {
        /*String terminal="bash archivo.sh";
        Runtime run = Runtime.getRuntime();
        Process process = run.exec(terminal);*/
        final String commands[] = {"adb", "devices"}; // estos son los comandos a ejecutar, seria: 
        int l=0;
        // usuario@usuario-pc:~$ ls /

        Process process = new ProcessBuilder(commands).start(); // se crea el proceso
         // usando los comandos // ProcessBuilder.directory(new File("ruta")); donde ruta = la carpeta del ejecutable
                // Se lee la salida
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        System.out.println("esto es :" + br);

        String line[]= new String[10];
        while ((line[l] = br.readLine()) != null) {
            System.out.println(line[l]); l++;

        } System.out.println(line[3]);

// Esperamos que el proceso termine
        try {
            int exitValue = process.waitFor();
            System.out.println("\nCódigo de salida: " + exitValue);
          
        } catch (InterruptedException f) {

        }
    }

}
