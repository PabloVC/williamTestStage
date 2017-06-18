package com.testwilliam.steps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
 
public class WilliamTestStep {
	
	//Declaration of the main variables used in the code.
	String website = "http://sports.williamhill.com/sr-admin-set-white-list-cookie.html";
	String user = "WHATA_FOG2";
    String password = "F0gUaTtest";
    WebDriver driver = new FirefoxDriver();
    String stack = "0";
    
    //Method to wait between Selenium commands in Firefox.
    public void waitSecond(WebDriver driver, int seconds){
        synchronized(driver){
           try {
              driver.wait(seconds * 1000);
           } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }
        }
     }
 
	@Given("^Authentication on william hill$")
	// Open the web site page and manage an authentication with the data provided.
	public void authentication_on_william_hill() throws Throwable {
		driver.get(website);
		driver.findElement(By.id("accountTabButton")).click();
			System.out.println("Identification button has been clicked");
		WebElement elementUser;
		elementUser = driver.findElement(By.id("loginUsernameInput"));
		elementUser.sendKeys(user);
			System.out.println("User name entered ");
		WebElement elementPassword;
		elementPassword = driver.findElement(By.id("loginPasswordInput"));
		elementPassword.sendKeys(password);
			System.out.println("Password entered ");
		driver.findElement(By.id("loginButton")).click();
			System.out.println("Login button has been clicked");
		waitSecond(driver,10);
	    //throw new PendingException();
	}

	@When("^Bet (\\d+)\\.(\\d+) to \"(.*?)\"$")
	//Navigate to any parameterized sport (football, tennis,....) Select one
	//bet slip ant enter the parameterized stake.
	public void bet_to(int arg1, int arg2, String arg3) throws Throwable {
	//Navigate to any parameterized sport.
		driver.manage().window().maximize();
		driver.findElement(By.id("desktop-sidebar-az")).click();
		driver.findElement(By.id("nav-"+arg3)).click();
		waitSecond(driver,3);
			System.out.println("The parameterize sport, "+arg3+", has been selected");
	//Select one bet slip.
		driver.findElement(By.xpath("//div[2]/div[2]/div/button")).click();
		waitSecond(driver,3);
	//Write parameterized stake.
	    if (arg2>9){ //To write correct stack: 0.50,0.05,...
	    	stack = arg1+"."+arg2;
	    }else{
	    	stack = arg1+".0"+arg2;
	    }
		driver.findElement(By.xpath("//div/div/div/div/span/input")).click();
	    driver.findElement(By.xpath("//div/div/div/div/span/input")).clear();
	    driver.findElement(By.xpath("//div/div/div/div/span/input")).sendKeys(stack);
	    //throw new PendingException();
	}
 
	@Then("^Assert to return, total stake and user amount$")
	//Assert the "To return", "Total stake" and User balance is update).
	public void show_result() throws Throwable {
		//Save data to assert.
		String toreturn = driver.findElement(By.id("total-to-return-price")).getText();
		String totalstake = driver.findElement(By.id("total-stake-price")).getText();
		String odds = driver.findElement(By.xpath("//header[2]/span/span[2]")).getText();
		System.out.println("To return:"+toreturn);
		System.out.println("Total stake:"+totalstake);
		System.out.println("Odds:"+odds);
		//Verify that entered stack is the same that total stake in the bet.
		Assert.assertEquals(stack, totalstake);
		
		//Get actual account amount and calculate account amount after bet.
		String beforeAA = driver.findElement(By.xpath("//div/a/span")).getText();
		float varFloatBefore = Float.parseFloat(beforeAA.substring(1));
		float varStake = Float.parseFloat(totalstake);
		String spectedAA = Float.toString(varFloatBefore-varStake);
		System.out.println("Actual account amount:"+beforeAA.substring(1));
		System.out.println("Spected account amount:"+spectedAA);
		
		//Bet.
		driver.findElement(By.id("place-bet-button")).click();
		System.out.println("Bet button has been selected");
		waitSecond(driver,5);
		
		//Get account amount after bet.
		String afterAA = driver.findElement(By.xpath("//div/a/span")).getText();
		//Verify that user balance has been updated correctly.
		Assert.assertEquals(spectedAA, afterAA.substring(1));
		System.out.println("Real account amount after bet:"+afterAA.substring(1));
	    //throw new PendingException();
	}
	
}