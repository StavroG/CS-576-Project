package org.example.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DeviceManager
{
    private final List<Device> availableDevices;
    private static final String WINDOWS_OS_NAME = "Windows";
    private static final String LINUX_OS_NAME = "Linux";

    public DeviceManager()
    {
        availableDevices = new ArrayList<>();
        initAvailableDevices();
    }

    private void initAvailableDevices()
    {
        try
        {
            String os = System.getProperty("os.name");
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            String subnet = hostAddress.substring(0, hostAddress.lastIndexOf('.'));

            if(os.startsWith(WINDOWS_OS_NAME))
            {

                Process exec = Runtime.getRuntime().exec(new String[]{"arp", "-a"});
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                String input = "";

                while((input = bufferedReader.readLine()) != null)
                {
                    input = input.trim();
                    if(input.startsWith(subnet))
                    {
                        String[] output = input.split(" * ");
                        Device device = new Device(output[0], output[1], ConnectionType.valueOf(output[2].toUpperCase()));
                        availableDevices.add(device);
                    }
                }

            }
            else if(os.startsWith(LINUX_OS_NAME))
            {

            }
            else
            {
                System.out.println("Running on unsupported operating system: " + os);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAvailableAddresses()
    {
        List<String> addresses = new ArrayList<>();

        availableDevices.forEach(device -> addresses.add(device.getIpAddress()));

        return addresses;
    }

    public void connectDevice(String ipAddress)
    {
        for(Device availableDevice : availableDevices)
        {
            if(availableDevice.getIpAddress().equals(ipAddress))
            {
                availableDevice.setConnected(true);
            }
        }
    }

    public void disconnectDevice(String ipAddress)
    {
        for(Device availableDevice : availableDevices)
        {
            if(availableDevice.getIpAddress().equals(ipAddress))
            {
                availableDevice.setConnected(false);
            }
        }
    }
}
