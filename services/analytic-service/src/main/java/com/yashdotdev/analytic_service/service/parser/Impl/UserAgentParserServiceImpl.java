package com.yashdotdev.analytic_service.service.parser.Impl;


import com.yashdotdev.analytic_service.dtos.UserAgentMetadata;
import com.yashdotdev.analytic_service.enums.BrowserType;
import com.yashdotdev.analytic_service.enums.DeviceType;
import com.yashdotdev.analytic_service.enums.OperatingSystem;
import com.yashdotdev.analytic_service.service.parser.UserAgentParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;


@Service
@Slf4j
public class UserAgentParserServiceImpl implements UserAgentParserService {

    private final Parser parser = new Parser();


    @Override
    public UserAgentMetadata parse(String userAgent) {


        if(userAgent == null || userAgent.isBlank()){

            return UserAgentMetadata.builder()
                    .browser(BrowserType.OTHER)
                    .operatingSystem(OperatingSystem.OTHER)
                    .deviceType(DeviceType.OTHER)
                    .build();
        }

        Client client = parser.parse(userAgent);

        BrowserType browser =
                parseBrowser(client.userAgent.family);

        OperatingSystem os =
                parseOperatingSystem(client.os.family);

        DeviceType device =
                parseDevice(client.device.family);

        return UserAgentMetadata.builder()
                .browser(browser)
                .operatingSystem(os)
                .deviceType(device)
                .build();
    }

    private BrowserType parseBrowser(String browser) {

        if (browser == null) {
            return BrowserType.OTHER;
        }

        browser = browser.toLowerCase();

        if (browser.contains("chrome"))
            return BrowserType.CHROME;

        if (browser.contains("firefox"))
            return BrowserType.FIREFOX;

        if (browser.contains("edge"))
            return BrowserType.EDGE;

        if (browser.contains("safari"))
            return BrowserType.SAFARI;

        if (browser.contains("opera"))
            return BrowserType.OPERA;

        if (browser.contains("internet explorer"))
            return BrowserType.INTERNET_EXPLORER;

        return BrowserType.OTHER;
    }

    private OperatingSystem parseOperatingSystem(String os) {

        if (os == null) {
            return OperatingSystem.OTHER;
        }

        os = os.toLowerCase();

        if (os.contains("windows"))
            return OperatingSystem.WINDOWS;

        if (os.contains("linux"))
            return OperatingSystem.LINUX;

        if (os.contains("mac"))
            return OperatingSystem.MAC;

        if (os.contains("android"))
            return OperatingSystem.ANDROID;

        if (os.contains("ios"))
            return OperatingSystem.IOS;

        return OperatingSystem.OTHER;
    }

    private DeviceType parseDevice(String device) {

        if (device == null) {
            return DeviceType.OTHER;
        }

        device = device.toLowerCase();

        if (device.contains("mobile"))
            return DeviceType.MOBILE;

        if (device.contains("tablet"))
            return DeviceType.TABLET;

        if (device.contains("spider")
                || device.contains("bot")) {

            return DeviceType.BOT;
        }

        return DeviceType.DESKTOP;
    }
}
