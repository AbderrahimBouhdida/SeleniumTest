package testing;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Config {
    private String username;
    private String password;

    Config(){}

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    void loadConfig(){
        try  {
            InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find Config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            username = prop.getProperty("username");
            password = prop.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
