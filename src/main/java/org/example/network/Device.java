package org.example.network;

class Device
{
    private final String ipAddress;
    private final String macAddress;
    private final ConnectionType connectionType;
    private boolean connected;

    public Device(String ipAddress, String macAddress, ConnectionType connectionType)
    {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.connectionType = connectionType;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public String getMacAddress()
    {
        return macAddress;
    }

    public ConnectionType getConnectionType()
    {
        return connectionType;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public void setConnected(boolean connected)
    {
        this.connected = connected;
    }
}
