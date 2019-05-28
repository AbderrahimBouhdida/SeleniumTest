# SeleniumTest

A simple java application demonstrating the use of Selenium Framework to pull new grades from "Esprit" website.

This should work on Windows normally for the Linux Follow this instructions :
  - install ChromeDriver:
  <code>
  wget https://chromedriver.storage.googleapis.com/2.35/chromedriver_linux64.zip
  </code>
  <code>
  unzip chromedriver_linux64.zip
  </code>
  <code>
  ./chromedriver
  </code>
  
  - uncomment necessary line in Tests.java file
  <code>
  String pathToChromeDriver = "lib/chromedriver.exe";  //Comment this line
  System.setProperty("webdriver.chrome.driver", pathToChromeDriver);  // and this line for linux
  </code>
  
  - Don't forget to change username and password in config.propreties
